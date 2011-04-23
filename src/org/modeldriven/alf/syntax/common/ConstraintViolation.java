package org.modeldriven.alf.syntax.common;

public class ConstraintViolation {
    
    private String constraintName = null;
    private Object violatingElement = null;
    
    public ConstraintViolation(String constraintName, Object violatingElement) {
        this.constraintName = constraintName;
        this.violatingElement = violatingElement;
    }
    
    @Override
    public String toString() {
        return this.constraintName + ": " + violatingElement;
    }

}
