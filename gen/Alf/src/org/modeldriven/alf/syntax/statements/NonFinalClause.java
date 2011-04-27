
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

import org.modeldriven.alf.syntax.statements.impl.NonFinalClauseImpl;

/**
 * A clause of an if statement with a conditional expression and a sequence of
 * statements that may be executed if the condition is true.
 **/

public class NonFinalClause extends SyntaxElement {

	public NonFinalClause() {
		this.impl = new NonFinalClauseImpl(this);
	}

	public NonFinalClauseImpl getImpl() {
		return (NonFinalClauseImpl) this.impl;
	}

	public Expression getCondition() {
		return this.getImpl().getCondition();
	}

	public void setCondition(Expression condition) {
		this.getImpl().setCondition(condition);
	}

	public Block getBody() {
		return this.getImpl().getBody();
	}

	public void setBody(Block body) {
		this.getImpl().setBody(body);
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
	public Collection<AssignedSource> assignmentsBefore() {
		return this.getImpl().assignmentsBefore();
	}

	/**
	 * The assignments after a non-final clause are the assignments after the
	 * block of the clause.
	 **/
	public Collection<AssignedSource> assignmentsAfter() {
		return this.getImpl().assignmentsAfter();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.nonFinalClauseAssignmentsBeforeBody()) {
			violations.add(new ConstraintViolation(
					"nonFinalClauseAssignmentsBeforeBody", this));
		}
		if (!this.nonFinalClauseConditionLocalNames()) {
			violations.add(new ConstraintViolation(
					"nonFinalClauseConditionLocalNames", this));
		}
		if (!this.nonFinalClauseConditionType()) {
			violations.add(new ConstraintViolation(
					"nonFinalClauseConditionType", this));
		}
		Expression condition = this.getCondition();
		if (condition != null) {
			condition.checkConstraints(violations);
		}
		Block body = this.getBody();
		if (body != null) {
			body.checkConstraints(violations);
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
		Expression condition = this.getCondition();
		if (condition != null) {
			System.out.println(prefix + " condition:");
			condition.print(prefix + "  ");
		}
		Block body = this.getBody();
		if (body != null) {
			System.out.println(prefix + " body:");
			body.print(prefix + "  ");
		}
	}
} // NonFinalClause
