
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
	private Expression expression = null;

	public ForVariableDefinition(String variable, Expression expression) {
		this.variable = variable;
		this.expression = expression;
	} // ForVariableDefinition

	public String getVariable() {
		return this.variable;
	} // getVariable

	public Expression getExpression() {
		return this.expression;
	} // getExpression

	public String toString() {
		return super.toString() + " variable:" + this.getVariable();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getExpression().printChild(prefix);
	} // print

} // ForVariableDefinition
