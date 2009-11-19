
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

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
