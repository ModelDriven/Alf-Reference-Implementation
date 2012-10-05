/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.units;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingMember;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class RootNamespaceImpl extends ModelNamespaceImpl {
    
    private ModelNamespace modelScope = null;
    
    public RootNamespaceImpl() {
        super(RootNamespace.getRootScope());
        RootNamespace.setRootImpl(this);
        
        this.setLibraryDirectory("Libraries");
        
        this.modelScope = new ModelNamespace();
        this.modelScope.setImpl(new ModelNamespaceImpl(this.modelScope));
        
        RootNamespace self = this.getSelf();
        self.addOwnedMember(this.modelScope);
        this.modelScope.setNamespace(self);
    }
    
    public RootNamespace getSelf() {
        return (RootNamespace)this.self;
    }

    public ModelNamespace getModelScope() {
        return this.modelScope;
    }
    
    public ModelNamespaceImpl getModelScopeImpl() {
        return (ModelNamespaceImpl)this.getModelScope().getImpl();
    }
    
    public void setModelDirectory(String modelDirectory) {
        this.getModelScopeImpl().setModelDirectory(modelDirectory);
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
        super.setModelDirectory(libraryDirectory);
    }
    
    public void setIsVerbose(boolean isVerbose) {
        this.getModelScopeImpl().setIsVerbose(isVerbose);
    }
    
    @Override
    public NamespaceDefinition getModelNamespace(UnitDefinition unit) {
        return this.getModelScope().getModelNamespace(unit);
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
    
    @Override
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return this.getModelScope().resolveUnit(qualifiedName);
    }

}
