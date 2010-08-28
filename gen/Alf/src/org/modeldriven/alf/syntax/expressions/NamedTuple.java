
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
 * A tuple in which the arguments are matched to parameters by name.
 **/

public class NamedTuple extends Tuple {

	private ArrayList<NamedExpression> namedExpression = new ArrayList<NamedExpression>();

	public ArrayList<NamedExpression> getNamedExpression() {
		return this.namedExpression;
	}

	public void setNamedExpression(ArrayList<NamedExpression> namedExpression) {
		this.namedExpression = namedExpression;
	}

	public void addNamedExpression(NamedExpression namedExpression) {
		this.namedExpression.add(namedExpression);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (NamedExpression namedExpression : this.getNamedExpression()) {
			if (namedExpression != null) {
				namedExpression.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // NamedTuple
