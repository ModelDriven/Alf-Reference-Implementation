/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class ModelNamespaceImpl extends PackageDefinitionImpl {
    
    public ModelNamespaceImpl(ModelNamespace self) {
        super(self);
    }
    
    public ModelNamespace getSelf() {
        return (ModelNamespace)this.self;
    }
    
    public NamespaceDefinition getModelNamespace(UnitDefinition unit) {
        NamespaceDefinition definition = unit.getDefinition();
        NamespaceDefinition modelScope = definition.getNamespace();
        if (modelScope == null) {
            // NOTE: The model scope for a unit must include the unit itself,
            // so that it can refer to itself recursively.
            modelScope = this.getSelf();
            modelScope.getMember(); // To ensure computation of derived attributes.
            modelScope.addOwnedMember(definition);
            modelScope.addMember(definition);
            definition.setNamespace(modelScope);
        }
        return modelScope;
    }
    
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return null;
    }

}
