package model.types;

import model.values.ReferenceValue;
import model.values.Value;

public class ReferenceType implements Type{

    Type inner;

    public ReferenceType(Type inner){
        this.inner = inner;
    }

    public Type getInner(){
        return inner;
    }

    public boolean equals(Object another){
        if(another instanceof ReferenceType)
            return inner.equals(((ReferenceType) another).getInner());
        else
            return false;
    }

    @Override
    public String toString(){
        return "Ref(" + inner.toString() + ")";
    }
    public Value getDefaultValue() {
        return new ReferenceValue(0, inner);
    }
}
