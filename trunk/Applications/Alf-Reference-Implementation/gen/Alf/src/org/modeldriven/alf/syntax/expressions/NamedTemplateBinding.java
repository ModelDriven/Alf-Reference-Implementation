
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

import org.modeldriven.alf.syntax.expressions.impl.NamedTemplateBindingImpl;

/**
 * A template binding in which the arguments are matched to formal template
 * parameters by name.
 **/

public class NamedTemplateBinding extends TemplateBinding {

	private ArrayList<TemplateParameterSubstitution> substitution = new ArrayList<TemplateParameterSubstitution>();

	public NamedTemplateBinding() {
		this.impl = new NamedTemplateBindingImpl(this);
	}

	public NamedTemplateBindingImpl getImpl() {
		return (NamedTemplateBindingImpl) this.impl;
	}

	public ArrayList<TemplateParameterSubstitution> getSubstitution() {
		return this.substitution;
	}

	public void setSubstitution(
			ArrayList<TemplateParameterSubstitution> substitution) {
		this.substitution = substitution;
	}

	public void addSubstitution(TemplateParameterSubstitution substitution) {
		this.substitution.add(substitution);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<TemplateParameterSubstitution> substitution = this
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
