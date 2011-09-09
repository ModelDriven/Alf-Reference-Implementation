
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

import org.modeldriven.alf.syntax.expressions.impl.NamedTemplateBindingImpl;

/**
 * A template binding in which the arguments are matched to formal template
 * parameters by name.
 **/

public class NamedTemplateBinding extends TemplateBinding {

	public NamedTemplateBinding() {
		this.impl = new NamedTemplateBindingImpl(this);
	}

	public NamedTemplateBinding(AlfParser parser) {
		this();
		this.setParserInfo(parser.getFileName(), parser.getLine(), parser
				.getColumn());
	}

	public NamedTemplateBinding(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public NamedTemplateBindingImpl getImpl() {
		return (NamedTemplateBindingImpl) this.impl;
	}

	public Collection<TemplateParameterSubstitution> getSubstitution() {
		return this.getImpl().getSubstitution();
	}

	public void setSubstitution(
			Collection<TemplateParameterSubstitution> substitution) {
		this.getImpl().setSubstitution(substitution);
	}

	public void addSubstitution(TemplateParameterSubstitution substitution) {
		this.getImpl().addSubstitution(substitution);
	}

	public void _deriveAll() {
		super._deriveAll();
		Collection<TemplateParameterSubstitution> substitution = this
				.getSubstitution();
		if (substitution != null) {
			for (Object _substitution : substitution.toArray()) {
				((TemplateParameterSubstitution) _substitution).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		Collection<TemplateParameterSubstitution> substitution = this
				.getSubstitution();
		if (substitution != null) {
			for (Object _substitution : substitution.toArray()) {
				((TemplateParameterSubstitution) _substitution)
						.checkConstraints(violations);
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
		Collection<TemplateParameterSubstitution> substitution = this
				.getSubstitution();
		if (substitution != null && substitution.size() > 0) {
			System.out.println(prefix + " substitution:");
			for (Object _object : substitution.toArray()) {
				TemplateParameterSubstitution _substitution = (TemplateParameterSubstitution) _object;
				if (_substitution != null) {
					_substitution.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // NamedTemplateBinding
