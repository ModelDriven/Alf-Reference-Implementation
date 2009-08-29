
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.behavioral;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class ForVariableDefinition extends Node {

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
