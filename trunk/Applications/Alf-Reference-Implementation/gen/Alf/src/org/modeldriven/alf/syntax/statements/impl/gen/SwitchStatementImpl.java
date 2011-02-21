
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
 * A statement that executes (at most) one of a set of statement sequences based
 * on matching a switch value to a set of test cases.
 **/

public class SwitchStatementImpl extends
		org.modeldriven.alf.syntax.statements.impl.gen.StatementImpl {

	public SwitchStatementImpl(SwitchStatement self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.SwitchStatement getSelf() {
		return (SwitchStatement) this.self;
	}

	public Boolean deriveIsAssured() {
		return null; // STUB
	}

	public Boolean deriveIsDetermined() {
		return null; // STUB
	}

	/**
	 * The assignments before all clauses of a switch statement are the same as
	 * the assignments before the switch statement.
	 **/
	public boolean switchStatementAssignmentsBefore() {
		return true;
	}

	/**
	 * The same local name may not be assigned in more than one case expression
	 * in a switch statement.
	 **/
	public boolean switchStatementCaseAssignments() {
		return true;
	}

	/**
	 * If a name has an assigned source after any clause of a switch statement
	 * that is different than before that clause (including newly defined
	 * names), the assigned source after the switch statement is the switch
	 * statement. Otherwise, the assigned source of a name after the switch
	 * statement is the same as before the switch statement.
	 **/
	public boolean switchStatementAssignmentsAfter() {
		return true;
	}

	/**
	 * If a switch statement does not have a final default clause, then any name
	 * that is unassigned before the switch statement is unassigned after the
	 * switch statement. If a switch statement does have a final default clause,
	 * then any name that is unassigned before the switch statement and is
	 * assigned after any one clause of the switch statement must also be
	 * assigned after every other clause. The type of such names after the
	 * switch statement is the effective common ancestor of the types of the
	 * name in each clause with a multiplicity lower bound that is the minimum
	 * of the lower bound for the name in each clause and a multiplicity upper
	 * bound that is the maximum for the name in each clause.
	 **/
	public boolean switchStatementAssignments() {
		return true;
	}

	public boolean switchStatementExpression() {
		return true;
	}

	public boolean switchStatementEnclosedStatements() {
		return true;
	}

	/**
	 * An switch statement is determined if it has an @determined annotation.
	 **/
	public boolean switchStatementIsDeterminedDerivation() {
		this.getSelf().getIsDetermined();
		return true;
	}

	/**
	 * An switch statement is assured if it has an @assured annotation.
	 **/
	public boolean switchStatementIsAssuredDerivation() {
		this.getSelf().getIsAssured();
		return true;
	}

	/**
	 * In addition to an @isolated annotation, a switch statement may have @assured
	 * and @determined annotations. They may not have arguments.
	 **/
	public Boolean annotationAllowed(Annotation annotation) {
		return false; // STUB
	} // annotationAllowed

} // SwitchStatementImpl
