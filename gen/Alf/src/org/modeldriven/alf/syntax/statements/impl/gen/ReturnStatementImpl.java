
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * A statement that provides a value for the return parameter of an activity.
 **/

public class ReturnStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	public ReturnStatementImpl(ReturnStatement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.ReturnStatement getSelf() {
		return (ReturnStatement) this.self;
	}

	public ElementReference deriveBehavior() {
		return null; // STUB
	}

	/**
	 * The behavior containing the return statement must have a return
	 * parameter. The expression of the return statement must be assignable to
	 * that return parameter.
	 **/
	public boolean returnStatementContext() {
		return true;
	}

	/**
	 * The assignments before the expression of a return statement are the same
	 * as the assignments before the statement.
	 **/
	public boolean returnStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after a return statement are the same as the assignments
	 * after the expression of the return statement.
	 **/
	public boolean returnStatementAssignmentsAfter() {
		return true;
	}

} // ReturnStatementImpl
