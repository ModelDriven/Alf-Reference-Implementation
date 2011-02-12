
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

import org.modeldriven.alf.syntax.statements.impl.DoStatementImpl;

/**
 * A looping statement for which the continuation condition is first tested
 * after the first iteration.
 **/

public class DoStatement extends Statement {

	private Expression condition = null;
	private Block body = null;

	public DoStatement() {
		this.impl = new DoStatementImpl(this);
	}

	public DoStatementImpl getImpl() {
		return (DoStatementImpl) this.impl;
	}

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	/**
	 * The assignments before the block of a do statement are the same as the
	 * assignments before the do statement. The assignments before the condition
	 * expression of a do statement are the same assignments after the block.
	 **/
	public boolean doStatementAssignmentsBefore() {
		return this.getImpl().doStatementAssignmentsBefore();
	}

	/**
	 * If the assigned source for a name after the condition expression is
	 * different than before the do statement, then the assigned source of the
	 * name after the do statement is the do statement. Otherwise it is the same
	 * as before the do statement.
	 **/
	public boolean doStatementAssignmentsAfter() {
		return this.getImpl().doStatementAssignmentsAfter();
	}

	/**
	 * The condition expression of a do statement must have type Boolean and a
	 * multiplicity upper bound of 1.
	 **/
	public boolean doStatementCondition() {
		return this.getImpl().doStatementCondition();
	}

	/**
	 * The enclosing statement for all statements in the body of a do statement
	 * are the do statement.
	 **/
	public boolean doStatementEnclosedStatements() {
		return this.getImpl().doStatementEnclosedStatements();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Expression condition = this.getCondition();
		if (condition != null) {
			condition.print(prefix + " ");
		}
		Block body = this.getBody();
		if (body != null) {
			body.print(prefix + " ");
		}
	}
} // DoStatement
