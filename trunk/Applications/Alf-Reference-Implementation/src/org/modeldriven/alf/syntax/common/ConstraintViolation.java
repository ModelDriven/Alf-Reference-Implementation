/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/
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
