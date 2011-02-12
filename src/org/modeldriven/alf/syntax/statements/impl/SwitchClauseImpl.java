
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
 * A clause in a switch statement with a set of cases and a sequence of
 * statements that may be executed if one of the cases matches the switch value.
 **/

public class SwitchClauseImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public SwitchClauseImpl(SwitchClause self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.SwitchClause getSelf() {
		return (SwitchClause) this.self;
	}

	/**
	 * The assignments before any case expression of a switch clause are the
	 * same as the assignments before the clause. The assignments before the
	 * block of a switch clause are the assignments after all case expressions.
	 **/
	public boolean switchClauseAssignmentsBefore() {
		return true;
	}

	/**
	 * If a name is unassigned before a switch clause, then it must be
	 * unassigned after all case expressions of the clause (i.e., new local
	 * names may not be defined in case expressions).
	 **/
	public boolean switchClauseCaseLocalNames() {
		return true;
	}

	/**
	 * The assignments before a switch clause are the assignments before any
	 * case expression of the clause.
	 **/
	public ArrayList<AssignedSource> assignmentsBefore() {
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsBefore

	/**
	 * The assignments after a switch clause are the assignments after the block
	 * of the switch clause.
	 **/
	public ArrayList<AssignedSource> assignmentsAfter() {
		return new ArrayList<AssignedSource>(); // STUB
	} // assignmentsAfter

} // SwitchClauseImpl
