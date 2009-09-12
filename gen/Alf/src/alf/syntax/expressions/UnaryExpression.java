
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

public class UnaryExpression extends Expression {

	private String operator = "";
	private Expression operand = null;

	public UnaryExpression(String operator, Expression operand) {
		this.operator = operator;
		this.operand = operand;
	} // UnaryExpression

	public String getOperator() {
		return this.operator;
	} // getOperator

	public Expression getOperand() {
		return this.operand;
	} // getOperand

	public String toString() {
		return super.toString() + " operator:" + this.getOperator();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getOperand().printChild(prefix);
	} // print

} // UnaryExpression
