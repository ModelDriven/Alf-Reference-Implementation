
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class NamedTemplateBinding extends TemplateBinding {

	private ArrayList<TemplateParameterSubstitution> substitutions = new ArrayList<TemplateParameterSubstitution>();

	public void addSubstitution(TemplateParameterSubstitution substitution) {
		this.substitutions.add(substitution);
	} // addSubstitution

	public ArrayList<TemplateParameterSubstitution> getSubstitutions() {
		return this.substitutions;
	} // getSubstitutions

	public String toString() {
		StringBuffer s = new StringBuffer();
		ArrayList<TemplateParameterSubstitution> substitutions = this
				.getSubstitutions();
		int n = substitutions.size();

		for (int i = 0; i < n; i++) {
			s.append(substitutions.get(i).toString());
			if (i < n - 1) {
				s.append(",");
			}
		}

		return "<" + s + ">";
	} // toString

} // NamedTemplateBinding
