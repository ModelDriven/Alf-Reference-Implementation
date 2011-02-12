
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

import java.util.ArrayList;

/**
 * A tuple in which the arguments are matched to parameters by name.
 **/

public class NamedTuple extends Tuple implements INamedTuple {

	private ArrayList<INamedExpression> namedExpression = new ArrayList<INamedExpression>();

	public ArrayList<INamedExpression> getNamedExpression() {
		return this.namedExpression;
	}

	public void setNamedExpression(ArrayList<INamedExpression> namedExpression) {
		this.namedExpression = namedExpression;
	}

	public void addNamedExpression(INamedExpression namedExpression) {
		this.namedExpression.add(namedExpression);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<INamedExpression> namedExpression = this.getNamedExpression();
		if (namedExpression != null) {
			for (INamedExpression item : this.getNamedExpression()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // NamedTuple
