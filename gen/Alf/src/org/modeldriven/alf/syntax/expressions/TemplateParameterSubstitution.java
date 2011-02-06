
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A specification of the substitution of an argument type name for a template
 * parameter.
 **/

public class TemplateParameterSubstitution extends SyntaxElement implements
		ITemplateParameterSubstitution {

	private String parameterName = "";
	private IQualifiedName argumentName = null;

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public IQualifiedName getArgumentName() {
		return this.argumentName;
	}

	public void setArgumentName(IQualifiedName argumentName) {
		this.argumentName = argumentName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" parameterName:");
		s.append(this.getParameterName());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			argumentName.print(prefix + " ");
		}
	}
} // TemplateParameterSubstitution
