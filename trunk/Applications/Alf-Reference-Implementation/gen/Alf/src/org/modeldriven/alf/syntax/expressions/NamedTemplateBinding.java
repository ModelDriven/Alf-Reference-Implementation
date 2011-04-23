
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

import org.modeldriven.alf.syntax.expressions.impl.NamedTemplateBindingImpl;

/**
 * A template binding in which the arguments are matched to formal template
 * parameters by name.
 **/

public class NamedTemplateBinding extends TemplateBinding {

	public NamedTemplateBinding() {
		this.impl = new NamedTemplateBindingImpl(this);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		for (TemplateParameterSubstitution _substitution : this
				.getSubstitution()) {
			_substitution.checkConstraints(violations);
		}
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<TemplateParameterSubstitution> substitution = this
				.getSubstitution();
		if (substitution != null) {
			if (substitution.size() > 0) {
				System.out.println(prefix + " substitution:");
			}
			for (TemplateParameterSubstitution _substitution : substitution) {
				if (_substitution != null) {
					_substitution.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // NamedTemplateBinding
