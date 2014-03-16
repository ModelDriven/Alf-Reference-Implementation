/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * Copyright 2014 Ivar Jacobson International SA
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
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
    
    protected RootNamespaceImpl(RootNamespace self) {
        super(self);
        RootNamespace.setRootImpl(this);
    }
    
    public RootNamespaceImpl() {
        this(RootNamespace.getRootScope());
        this.setName("Root");
        this.setLibraryDirectory("Libraries");
        this.resetModelNamespace();
    }
    
    @Override
    public RootNamespace getSelf() {
        return (RootNamespace)this.self;
    }
    
    public void resetModelNamespace() {
        ModelNamespace modelNamespace = new ModelNamespace();
        modelNamespace.setImpl(new ModelNamespaceImpl(modelNamespace));
        modelNamespace.setName("Model");               
        this.setModelNamespace(modelNamespace);
        this.setMapping(null);
    }
    
    protected void setModelNamespace(ModelNamespace modelNamespace) {
        RootNamespace self = this.getSelf();
        
        // NOTE: The following insures that there is always a non-null member list.
        Collection<Member> members = self.getMember();
        
        if (this.modelNamespace != null) {
            self.getOwnedMember().remove(this.modelNamespace);
            members.remove(this.modelNamespace);
        }        
        this.modelNamespace = modelNamespace;
        self.addOwnedMember(modelNamespace);
        self.addMember(modelNamespace);
        modelNamespace.setNamespace(self);
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
