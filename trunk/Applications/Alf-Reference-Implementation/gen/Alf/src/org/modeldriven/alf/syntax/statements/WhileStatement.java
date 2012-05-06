
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

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

import org.modeldriven.alf.syntax.statements.impl.WhileStatementImpl;

/**
 * A looping statement for which the continuation condition is first tested
 * before the first iteration.
 **/

public class WhileStatement extends Statement {

	public WhileStatement() {
		this.impl = new WhileStatementImpl(this);
	}

	public WhileStatement(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public WhileStatement(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public WhileStatementImpl getImpl() {
		return (WhileStatementImpl) this.impl;
	}

	public Block getBody() {
		return this.getImpl().getBody();
	}

	public void setBody(Block body) {
		this.getImpl().setBody(body);
	}

	public Expression getCondition() {
		return this.getImpl().getCondition();
	}

	public void setCondition(Expression condition) {
		this.getImpl().setCondition(condition);
	}

	/**
	 * The assignments before the condition expression of a while statement are
	 * the same as the assignments before the while statement. The assignments
	 * before the block of the while statement are the same as the assignments
	 * after the condition expression.
	 **/
	public boolean whileStatementAssignmentsBefore() {
		return this.getImpl().whileStatementAssignmentsBefore();
	}

	/**
	 * If a name is assigned before the block, but the assigned source for the
	 * name after the block is different than before the block, then the
	 * assigned source of the name after the while statement is the while
	 * statement. Otherwise it is the same as before the block. If a name is
	 * unassigned before the block of a while statement, then it is unassigned
	 * after the while statement, even if it is assigned after the block.
	 **/
	public boolean whileStatementAssignmentsAfter() {
		return this.getImpl().whileStatementAssignmentsAfter();
	}

	/**
	 * The condition expression of a while statement must have type Boolean and
	 * a multiplicity upper bound of 1.
	 **/
	public boolean whileStatementCondition() {
		return this.getImpl().whileStatementCondition();
	}

	/**
	 * The enclosing statement for all statements in the body of a while
	 * statement are the while statement.
	 **/
	public boolean whileStatementEnclosedStatements() {
		return this.getImpl().whileStatementEnclosedStatements();
	}

	public void _deriveAll() {
		super._deriveAll();
		Block body = this.getBody();
		if (body != null) {
			body.deriveAll();
		}
		Expression condition = this.getCondition();
		if (condition != null) {
			condition.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.whileStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"whileStatementAssignmentsBefore", this));
		}
		if (!this.whileStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"whileStatementAssignmentsAfter", this));
		}
		if (!this.whileStatementCondition()) {
			violations.add(new ConstraintViolation("whileStatementCondition",
					this));
		}
		if (!this.whileStatementEnclosedStatements()) {
			violations.add(new ConstraintViolation(
					"whileStatementEnclosedStatements", this));
		}
		Block body = this.getBody();
		if (body != null) {
			body.checkConstraints(violations);
		}
		Expression condition = this.getCondition();
		if (condition != null) {
			condition.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Block body = this.getBody();
		if (body != null) {
			System.out.println(prefix + " body:");
			body.print(prefix + "  ", includeDerived);
		}
		Expression condition = this.getCondition();
		if (condition != null) {
			System.out.println(prefix + " condition:");
			condition.print(prefix + "  ", includeDerived);
		}
	}
} // WhileStatement
