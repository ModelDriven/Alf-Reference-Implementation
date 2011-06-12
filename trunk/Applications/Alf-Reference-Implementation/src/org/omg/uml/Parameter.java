package org.omg.uml;

public class Parameter extends NamedElement {

    public ParameterDirectionKind getDirection() {
        return ParameterDirectionKind.in;
    }
    
    public Integer getLower() {
        return 0;
    }
    
    public Integer getUpper() {
        return -1;
    }
    
    public Boolean getIsOrdered() {
        return false;
    }
    
    public Boolean getIsUnique() {
        return false;
    }
    
    public Type getType() {
        return null;
    }

}
