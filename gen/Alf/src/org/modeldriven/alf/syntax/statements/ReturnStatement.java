
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.ReturnStatementImpl;

/**
 * A statement that provides a value for the return parameter of an activity.
 **/

public class ReturnStatement extends Statement {

	public ReturnStatement() {
		this.impl = new ReturnStatementImpl(this);
	}

	public ReturnStatementImpl getImpl() {
		return (ReturnStatementImpl) this.impl;
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public ElementReference getBehavior() {
		return this.getImpl().getBehavior();
	}

	public void setBehavior(ElementReference behavior) {
		this.getImpl().setBehavior(behavior);
	}

	/**
	 * The behavior containing the return statement must have a return
	 * parameter. The expression of the return statement must be assignable to
	 * that return parameter.
	 **/
	public boolean returnStatementContext() {
		return this.getImpl().returnStatementContext();
	}

	/**
	 * The assignments before the expression of a return statement are the same
	 * as the assignments before the statement.
	 **/
	public boolean returnStatementAssignmentsBefore() {
		return this.getImpl().returnStatementAssignmentsBefore();
	}

	/**
	 * The assignments after a return statement are the same as the assignments
	 * after the expression of the return statement.
	 **/
	public boolean returnStatementAssignmentsAfter() {
		return this.getImpl().returnStatementAssignmentsAfter();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.returnStatementContext()) {
			violations.add(new ConstraintViolation("returnStatementContext",
					this));
		}
		if (!this.returnStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"returnStatementAssignmentsBefore", this));
		}
		if (!this.returnStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"returnStatementAssignmentsAfter", this));
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ");
		}
		ElementReference behavior = this.getBehavior();
		if (behavior != null) {
			System.out.println(prefix + " /behavior:" + behavior);
		}
	}
} // ReturnStatement
