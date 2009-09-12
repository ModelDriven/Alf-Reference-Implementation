
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

public abstract class InvocationExpression extends Expression {

	private Tuple tuple = null;

	public InvocationExpression(Tuple tuple) {
		this.tuple = tuple;
	} // InvocationExpression

	public Tuple getTuple() {
		return this.tuple;
	} // getTuple

	public void print(String prefix) {
		super.print(prefix);
		this.printTarget(prefix);
		this.getTuple().printChild(prefix);
	} // print

	public abstract void printTarget(String prefix);
} // InvocationExpression
