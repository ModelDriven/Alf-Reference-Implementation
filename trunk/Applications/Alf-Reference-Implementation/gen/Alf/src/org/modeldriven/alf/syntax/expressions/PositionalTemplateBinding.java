
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

public class PositionalTemplateBinding extends TemplateBinding {

	private ArrayList<QualifiedName> argumentNames = new ArrayList<QualifiedName>();

	public void addArgumentName(QualifiedName argumentName) {
		this.argumentNames.add(argumentName);
	} // addArgumentName

	public ArrayList<QualifiedName> getArgumentNames() {
		return this.argumentNames;
	} // getArgumentNames

	public String toString() {
		StringBuffer s = new StringBuffer();
		ArrayList<QualifiedName> arguments = this.getArgumentNames();
		int n = arguments.size();

		for (int i = 0; i < n; i++) {
			s.append(arguments.get(i).toString());
			if (i < n - 1) {
				s.append(",");
			}
		}

		return "<" + s + ">";
	} // toString

} // PositionalTemplateBinding
