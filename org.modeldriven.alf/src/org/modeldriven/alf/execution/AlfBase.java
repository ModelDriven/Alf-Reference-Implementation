/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.execution;

import java.util.Collection;

import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public abstract class AlfBase {
    
    protected boolean isVerbose = false;

    public void setIsVerbose(boolean isVerbose) {
        this.isVerbose = isVerbose;
    }
    
    public UnitDefinition resolve(String unitName) {
        if (unitName == null) {
            return null;
        } else {
            String[] names = unitName.replace(".","::").split("::");
            QualifiedName qualifiedName = new QualifiedName();
            for (String name: names) {
                qualifiedName.getImpl().addName(name);
            }
            return RootNamespace.resolve(qualifiedName);
        }
    }
    
    public Collection<ConstraintViolation> check(
            UnitDefinition unit) {
        Collection<ConstraintViolation> violations = null;
        
        if (unit != null) {
            NamespaceDefinition definition = unit.getDefinition();
            if (unit.getImpl().resolveStub()) {
                this.printVerbose("Resolved stub for " + 
                        definition.getImpl().getQualifiedName().getPathName());
            }
    
            RootNamespace root = RootNamespace.getRootScope();
            root.deriveAll();
            violations = root.checkConstraints();
            
            if (!violations.isEmpty()) {
                this.println("Constraint violations:");
                for (ConstraintViolation violation: violations) {
                    this.println("  " + violation);
                }    
            } else {
                this.printVerbose("No constraint violations.");
            }    
        }
        
        return violations;
    }
    
    protected void printVerbose(String message) {
        if (this.isVerbose) {
            this.println(message);
        }
    }
    
    protected void println(String message) {
        System.out.println(message);
    }
    
}
