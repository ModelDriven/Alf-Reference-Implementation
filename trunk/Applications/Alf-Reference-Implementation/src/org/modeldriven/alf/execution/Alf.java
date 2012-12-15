/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.execution;

import java.util.Collection;

import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public abstract class Alf {
    
    protected boolean isVerbose = false;
    protected boolean isParseOnly = false;
    protected boolean isPrint = false;
    
    public void setIsVerbose(boolean isVerbose) {
        this.isVerbose = isVerbose;
    }
    
    public void setIsParseOnly(boolean isParseOnly) {
        this.isParseOnly = isParseOnly;
    }
    
    public void setIsPrint(boolean isPrint) {
        this.isPrint = isPrint;
    }

    protected void printVerbose(String message) {
        if (this.isVerbose) {
            this.println(message);
        }
    }
    
    public void executeUnit(UnitDefinition unit) {
        if (!(unit instanceof MissingUnit)) {
            NamespaceDefinition definition = unit.getDefinition();
            if (unit.getImpl().resolveStub()) {
                this.printVerbose("Resolved stub for " + 
                        definition.getImpl().getQualifiedName().getPathName());
            }
            
            RootNamespace root = RootNamespace.getRootScope();
            root.deriveAll();
            Collection<ConstraintViolation> violations = root.checkConstraints();
            if (!violations.isEmpty()) {
                this.println("Constraint violations:");
                for (ConstraintViolation violation: violations) {
                    this.println("  " + violation);
                }
                
            } else {
                this.printVerbose("No constraint violations.");
            }
            
            if (this.isPrint) {
                unit.print(true);
            } else if (!this.isParseOnly && violations.isEmpty()) {
                if (definition.getImpl().isTemplate()) { 
                    this.println(definition.getName() + " is a template.");
                } else {
                    this.execute(definition);
                }
            }
        }
    }
    
    protected abstract void execute(NamespaceDefinition definition);
    
    protected void println(String message) {
        System.out.println(message);
    }
    
}
