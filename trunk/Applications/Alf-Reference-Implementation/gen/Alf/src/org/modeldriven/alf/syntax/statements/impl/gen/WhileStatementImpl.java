
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.parser.AlfParser;

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
 * before the first iteration.
 **/

public class WhileStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	private Block body = null;
	private Expression condition = null;

	public WhileStatementImpl(WhileStatement self) {
		super(self);
	}

	public WhileStatement getSelf() {
		return (WhileStatement) this.self;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	/**
	 * The assignments before the condition expression of a while statement are
	 * the same as the assignments before the while statement. The assignments
	 * before the block of the while statement are the same as the assignments
	 * after the condition expression.
	 **/
	public boolean whileStatementAssignmentsBefore() {
		return true;
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
		return true;
	}

	/**
	 * The condition expression of a while statement must have type Boolean and
	 * a multiplicity upper bound of 1.
	 **/
	public boolean whileStatementCondition() {
		return true;
	}

	/**
	 * The enclosing statement for all statements in the body of a while
	 * statement are the while statement.
	 **/
	public boolean whileStatementEnclosedStatements() {
		return true;
	}

} // WhileStatementImpl
