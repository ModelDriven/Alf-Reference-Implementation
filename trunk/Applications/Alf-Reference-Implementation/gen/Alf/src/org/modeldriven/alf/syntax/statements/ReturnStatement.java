
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.Parser;
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
import java.util.TreeSet;

import org.modeldriven.alf.syntax.statements.impl.ReturnStatementImpl;

/**
 * A statement that provides a value for the return parameter of an activity.
 **/

public class ReturnStatement extends Statement {

	public ReturnStatement() {
		this.impl = new ReturnStatementImpl(this);
	}

	public ReturnStatement(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ReturnStatement(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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
	 * If the behavior containing the return statement has a return parameter,
	 * then the return statement must have an expression, and the expression
	 * must be assignable to that return parameter.
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

	public void _deriveAll() {
		this.getBehavior();
		super._deriveAll();
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
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
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			ElementReference behavior = this.getBehavior();
			if (behavior != null) {
				System.out.println(prefix + " /behavior:"
						+ behavior.toString(includeDerived));
			}
		}
	}
} // ReturnStatement
