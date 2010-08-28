
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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
 * A template binding in which the arguments are matched to formal template
 * parameters by name.
 **/

public class NamedTemplateBinding extends TemplateBinding {

	private ArrayList<TemplateParameterSubstitution> substitution = new ArrayList<TemplateParameterSubstitution>();

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
		for (TemplateParameterSubstitution substitution : this
				.getSubstitution()) {
			if (substitution != null) {
				substitution.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // NamedTemplateBinding
