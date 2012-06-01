/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

public class RootNamespaceImpl extends ModelNamespaceImpl {
    
    public RootNamespaceImpl(RootNamespace self) {
        super(self);
        this.setModelDirectory("Libraries");
    }

    @Override
    public Collection<Member> resolve(String name, boolean classifierOnly) {
        Collection<Member> members = super.resolveInScope(name, classifierOnly);
        if (members.size() == 0) {
            QualifiedName qualifiedName = new QualifiedName().getImpl().addName(name);
            UnitDefinition unit = this.resolveUnit(qualifiedName);
            Member member;
            if (unit == null) {
                member = new MissingMember(name);
            } else {
                member = unit.getDefinition();
                if (member == null) {
                    member = new MissingMember(name);
                } else {
                    members.add(member);
                }
            }
            NamespaceDefinition self = this.getSelf();
            self.addOwnedMember(member);
            self.addMember(member);
        } else if (members.toArray()[0] instanceof MissingMember) {
            members = new ArrayList<Member>();
        }
        return members;
    }

    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        ModelNamespace modelScope = RootNamespace.getModelScope();
        
        // Look for the unit in the model first.
        UnitDefinition unit = modelScope.getImpl().resolveUnit(qualifiedName);
        
        // If not found in the model, look for the unit in the library.
        if (unit instanceof MissingUnit) {
            unit = super.resolveUnit(qualifiedName);
            if (unit instanceof MissingUnit) {
                System.out.println("Unit not found: " + qualifiedName.getPathName());
            } 
        }
        
        // Return a MissingUnit rather than null if parsing failed.
        return unit == null? new MissingUnit(qualifiedName): unit;
    }

}
