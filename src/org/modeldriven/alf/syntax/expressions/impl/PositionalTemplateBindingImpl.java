
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
 * parameters in order by position.
 **/

public class PositionalTemplateBindingImpl extends TemplateBindingImpl {

    private Collection<QualifiedName> argumentName = new ArrayList<QualifiedName>();

	public PositionalTemplateBindingImpl(PositionalTemplateBinding self) {
		super(self);
	}

	@Override
	public PositionalTemplateBinding getSelf() {
		return (PositionalTemplateBinding) this.self;
	}
	
    public Collection<QualifiedName> getArgumentName() {
        return this.argumentName;
    }

    public void setArgumentName(Collection<QualifiedName> argumentName) {
        this.argumentName = argumentName;
    }

    public void addArgumentName(QualifiedName argumentName) {
        this.argumentName.add(argumentName);
    }

    @Override
	public String toString() {
	    StringBuffer s = new StringBuffer("<");
	    PositionalTemplateBinding self = this.getSelf();
	    String separator = "";
	    for (QualifiedName q: self.getArgumentName()) {
	        s.append(separator);
	        s.append(q.getPathName());
	        separator = ",";
	    }
	    s.append(">");
	    return s.toString();
	}

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        PositionalTemplateBinding self = this.getSelf();
        for (QualifiedName argumentName: self.getArgumentName()) {
            argumentName.getImpl().setCurrentScope(currentScope);
        }
    }

} // PositionalTemplateBindingImpl
