
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import org.modeldriven.alf.syntax.expressions.impl.TemplateParameterSubstitutionImpl;

/**
 * A specification of the substitution of an argument type name for a template
 * parameter.
 **/

public class TemplateParameterSubstitution extends SyntaxElement {

	public TemplateParameterSubstitution() {
		this.impl = new TemplateParameterSubstitutionImpl(this);
	}

	public TemplateParameterSubstitution(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public TemplateParameterSubstitution(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public void _deriveAll() {
		super._deriveAll();
		QualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			argumentName.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		QualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			argumentName.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" parameterName:");
		s.append(this.getParameterName());
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		QualifiedName argumentName = this.getArgumentName();
		if (argumentName != null) {
			System.out.println(prefix + " argumentName:");
			argumentName.print(prefix + "  ", includeDerived);
		}
	}
} // TemplateParameterSubstitution
