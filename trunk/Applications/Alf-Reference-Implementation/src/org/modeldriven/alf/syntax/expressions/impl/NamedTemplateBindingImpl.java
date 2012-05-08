
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A template binding in which the arguments are matched to formal template
 * parameters by name.
 **/

public class NamedTemplateBindingImpl extends TemplateBindingImpl {

    private Collection<TemplateParameterSubstitution> substitution = 
        new ArrayList<TemplateParameterSubstitution>();

    public NamedTemplateBindingImpl(NamedTemplateBinding self) {
        super(self);
    }

    @Override
    public NamedTemplateBinding getSelf() {
        return (NamedTemplateBinding) this.self;
    }

    public Collection<TemplateParameterSubstitution> getSubstitution() {
        return this.substitution;
    }

    public void setSubstitution(
            Collection<TemplateParameterSubstitution> substitution) {
        this.substitution = substitution;
    }

    public void addSubstitution(TemplateParameterSubstitution substitution) {
        this.substitution.add(substitution);
    }
    
    /*
     * Helper Methods
     */

    @Override
    public String toString(boolean includeDerived) {
        StringBuilder s = new StringBuilder("<");
        NamedTemplateBinding self = this.getSelf();
        String separator = "";
        for (TemplateParameterSubstitution p: self.getSubstitution()) {
            s.append(separator);
            s.append(p.getParameterName());
            s.append("=>");
            QualifiedName argumentName = p.getArgumentName();
            s.append(argumentName == null? "any": argumentName.getPathName());
            separator = ",";
        }
        s.append(">");
        return s.toString();
    }
    
    /*
     * Helper Methods 
     */
    
    public Collection<QualifiedName> getArgumentName() {
        Collection<QualifiedName> argumentNames = new ArrayList<QualifiedName>();
        for (TemplateParameterSubstitution substitution: this.getSelf().getSubstitution()) {
            argumentNames.add(substitution.getArgumentName());
        }
        return argumentNames;
    }
    
    @Override
    public List<ElementReference> getArgumentReferents(
            List<ElementReference> templateParameters,
            NamespaceDefinition currentScope) {
        Collection<TemplateParameterSubstitution> substitutions = 
            this.getSelf().getSubstitution();
        List<ElementReference> argumentReferents = new ArrayList<ElementReference>();
        for (ElementReference templateParameter: templateParameters) {
            String parameterName = templateParameter.getImpl().getName();
            for (TemplateParameterSubstitution substitution: substitutions) {
                String name = substitution.getParameterName();
                if (name != null && name.equals(parameterName)) {
                    QualifiedName argumentName = substitution.getArgumentName();
                    if (argumentName == null) {
                        argumentReferents.add(null);
                    } else {
                        argumentName.getImpl().setCurrentScope(currentScope);
                        ElementReference argumentReferent = 
                            argumentName.getImpl().getClassifierReferent();
                        if (argumentReferent != null) {
                            argumentReferents.add(argumentReferent);
                        }
                    }
                    break;
                }
            }
        }
        return argumentReferents;
    }

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        NamedTemplateBinding self = this.getSelf();
        for (TemplateParameterSubstitution substitution: self.getSubstitution()) {
            substitution.getImpl().setCurrentScope(currentScope);
        }        
    }

    @Override
    public TemplateBinding update(List<ElementReference> templateParameters,
            List<ElementReference> templateArguments) {
        NamedTemplateBinding templateBinding = new NamedTemplateBinding();
        for (TemplateParameterSubstitution substitution: this.getSelf().getSubstitution()) {
            templateBinding.addSubstitution(substitution.getImpl().
                    update(templateParameters, templateArguments));
        }
        return templateBinding;
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof NamedTemplateBinding) {
            NamedTemplateBinding self = this.getSelf();
            for (TemplateParameterSubstitution substitution: 
                ((NamedTemplateBinding)base).getSubstitution()) {
                self.addSubstitution
                    ((TemplateParameterSubstitution)substitution.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // NamedTemplateBindingImpl
