
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
 * A clause of an if statement with a conditional expression and a sequence of
 * statements that may be executed if the condition is true.
 **/

public class NonFinalClauseImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public NonFinalClauseImpl(NonFinalClause self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.NonFinalClause getSelf() {
		return (NonFinalClause) this.self;
	}

	/**
	 * The assignments before the body of a non-final clause are the assignments
	 * after the condition.
	 **/
	public boolean nonFinalClauseAssignmentsBeforeBody() {
		return true;
	}

	/**
	 * If a name is unassigned before the condition expression of a non-final
	 * clause, then it must be unassigned after that expression (i.e., new local
	 * names may not be defined in the condition).
	 **/
	public boolean nonFinalClauseConditionLocalNames() {
		return true;
	}

	/**
	 * The condition of a non-final clause must have type Boolean and a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean nonFinalClauseConditionType() {
		return true;
	}

	/**
	 * The assignments before a non-final clause are the assignments before the
	 * condition of the clause.
	 **/
	public ArrayList<AssignedSource> assignmentsBefore() {
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsBefore

	/**
	 * The assignments after a non-final clause are the assignments after the
	 * block of the clause.
	 **/
	public ArrayList<AssignedSource> assignmentsAfter() {
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsAfter

} // NonFinalClauseImpl
