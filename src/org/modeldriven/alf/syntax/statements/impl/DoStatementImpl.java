
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A looping statement for which the continuation condition is first tested
 * after the first iteration.
 **/

public class DoStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.StatementImpl {

	public DoStatementImpl(DoStatement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.DoStatement getSelf() {
		return (DoStatement) this.self;
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
