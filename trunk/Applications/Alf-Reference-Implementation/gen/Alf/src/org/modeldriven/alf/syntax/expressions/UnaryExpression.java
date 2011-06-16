
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.UnaryExpressionImpl;

/**
 * An expression consisting of an operator acting on a single operand
 * expression.
 **/

public abstract class UnaryExpression extends Expression {

	public UnaryExpressionImpl getImpl() {
		return (UnaryExpressionImpl) this.impl;
	}

	public String getOperator() {
		return this.getImpl().getOperator();
	}

	public void setOperator(String operator) {
		this.getImpl().setOperator(operator);
	}

	public Expression getOperand() {
		return this.getImpl().getOperand();
	}

	public void setOperand(Expression operand) {
		this.getImpl().setOperand(operand);
	}

	/**
	 * The assignments before the operand of a unary expression are the same as
	 * those before the unary expression.
	 **/
	public boolean unaryExpressionAssignmentsBefore() {
		return this.getImpl().unaryExpressionAssignmentsBefore();
	}

	/**
	 * By default, the assignments after a unary expression are the same as
	 * those after its operand expression.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.unaryExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"unaryExpressionAssignmentsBefore", this));
		}
		Expression operand = this.getOperand();
		if (operand != null) {
			operand.checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		s.append(" operator:");
		s.append(this.getOperator());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression operand = this.getOperand();
		if (operand != null) {
			System.out.println(prefix + " operand:");
			operand.print(prefix + "  ");
		}
	}
} // UnaryExpression
