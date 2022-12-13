package controller;

import exceptions.MyException;
import model.dataStructures.MyiStack;
import model.ProgramState;
import model.statements.IStatement;
import model.values.ReferenceValue;
import model.values.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller {

    IRepository repository;
    boolean displayFlag = false;

    public Controller(IRepository _repository, boolean _displayFlag){
        this.repository = _repository;
        this.displayFlag = _displayFlag;
    }

    public boolean getDisplayFlag(){
        return displayFlag;
    }

    public void setDisplayFlag(boolean newDisplayFlag){
        displayFlag = newDisplayFlag;
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolsTableAddresses, Map<Integer,Value> heap) {
        return heap.entrySet().stream().filter(e-> symbolsTableAddresses.contains(e.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    List<Integer> getAddressFromTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue value1 = (ReferenceValue) v; return value1.getAddress();})
                .collect(Collectors.toList());
    }

    public ProgramState oneStepExecution(ProgramState state) throws MyException{
        MyiStack<IStatement> executionStack = state.getExecutionStack();
        if(executionStack.isEmpty())
            throw new MyException("ProgramState's stack is empty!");

        IStatement currentStatement = executionStack.pop();

        state.setExecutionStack(executionStack);

        return currentStatement.execute(state);
    }

    public void allStepExecution() throws IOException {
        ProgramState programState = repository.getCurrentProgramState();
        displayCurrentProgramState();

        repository.logProgramStateExecution();
        while (!programState.getExecutionStack().isEmpty()){
            oneStepExecution(programState);
            displayCurrentProgramState();
            repository.logProgramStateExecution();

            programState.getHeapTable().setContent(unsafeGarbageCollector
                    (getAddressFromTable(programState.getSymbolsTable().getContent().values()),
                            programState.getHeapTable().getContent()));

            repository.logProgramStateExecution();
        }

        programState.resetToOriginalProgram();
    }

    public void displayCurrentProgramState(){
        if(displayFlag)
            System.out.println(repository.getCurrentProgramState().toString());
    }

}
