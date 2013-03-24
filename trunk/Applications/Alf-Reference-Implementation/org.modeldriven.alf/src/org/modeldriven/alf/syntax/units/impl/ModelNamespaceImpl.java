/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.syntax.units.impl;

import java.util.List;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

public class ModelNamespaceImpl extends PackageDefinitionImpl {
    
    public ModelNamespaceImpl(ModelNamespace self) {
        super(self);
    }
    
    @Override
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
    
    @Override
    public QualifiedName getQualifiedName() {
        QualifiedName qualifiedName = new QualifiedName();
        qualifiedName.getImpl().setCurrentScope(this.getSelf());
        return qualifiedName;
    }
    
    @Override
    public ModelNamespace getModelScope() {
        return this.getSelf();
    }
    
    @Override
    public UnitDefinition resolveUnit(QualifiedName qualifiedName) {
        return null;
    }
    
    public String makeBoundElementNameFor(
            ElementReference templateReferent, List<ElementReference> templateArguments) {
        return this.makeBoundElementNameFor(
                templateReferent.getImpl().getName(), templateArguments);
    }

    protected String makeBoundElementNameFor(
            String templateName,
            List<ElementReference> templateArguments) {
        StringBuilder name = new StringBuilder("$$");
        name.append(templateName);
        name.append("__");
        for (ElementReference argument: templateArguments) {
            String argumentName = argument == null? "any":
                argument.getImpl().asNamespace().getImpl().getQualifiedName().getPathName();
            name.append(argumentName.replace("::", "$"));
            name.append("_");
        }
        name.append("_");
        return name.toString();
    }
    
    public ElementReference getInstantiationNamespaceFor(
            ElementReference templateReferent) {
        return templateReferent.getImpl().getNamespace();
    }

}
