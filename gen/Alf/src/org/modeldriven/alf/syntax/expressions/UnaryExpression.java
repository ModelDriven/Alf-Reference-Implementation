
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
