/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.units;

import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class RootNamespace extends org.modeldriven.alf.syntax.units.RootNamespace {
    
    private ModelNamespace modelScope = new ModelNamespace();
    
    public RootNamespace() {
        this.impl = new RootNamespaceImpl(this);
        this.addOwnedMember(this.modelScope);
        this.modelScope.setNamespace(this);
    }
    
    @Override
    public RootNamespaceImpl getImpl() {
        return (RootNamespaceImpl)this.impl;
    }
    
    public ModelNamespace getModelScope() {
        return this.modelScope;
    }
    
    public void setModelDirectory(String modelDirectory) {
        this.getModelScope().getImpl().setModelDirectory(modelDirectory);
    }
    
    public void setLibraryDirectory(String libraryDirectory) {
        this.getImpl().setModelDirectory(libraryDirectory);
    }
    
    public void setIsVerbose(boolean isVerbose) {
        this.getModelScope().getImpl().setIsVerbose(isVerbose);
    }
    
    @Override
    public NamespaceDefinition getModelNamespace(UnitDefinition unit) {
        NamespaceDefinition definition = unit.getDefinition();
        NamespaceDefinition modelScope = definition.getNamespace();
        if (modelScope == null) {
            // NOTE: The model scope for a unit must include the unit itself,
            // so that it can refer to itself recursively.
            modelScope = this.getModelScope();
            modelScope.getMember(); // To ensure computation of derived attributes.
            modelScope.addOwnedMember(definition);
            modelScope.addMember(definition);
            definition.setNamespace(modelScope);
        }
        return modelScope;
    }
    
}
