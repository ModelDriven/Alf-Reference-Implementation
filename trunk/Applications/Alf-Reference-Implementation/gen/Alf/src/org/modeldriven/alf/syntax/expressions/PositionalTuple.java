
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

public class PositionalTuple extends Tuple {

	private ArrayList<Expression> expressions = new ArrayList<Expression>();

	public void addExpression(Expression expression) {
		this.expressions.add(expression);
	} // addExpression

	public ArrayList<Expression> getExpressions() {
		return this.expressions;
	} // getExpressions

	public void print(String prefix) {
		super.print(prefix);

		for (Expression expr : this.getExpressions()) {
			expr.printChild(prefix);
		}

	} // print

} // PositionalTuple
