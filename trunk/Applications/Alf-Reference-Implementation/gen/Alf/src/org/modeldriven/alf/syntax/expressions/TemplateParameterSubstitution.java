
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.TemplateParameterSubstitutionImpl;

/**
 * A specification of the substitution of an argument type name for a template
 * parameter.
 **/

public class TemplateParameterSubstitution extends SyntaxElement {

	public TemplateParameterSubstitution() {
		this.impl = new TemplateParameterSubstitutionImpl(this);
	}

	public TemplateParameterSubstitutionImpl getImpl() {
		return (TemplateParameterSubstitutionImpl) this.impl;
	}

	public String getParameterName() {
		return this.getImpl().getParameterName();
	}

	public void setParameterName(String parameterName) {
		this.getImpl().setParameterName(parameterName);
	}

	public QualifiedName getArgumentName() {
		return this.getImpl().getArgumentName();
	}

	public void setArgumentName(QualifiedName argumentName) {
		this.getImpl().setArgumentName(argumentName);
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		QualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			argumentName.checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		s.append(" parameterName:");
		s.append(this.getParameterName());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		QualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			System.out.println(prefix + " argumentName:");
			argumentName.print(prefix + "  ");
		}
	}
} // TemplateParameterSubstitution
