
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;

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

import org.modeldriven.alf.syntax.expressions.impl.PositionalTemplateBindingImpl;

/**
 * A template binding in which the arguments are matched to formal template
 * parameters in order by position.
 **/

public class PositionalTemplateBinding extends TemplateBinding {

	public PositionalTemplateBinding() {
		this.impl = new PositionalTemplateBindingImpl(this);
	}

	public PositionalTemplateBinding(AlfParser parser) {
		this();
		this.setParserInfo(parser.getFileName(), parser.getLine(), parser
				.getColumn());
	}

	public PositionalTemplateBinding(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public PositionalTemplateBindingImpl getImpl() {
		return (PositionalTemplateBindingImpl) this.impl;
	}

	public Collection<QualifiedName> getArgumentName() {
		return this.getImpl().getArgumentName();
	}

	public void setArgumentName(Collection<QualifiedName> argumentName) {
		this.getImpl().setArgumentName(argumentName);
	}

	public void addArgumentName(QualifiedName argumentName) {
		this.getImpl().addArgumentName(argumentName);
	}

	public void _deriveAll() {
		super._deriveAll();
		Collection<QualifiedName> argumentName = this.getArgumentName();
		if (argumentName != null) {
			for (Object _argumentName : argumentName.toArray()) {
				((QualifiedName) _argumentName).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		Collection<QualifiedName> argumentName = this.getArgumentName();
		if (argumentName != null) {
			for (Object _argumentName : argumentName.toArray()) {
				((QualifiedName) _argumentName).checkConstraints(violations);
			}
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
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
		Collection<QualifiedName> argumentName = this.getArgumentName();
		if (argumentName != null && argumentName.size() > 0) {
			System.out.println(prefix + " argumentName:");
			for (Object _object : argumentName.toArray()) {
				QualifiedName _argumentName = (QualifiedName) _object;
				if (_argumentName != null) {
					_argumentName.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // PositionalTemplateBinding
