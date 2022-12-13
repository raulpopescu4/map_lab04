package model.statements;

import exceptions.MyException;
import model.dataStructures.MyiDictionary;
import model.dataStructures.MyiStack;
import model.expressions.ProgramExpression;
import model.ProgramState;
import model.values.BoolValue;
import model.values.Value;

public class IfStatement implements IStatement {
    ProgramExpression expression;
    IStatement thenS;
    IStatement elseS;

    public IfStatement(ProgramExpression _expression, IStatement _thenS, IStatement _elseS){
        expression = _expression;
        thenS = _thenS;
        elseS = _elseS;
    }

    @Override
    public String toString(){
        return "(IF(" + expression.toString() + ") THEN(" + thenS.toString() + ") ELSE(" + elseS.toString() + "))";
    }

    public ProgramState execute(ProgramState state) throws MyException {
        MyiDictionary<String, Value> symbolsTable = state.getSymbolsTable();

        Value expressionValue = expression.evaluate(symbolsTable, state.getHeapTable());

        if(expressionValue instanceof BoolValue booleanExpression) {
            MyiStack<IStatement> executionStack = state.getExecutionStack();

            if (booleanExpression.getValue())
                executionStack.push(thenS);
            else executionStack.push(elseS);

            state.setExecutionStack(executionStack);

            return state;
        }
        else
            throw new MyException("Conditional expression is not a boolean!");
    }

    public IStatement createDeepCopy() {
        return new IfStatement(expression.createDeepCopy(), thenS.createDeepCopy(), elseS.createDeepCopy());
    }
}
