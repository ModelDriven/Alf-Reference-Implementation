
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.behavioral;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

import java.util.ArrayList;

public class ForVariableDefinition extends SyntaxNode {

	private String variable = "";
	private Expression expression1 = null;
	private Expression expression2 = null;

	public ForVariableDefinition(String variable, Expression expression1,
			Expression expression2) {
		this.variable = variable;
		this.expression1 = expression1;
		this.expression2 = expression2;
	} // ForVariableDefinition

	public String getVariable() {
		return this.variable;
	} // getVariable

	public Expression getExpression1() {
		return this.expression1;
	} // getExpression1

	public Expression getExpression2() {
		return this.expression2;
	} // getExpression2

	public String toString() {
		return super.toString() + " variable:" + this.getVariable();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getExpression1().printChild(prefix);
		if (this.getExpression2() != null) {
			this.getExpression2().printChild(prefix);
		}
	} // print

} // ForVariableDefinition
