
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

public class NamedTuple extends Tuple {

	private ArrayList<NamedExpression> expressions = new ArrayList<NamedExpression>();

	public void addExpression(NamedExpression expression) {
		this.expressions.add(expression);
	} // addExpression

	public ArrayList<NamedExpression> getExpressions() {
		return this.expressions;
	} // getExpressions

	public void print(String prefix) {
		super.print(prefix);

		for (NamedExpression expr : this.getExpressions()) {
			expr.printChild(prefix);
		}
	} // print

} // NamedTuple
