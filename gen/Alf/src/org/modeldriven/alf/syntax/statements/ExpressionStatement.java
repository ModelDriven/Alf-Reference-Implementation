
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

import org.modeldriven.alf.syntax.statements.impl.ExpressionStatementImpl;

/**
 * A statement that evaluates an expression when executed.
 **/

public class ExpressionStatement extends Statement {

	public ExpressionStatement() {
		this.impl = new ExpressionStatementImpl(this);
	}

	public ExpressionStatement(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ExpressionStatement(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public ExpressionStatementImpl getImpl() {
		return (ExpressionStatementImpl) this.impl;
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	/**
	 * The assignments before the expression of an expression statement are the
	 * same as the assignments before the statement.
	 **/
	public boolean expressionStatementAssignmentsBefore() {
		return this.getImpl().expressionStatementAssignmentsBefore();
	}

	/**
	 * The assignments after an expression statement are the same as the
	 * assignments after its expression.
	 **/
	public boolean expressionStatementAssignmentsAfter() {
		return this.getImpl().expressionStatementAssignmentsAfter();
	}

	public void _deriveAll() {
		super._deriveAll();
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.expressionStatementAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"expressionStatementAssignmentsBefore", this));
		}
		if (!this.expressionStatementAssignmentsAfter()) {
			violations.add(new ConstraintViolation(
					"expressionStatementAssignmentsAfter", this));
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
	}
} // ExpressionStatement
