
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

import org.modeldriven.alf.syntax.statements.impl.NonFinalClauseImpl;

/**
 * A clause of an if statement with a conditional expression and a sequence of
 * statements that may be executed if the condition is true.
 **/

public class NonFinalClause extends SyntaxElement {

	private Expression condition = null;
	private Block body = null;

	public NonFinalClause() {
		this.impl = new NonFinalClauseImpl(this);
	}

	public NonFinalClauseImpl getImpl() {
		return (NonFinalClauseImpl) this.impl;
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
	 * The assignments before the body of a non-final clause are the assignments
	 * after the condition.
	 **/
	public boolean nonFinalClauseAssignmentsBeforeBody() {
		return this.getImpl().nonFinalClauseAssignmentsBeforeBody();
	}

	/**
	 * If a name is unassigned before the condition expression of a non-final
	 * clause, then it must be unassigned after that expression (i.e., new local
	 * names may not be defined in the condition).
	 **/
	public boolean nonFinalClauseConditionLocalNames() {
		return this.getImpl().nonFinalClauseConditionLocalNames();
	}

	/**
	 * The condition of a non-final clause must have type Boolean and a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean nonFinalClauseConditionType() {
		return this.getImpl().nonFinalClauseConditionType();
	}

	/**
	 * The assignments before a non-final clause are the assignments before the
	 * condition of the clause.
	 **/
	public ArrayList<AssignedSource> assignmentsBefore() {
		return this.getImpl().assignmentsBefore();
	}

	/**
	 * The assignments after a non-final clause are the assignments after the
	 * block of the clause.
	 **/
	public ArrayList<AssignedSource> assignmentsAfter() {
		return this.getImpl().assignmentsAfter();
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
} // NonFinalClause
