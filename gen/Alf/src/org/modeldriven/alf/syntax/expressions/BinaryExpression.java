
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.BinaryExpressionImpl;

/**
 * An expression consisting of an operator acting on two operand expressions.
 **/

public abstract class BinaryExpression extends Expression {

	public BinaryExpressionImpl getImpl() {
		return (BinaryExpressionImpl) this.impl;
	}

	public Expression getOperand1() {
		return this.getImpl().getOperand1();
	}

	public void setOperand1(Expression operand1) {
		this.getImpl().setOperand1(operand1);
	}

	public Expression getOperand2() {
		return this.getImpl().getOperand2();
	}

	public void setOperand2(Expression operand2) {
		this.getImpl().setOperand2(operand2);
	}

	public String getOperator() {
		return this.getImpl().getOperator();
	}

	public void setOperator(String operator) {
		this.getImpl().setOperator(operator);
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
	public Collection<AssignedSource> updateAssignments() {
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
			System.out.println(prefix + " operand1:");
			operand1.print(prefix + "  ");
		}
		Expression operand2 = this.getOperand2();
		if (operand2 != null) {
			System.out.println(prefix + " operand2:");
			operand2.print(prefix + "  ");
		}
	}
} // BinaryExpression
