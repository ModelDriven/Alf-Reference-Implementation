
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

public class BinaryExpression extends Expression {

	private Expression operand1 = null;
	private Expression operand2 = null;
	private String operator = "";

	public BinaryExpression(Expression operand1, String operator,
			Expression operand2) {
		this.operand1 = operand1;
		this.operator = operator;
		this.operand2 = operand2;
	} // BinaryExpression

	public Expression getOperand1() {
		return this.operand1;
	} // getOperand1

	public Expression getOperand2() {
		return this.operand2;
	} // getOperand2

	public String getOperator() {
		return this.operator;
	} // getOperator

	public String toString() {
		return super.toString() + " operator:" + this.getOperator();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getOperand1().printChild(prefix);
		this.getOperand2().printChild(prefix);
	} // print

} // BinaryExpression
