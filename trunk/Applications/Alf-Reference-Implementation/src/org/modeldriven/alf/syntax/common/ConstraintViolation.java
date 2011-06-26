package org.modeldriven.alf.syntax.common;

public class ConstraintViolation {
    
    private String constraintName = null;
    private Object violatingElement = null;
    
    public ConstraintViolation(String constraintName, Object violatingElement) {
        this.constraintName = constraintName;
        this.violatingElement = violatingElement;
    }
    
    public String getConstraintName() {
        return this.constraintName;
    }
    
    public Object getViolatingElement() {
        return this.violatingElement;
    }
    
    @Override
    public String toString() {
        return this.getConstraintName() + ": " + this.getViolatingElement();
    }

}
