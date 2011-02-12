
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
 * A statement that evaluates an expression when executed.
 **/

public class ExpressionStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.StatementImpl {

	public ExpressionStatementImpl(ExpressionStatement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.ExpressionStatement getSelf() {
		return (ExpressionStatement) this.self;
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
