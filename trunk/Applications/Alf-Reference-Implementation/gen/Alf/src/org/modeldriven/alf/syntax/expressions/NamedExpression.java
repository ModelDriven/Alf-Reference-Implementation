
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

public class NamedExpression extends SyntaxNode {

	private String name = "";
	private Expression expression = null;
	private Expression index = null;

	public NamedExpression(String name, Expression index, Expression expression) {
		this.name = name;
		this.index = index;
		this.expression = expression;
	} // NamedExpression

	public NamedExpression(String name, Expression expression) {
		this(name, null, expression);
	} // NamedExpression

	public String getName() {
		return this.name;
	} // getName

	public Expression getExpression() {
		return this.expression;
	} // getExpression

	public String toString() {
		return super.toString() + " name:" + this.getName();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getExpression().printChild(prefix);
	} // print

} // NamedExpression
