/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.common;

public class ConstraintViolation implements Comparable<ConstraintViolation> {
    
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
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ConstraintViolation)) {
            return false;
        } else {
            ConstraintViolation violation = (ConstraintViolation)other;
            String constraintName = this.getConstraintName();
            String otherConstraintName = violation.getConstraintName();
            ParsedElement violatingElement = this.getViolatingElement();
            ParsedElement otherViolatingElement = violation.getViolatingElement();
            return (constraintName == null && otherConstraintName == null ||
                    constraintName != null && constraintName.equals(otherConstraintName)) &&
                   violatingElement == otherViolatingElement;
        }
    }
    
    @Override
    public int hashCode() {
        return this.getViolatingElement().hashCode();
    }

    @Override
    public int compareTo(ConstraintViolation other) {
        ParsedElement element = this.getViolatingElement();
        int line = element.getLine();
        int column = element.getColumn();
        
        element = other.getViolatingElement();
        int otherLine = element.getLine();
        int otherColumn = element.getColumn();
        
        return line < otherLine? -1:
               line > otherLine? 1:
               column < otherColumn? -1:
               column > otherColumn? 1: 0;
    }

}
