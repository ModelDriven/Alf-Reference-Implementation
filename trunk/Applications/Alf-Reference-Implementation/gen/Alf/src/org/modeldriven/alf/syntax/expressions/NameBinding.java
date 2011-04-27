
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.NameBindingImpl;

/**
 * An unqualified name, optionally with a template binding.
 **/

public class NameBinding extends SyntaxElement {

	public NameBinding() {
		this.impl = new NameBindingImpl(this);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		TemplateBinding binding = this.getBinding();
		if (binding != null) {
			binding.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		s.append(" name:");
		s.append(this.getName());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		TemplateBinding binding = this.getBinding();
		if (binding != null) {
			System.out.println(prefix + " binding:");
			binding.print(prefix + "  ");
		}
	}
} // NameBinding
