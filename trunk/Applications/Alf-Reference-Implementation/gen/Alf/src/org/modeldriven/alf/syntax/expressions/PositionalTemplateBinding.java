
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
 * parameters in order by position.
 **/

public class PositionalTemplateBinding extends TemplateBinding {

	private ArrayList<QualifiedName> argumentName = new ArrayList<QualifiedName>();

	public ArrayList<QualifiedName> getArgumentName() {
		return this.argumentName;
	}

	public void setArgumentName(ArrayList<QualifiedName> argumentName) {
		this.argumentName = argumentName;
	}

	public void addArgumentName(QualifiedName argumentName) {
		this.argumentName.add(argumentName);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (QualifiedName argumentName : this.getArgumentName()) {
			if (argumentName != null) {
				argumentName.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // PositionalTemplateBinding
