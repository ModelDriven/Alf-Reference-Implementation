package org.modeldriven.alf.syntax.common;

public class ConstraintViolation {
    
    private String constraintName = null;
    private ParsedElement violatingElement = null;
    
    public ConstraintViolation(String constraintName, ParsedElement violatingElement) {
        this.constraintName = constraintName;
        this.violatingElement = violatingElement;
    }
    
    public String getConstraintName() {
        return this.constraintName;
    }
    
    public ParsedElement getViolatingElement() {
        return this.violatingElement;
    }
    
    @Override
    public String toString() {
        ParsedElement element = this.getViolatingElement();
        return this.getConstraintName() + " in " + element.getFileName() + 
            " at line " + element.getLine() + ", column " + element.getColumn();
    }

}
