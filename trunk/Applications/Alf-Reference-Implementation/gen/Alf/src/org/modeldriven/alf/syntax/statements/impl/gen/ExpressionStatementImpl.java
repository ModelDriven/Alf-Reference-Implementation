
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl.gen;

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

/**
 * A statement that evaluates an expression when executed.
 **/

public class ExpressionStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private Expression expression = null;

	public ExpressionStatementImpl(ExpressionStatement self) {
		super(self);
	}

	public ExpressionStatement getSelf() {
		return (ExpressionStatement) this.self;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	/**
	 * The assignments before the expression of an expression statement are the
	 * same as the assignments before the statement.
	 **/
	public boolean expressionStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after an expression statement are the same as the
	 * assignments after its expression.
	 **/
	public boolean expressionStatementAssignmentsAfter() {
		return true;
	}

} // ExpressionStatementImpl
