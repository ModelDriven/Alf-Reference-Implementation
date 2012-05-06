
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
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
 * A looping statement for which the continuation condition is first tested
 * after the first iteration.
 **/

public class DoStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private Expression condition = null;
	private Block body = null;

	public DoStatementImpl(DoStatement self) {
		super(self);
	}

	public DoStatement getSelf() {
		return (DoStatement) this.self;
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
		return true;
	}

	/**
	 * If the assigned source for a name after the condition expression is
	 * different than before the do statement, then the assigned source of the
	 * name after the do statement is the do statement. Otherwise it is the same
	 * as before the do statement.
	 **/
	public boolean doStatementAssignmentsAfter() {
		return true;
	}

	/**
	 * The condition expression of a do statement must have type Boolean and a
	 * multiplicity upper bound of 1.
	 **/
	public boolean doStatementCondition() {
		return true;
	}

	/**
	 * The enclosing statement for all statements in the body of a do statement
	 * are the do statement.
	 **/
	public boolean doStatementEnclosedStatements() {
		return true;
	}

} // DoStatementImpl
