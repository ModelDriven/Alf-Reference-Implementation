
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;

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
    public String toString() {
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

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        NamedTemplateBinding self = this.getSelf();
        for (TemplateParameterSubstitution substitution: self.getSubstitution()) {
            substitution.getImpl().setCurrentScope(currentScope);
        }        
    }

} // NamedTemplateBindingImpl
