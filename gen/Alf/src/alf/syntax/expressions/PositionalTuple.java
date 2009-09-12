
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

public class PositionalTuple extends Tuple {

	private ArrayList<Expression> expressions = new ArrayList<Expression>();

	public PositionalTuple(ArrayList<Expression> expressions) {
		this.expressions = expressions;
	} // PositionalTuple

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
