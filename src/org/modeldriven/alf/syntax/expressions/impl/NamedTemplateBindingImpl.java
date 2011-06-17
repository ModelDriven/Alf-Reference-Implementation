
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
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

    private Collection<TemplateParameterSubstitution> substitution = new ArrayList<TemplateParameterSubstitution>();

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
        StringBuffer s = new StringBuffer("<");
        NamedTemplateBinding self = this.getSelf();
        String separator = "";
        for (TemplateParameterSubstitution p: self.getSubstitution()) {
            s.append(separator);
            s.append(p.getParameterName());
            s.append("=>");
            s.append(p.getArgumentName().getPathName());
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
                    argumentName.getImpl().setCurrentScope(currentScope);
                    ElementReference argumentReferent = 
                        argumentName.getImpl().getClassifierReferent();
                    if (argumentReferent != null) {
                        argumentReferents.add(argumentReferent);
                        break;
                    }
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

} // NamedTemplateBindingImpl
