
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.expressions;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

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
