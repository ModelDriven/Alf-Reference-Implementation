
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

import org.modeldriven.alf.syntax.expressions.impl.NameBindingImpl;

/**
 * An unqualified name, optionally with a template binding.
 **/

public class NameBinding extends SyntaxElement {

	public NameBinding() {
		this.impl = new NameBindingImpl(this);
	}

	public NameBinding(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public NameBinding(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public NameBindingImpl getImpl() {
		return (NameBindingImpl) this.impl;
	}

	public TemplateBinding getBinding() {
		return this.getImpl().getBinding();
	}

	public void setBinding(TemplateBinding binding) {
		this.getImpl().setBinding(binding);
	}

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public void _deriveAll() {
		super._deriveAll();
		TemplateBinding binding = this.getBinding();
		if (binding != null) {
			binding.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		TemplateBinding binding = this.getBinding();
		if (binding != null) {
			binding.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" name:");
		s.append(this.getName());
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
		TemplateBinding binding = this.getBinding();
		if (binding != null) {
			System.out.println(prefix + " binding:");
			binding.print(prefix + "  ", includeDerived);
		}
	}
} // NameBinding
