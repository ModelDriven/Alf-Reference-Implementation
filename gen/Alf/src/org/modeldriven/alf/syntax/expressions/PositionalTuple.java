
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
 * A tuple in which the arguments are matched to parameters in order by
 * position.
 **/

public class PositionalTuple extends Tuple implements IPositionalTuple {

	private ArrayList<IExpression> expression = new ArrayList<IExpression>();

	public ArrayList<IExpression> getExpression() {
		return this.expression;
	}

	public void setExpression(ArrayList<IExpression> expression) {
		this.expression = expression;
	}

	public void addExpression(IExpression expression) {
		this.expression.add(expression);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<IExpression> expression = this.getExpression();
		if (expression != null) {
			for (IExpression item : this.getExpression()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // PositionalTuple
