package model.expressions;

import exceptions.MyException;
import model.dataStructures.MyIHeap;
import model.dataStructures.MyiDictionary;
import model.types.ReferenceType;
import model.values.ReferenceValue;
import model.values.Value;

public class HeapReadingExpression implements ProgramExpression{

    ProgramExpression expression;

    public HeapReadingExpression(ProgramExpression expression){
        this.expression = expression;
    }

    public Value evaluate(MyiDictionary<String, Value> table, MyIHeap heap) throws MyException {
        Value value = expression.evaluate(table, heap);
        if(!(value.getType() instanceof ReferenceType))
            throw new MyException("The value of the expression must be of type ReferenceType!");

        ReferenceValue referenceValue = (ReferenceValue) value;

        return heap.lookUp(referenceValue.getAddress());
    }

    public ProgramExpression createDeepCopy() {
        return new HeapReadingExpression(expression.createDeepCopy());
    }

    @Override
    public String toString(){
        return "HeapReadingExpression(" + expression.toString() + ")";
    }
}
