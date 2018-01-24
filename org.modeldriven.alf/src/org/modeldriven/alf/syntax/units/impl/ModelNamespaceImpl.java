/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.ModelNamespace;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
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
    
    @Override
    public ElementReference resolveStereotype(QualifiedName name) {
        return resolveStandardStereotype(name);
    }
    
    public static ElementReference resolveStandardStereotype(QualifiedName name) {
        ElementReference standardProfile = RootNamespace.getRootScope().getStandardProfile();
        QualifiedName qualification = name.getQualification();
        String stereotypeName = name.getUnqualifiedName().getName();
        if (qualification != null && 
                !qualification.getImpl().equals(standardProfile.getImpl().getName())) {
            return null;
        }
        for (ElementReference member: standardProfile.getImpl().getOwnedMembers()) {
            if (member.getImpl().isStereotype() && 
                    member.getImpl().getName().equals(stereotypeName)) {
                return member;
            }
        }
        return null;
    }
    
    public String makeBoundElementName(
            ElementReference templateReferent, List<ElementReference> templateArguments) {
        return this.makeBoundElementName(
                templateReferent.getImpl().getName(), templateArguments);
    }

    protected String makeBoundElementName(
            String templateName,
            List<ElementReference> templateArguments) {
        StringBuilder name = new StringBuilder("$$");
        name.append(templateName);
        name.append("__");
        for (ElementReference argument: templateArguments) {
            String argumentName = argument == null || argument.getImpl().isAny() ? "any":
                argument.getImpl().getQualifiedName().getPathName().replace("::", "$");
            name.append(argumentName);
            name.append("_");
        }
        name.append("_");
        return name.toString();
    }
    
    public ElementReference getInstantiationNamespace(
            ElementReference templateReferent) {
        return templateReferent.getImpl().getNamespace();
    }

    public ElementReference getExistingBoundElement(
            ElementReference templateReferent,
            List<ElementReference> templateArguments) {
        ElementReference namespaceReference = this.getInstantiationNamespace(templateReferent);
        if (namespaceReference != null) {
            String name = this.makeBoundElementName(templateReferent, templateArguments);
            for (ElementReference member: namespaceReference.getImpl().getOwnedMembers()) {
                if (name.equals(member.getImpl().getName())) {
                    return member;
                }
            }
        }
        return null;
    }
    
    public ElementReference getEffectiveBoundElement(
            ElementReference templateReferent,
            List<ElementReference> templateParameters,
            List<ElementReference> templateArguments) {
        ElementReference namespaceReference = this.getInstantiationNamespace(templateReferent);        
        if (namespaceReference == null) {
            return null;
        } else {           
            String name = this.makeBoundElementName(templateReferent, templateArguments);
            for (ElementReference member: namespaceReference.getImpl().getOwnedMembers()) {
                if (name.equals(member.getImpl().getName())) {
                    return member;
                }
            }
            
            NamespaceDefinition instantiationNamespace = 
                    namespaceReference.getImpl().asNamespace();
            Member boundElement = templateReferent.getImpl().asNamespace().getImpl().
                    bind(name, instantiationNamespace, true,
                            templateParameters, templateArguments);
            if (boundElement == null) {
                return null;
            } else {
                boundElement.deriveAll();
                return boundElement.getImpl().getReferent();
            }
        }
    }

}
