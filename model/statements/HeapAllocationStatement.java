package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.dataStructures.MyIHeap;
import model.dataStructures.MyiDictionary;
import model.expressions.ProgramExpression;
import model.types.ReferenceType;
import model.types.Type;
import model.values.ReferenceValue;
import model.values.Value;

public class HeapAllocationStatement implements IStatement{

    String variableName;
    ProgramExpression expression;

    public HeapAllocationStatement(String variableName, ProgramExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyiDictionary<String, Value> symbolsTable = state.getSymbolsTable();

        if(!symbolsTable.isDefined(variableName))
            throw new MyException("Variable " + variableName + " is not defined in the symbols table!");

        Value variable = symbolsTable.lookUp(variableName);

        if(!(variable.getType() instanceof ReferenceType))
            throw new MyException(variableName + " must be of type ReferenceType!");

        MyIHeap heap = state.getHeapTable();
        Value valueOfExpression = expression.evaluate(symbolsTable, heap);

        Type locationType = ((ReferenceValue)variable).getLocationType();



        if(!valueOfExpression.getType().equals(locationType))
            throw new MyException("The variable and the expression must be of same type");

        Integer address = heap.addSymbol(valueOfExpression);
        symbolsTable.addSymbol(variableName, new ReferenceValue(address, locationType));

        state.setHeapTable(heap);
        state.setSymbolsTable(symbolsTable);


        return state;
    }

    public IStatement createDeepCopy() {
        return new HeapAllocationStatement(variableName, expression.createDeepCopy());
    }

    @Override
    public String toString(){
        return "new(" + variableName + ", " + expression.toString();
    }
}
