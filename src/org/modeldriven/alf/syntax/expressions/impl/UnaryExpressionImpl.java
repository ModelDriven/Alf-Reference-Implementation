
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An expression consisting of an operator acting on a single operand
 * expression.
 **/

public abstract class UnaryExpressionImpl extends ExpressionImpl {

	private String operator = "";
	private Expression operand = null;

	public UnaryExpressionImpl(UnaryExpression self) {
		super(self);
	}

	public UnaryExpression getSelf() {
		return (UnaryExpression) this.self;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Expression getOperand() {
		return this.operand;
	}

	public void setOperand(Expression operand) {
		this.operand = operand;
	}

	/**
	 * The assignments before the operand of a unary expression are the same as
	 * those before the unary expression.
	 **/
	public boolean unaryExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * By default, the assignments after a unary expression are the same as
	 * those after its operand expression.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // UnaryExpressionImpl
