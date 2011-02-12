
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

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.ReturnStatementImpl;

/**
 * A statement that provides a value for the return parameter of an activity.
 **/

public class ReturnStatement extends Statement {

	private Expression expression = null;
	private ElementReference behavior = null; // DERIVED

	public ReturnStatement() {
		this.impl = new ReturnStatementImpl(this);
	}

	public ReturnStatementImpl getImpl() {
		return (ReturnStatementImpl) this.impl;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public ElementReference getBehavior() {
		if (this.behavior == null) {
			this.behavior = this.getImpl().deriveBehavior();
		}
		return this.behavior;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.print(prefix + " ");
		}
		ElementReference behavior = this.getBehavior();
		if (behavior != null) {
			System.out.println(prefix + " /" + behavior);
		}
	}
} // ReturnStatement
