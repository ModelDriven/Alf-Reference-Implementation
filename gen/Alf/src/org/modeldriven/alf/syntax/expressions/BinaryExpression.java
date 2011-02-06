
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An expression consisting of an operator acting on two operand expressions.
 **/

public abstract class BinaryExpression extends Expression implements
		IBinaryExpression {

	private IExpression operand1 = null;
	private IExpression operand2 = null;
	private String operator = "";

	public IExpression getOperand1() {
		return this.operand1;
	}

	public void setOperand1(IExpression operand1) {
		this.operand1 = operand1;
	}

	public IExpression getOperand2() {
		return this.operand2;
	}

	public void setOperand2(IExpression operand2) {
		this.operand2 = operand2;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operator:");
		s.append(this.getOperator());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExpression operand1 = this.getOperand1();
		if (operand1 != null) {
			operand1.print(prefix + " ");
		}
		IExpression operand2 = this.getOperand2();
		if (operand2 != null) {
			operand2.print(prefix + " ");
		}
	}
} // BinaryExpression
