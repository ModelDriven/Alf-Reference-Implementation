/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.units;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.MissingMember;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class RootNamespaceImpl extends ModelNamespaceImpl {
    
    private ModelNamespace modelNamespace = null;
    
    public RootNamespaceImpl() {
        super(RootNamespace.getRootScope());
        RootNamespace.setRootImpl(this);
        
        this.setLibraryDirectory("Libraries");
        
        this.modelNamespace = new ModelNamespace();
        this.modelNamespace.setImpl(new ModelNamespaceImpl(this.modelNamespace));
        this.modelNamespace.setName("Model");
                
        RootNamespace self = this.getSelf();
        self.setName("Root");
        self.addOwnedMember(this.modelNamespace);
        this.modelNamespace.setNamespace(self);
    }
    
    @Override
    public RootNamespace getSelf() {
        return (RootNamespace)this.self;
    }

    public ModelNamespace getModelNamespace() {
        return this.modelNamespace;
    }
    
    public ModelNamespaceImpl getModelNamespaceImpl() {
        return (ModelNamespaceImpl)this.getModelNamespace().getImpl();
    }
    
    @Override
    public void setModelDirectory(String modelDirectory) {
        this.getModelNamespaceImpl().setModelDirectory(modelDirectory);
    }
    
    @Override
    public String getModelDirectory() {
        return this.getModelNamespaceImpl().getModelDirectory();
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
        super.setModelDirectory(libraryDirectory);
    }
    
    public String getLibraryDirectory() {
        return super.getModelDirectory();
    }
    
    @Override
    public void setIsVerbose(boolean isVerbose) {
        this.getModelNamespaceImpl().setIsVerbose(isVerbose);
    }
    
    @Override
    public NamespaceDefinition getModelNamespace(UnitDefinition unit) {
        return this.getModelNamespace().getModelNamespace(unit);
    }
    
    @Override
    public Collection<Member> resolve(String name, boolean classifierOnly) {
        ModelNamespaceImpl modelScopeImpl = this.getModelNamespaceImpl();
        Collection<Member> members = modelScopeImpl.resolve(name, classifierOnly);
        if (members.isEmpty()) {
            members = super.resolveInScope(name, classifierOnly);
            if (members.isEmpty()) {
                QualifiedName qualifiedName = new QualifiedName().getImpl().addName(name);
                Member member;
                UnitDefinition unit = this.resolveModelUnit(qualifiedName);
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
                member.setNamespace(self);
            } else if (members.toArray()[0] instanceof MissingMember) {
                members = new ArrayList<Member>();
            }
        }
        return members;
    }
    
    @Override
    public UnitDefinition resolveModelUnit(QualifiedName qualifiedName) {
        UnitDefinition unit = super.resolveModelUnit(qualifiedName);
        if (unit instanceof MissingUnit) {
            System.out.println("Unit not found: " + qualifiedName.getPathName());
        }
        return unit;
    }
    
    @Override
    public Collection<Member> resolveAsOuterScope(String name, boolean classifierOnly) {
        return new ArrayList<Member>();
    }
    
    @Override
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return this.getModelNamespace().resolveUnit(qualifiedName);
    }

}
