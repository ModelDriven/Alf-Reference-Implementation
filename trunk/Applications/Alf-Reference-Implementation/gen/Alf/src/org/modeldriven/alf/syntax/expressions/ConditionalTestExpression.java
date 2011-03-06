
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

import org.modeldriven.alf.syntax.expressions.impl.ConditionalTestExpressionImpl;

/**
 * An expression that uses the value of one operand expression to condition the
 * evaluation of one of two other operand expressions.
 **/

public class ConditionalTestExpression extends Expression {

	public ConditionalTestExpression() {
		this.impl = new ConditionalTestExpressionImpl(this);
	}

	public ConditionalTestExpressionImpl getImpl() {
		return (ConditionalTestExpressionImpl) this.impl;
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

	public Expression getOperand3() {
		return this.getImpl().getOperand3();
	}

	public void setOperand3(Expression operand3) {
		this.getImpl().setOperand3(operand3);
	}

	/**
	 * The type of a conditional-test operator expression is the effective
	 * common ancestor (if one exists) of the types of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionTypeDerivation() {
		return this.getImpl().conditionalTestExpressionTypeDerivation();
	}

	/**
	 * The multiplicity lower bound of a conditional-test operator expression is
	 * the minimum of the multiplicity lower bounds of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionLowerDerivation() {
		return this.getImpl().conditionalTestExpressionLowerDerivation();
	}

	/**
	 * The multiplicity upper bound of a conditional-test operator expression is
	 * the maximum of the multiplicity upper bounds of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionUpperDerivation() {
		return this.getImpl().conditionalTestExpressionUpperDerivation();
	}

	/**
	 * The first operand expression of a conditional-test expression must be of
	 * type Boolean and have a multiplicity upper bound of 1.
	 **/
	public boolean conditionalTestExpressionCondition() {
		return this.getImpl().conditionalTestExpressionCondition();
	}

	/**
	 * The assignments before the first operand expression of a conditional-test
	 * expression are the same as those before the conditional-test expression.
	 * The assignments before the second and third operand expressions are the
	 * same as those after the first operand expression.
	 **/
	public boolean conditionalTestExpressionAssignmentsBefore() {
		return this.getImpl().conditionalTestExpressionAssignmentsBefore();
	}

	/**
	 * If a name is unassigned after the first operand expression and has an
	 * assigned source after one of the other operand expression, then it must
	 * have an assigned source after both of those expressions.
	 **/
	public boolean conditionalTestExpressionAssignmentsAfter() {
		return this.getImpl().conditionalTestExpressionAssignmentsAfter();
	}

	/**
	 * Returns unchanged all assignments for local names that are not reassigned
	 * in either the second or third operand expressions. Any local names that
	 * have different assignments after the second and third operand expressions
	 * are adjusted to have the conditional-test expression as their assigned
	 * source.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
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
		Expression operand3 = this.getOperand3();
		if (operand3 != null) {
			System.out.println(prefix + " operand3:");
			operand3.print(prefix + "  ");
		}
	}
} // ConditionalTestExpression
