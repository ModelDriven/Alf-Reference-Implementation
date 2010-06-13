
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

public class IncrementOrDecrementExpression extends Expression {

	private String operator = "";
	private boolean isPrefix = false;
	private LeftHandSide operand = null;

	public IncrementOrDecrementExpression(String operator, LeftHandSide operand) {
		this.operator = operator;
		this.operand = operand;
	} // IncrementOrDecrementExpression

	public String getOperator() {
		return this.operator;
	} // getOperator

	public LeftHandSide getOperand() {
		return this.operand;
	} // getOperand

	public void setIsPrefix() {
		this.isPrefix = true;
	} // setIsPrefix

	public boolean isPrefix() {
		return this.isPrefix;
	} // isPrefix

	public String toString() {
		return super.toString() + " operator:" + this.getOperator()
				+ " isPrefix:" + this.isPrefix();
	} // toString

	public void print(String prefix) {
		super.print(prefix);
		this.getOperand().printChild(prefix);
	} // print

} // IncrementOrDecrementExpression
