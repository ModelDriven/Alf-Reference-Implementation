
/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.ExpressionStatementImpl;

/**
 * A statement that evaluates an expression when executed.
 **/

public class ExpressionStatement extends Statement {

	public ExpressionStatement() {
		this.impl = new ExpressionStatementImpl(this);
	}

	public ExpressionStatement(Parser parser) {
		this();
		this.init(parser);
	}

	public ExpressionStatement(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getExpression());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Expression expression = this.getExpression();
		if (expression != null) {
			expression.deriveAll();
		}
	}

	@Override
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " expression:");
			expression.print(prefix + "  ", includeDerived);
		}
	}
} // ExpressionStatement
