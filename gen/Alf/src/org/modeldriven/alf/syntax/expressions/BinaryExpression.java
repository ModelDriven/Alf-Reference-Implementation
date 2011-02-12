
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

import org.modeldriven.alf.syntax.expressions.impl.BinaryExpressionImpl;

/**
 * An expression consisting of an operator acting on two operand expressions.
 **/

public abstract class BinaryExpression extends Expression {

	private Expression operand1 = null;
	private Expression operand2 = null;
	private String operator = "";

	public BinaryExpressionImpl getImpl() {
		return (BinaryExpressionImpl) this.impl;
	}

	public Expression getOperand1() {
		return this.operand1;
	}

	public void setOperand1(Expression operand1) {
		this.operand1 = operand1;
	}

	public Expression getOperand2() {
		return this.operand2;
	}

	public void setOperand2(Expression operand2) {
		this.operand2 = operand2;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * The operands of a binary expression must both have a multiplicity upper
	 * bound of 1.
	 **/
	public boolean binaryExpressionOperandMultiplicity() {
		return this.getImpl().binaryExpressionOperandMultiplicity();
	}

	/**
	 * The assignments in the operand expressions of a binary expression must be
	 * valid (as determined by the validateAssignments helper operation).
	 **/
	public boolean binaryExpressionOperandAssignments() {
		return this.getImpl().binaryExpressionOperandAssignments();
	}

	/**
	 * In general the assignments before the operand expressions of a binary
	 * expression are the same as those before the binary expression and, if an
	 * assignment for a name is changed in one operand expression, then the
	 * assignment for that name may not change in the other operand expression.
	 * (This is overridden for conditional logical expressions.)
	 **/
	public Boolean validateAssignments() {
		return this.getImpl().validateAssignments();
	}

	/**
	 * The assignments after a binary expression include all the assignments
	 * before the expression that are not reassigned in either operand
	 * expression, plus the new assignments from each of the operand
	 * expressions.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operator:");
		s.append(this.getOperator());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression operand1 = this.getOperand1();
		if (operand1 != null) {
			operand1.print(prefix + " ");
		}
		Expression operand2 = this.getOperand2();
		if (operand2 != null) {
			operand2.print(prefix + " ");
		}
	}
} // BinaryExpression
