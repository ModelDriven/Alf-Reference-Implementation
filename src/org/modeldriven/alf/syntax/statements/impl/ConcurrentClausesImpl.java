
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
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClausesImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public ConcurrentClausesImpl(ConcurrentClauses self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.ConcurrentClauses getSelf() {
		return (ConcurrentClauses) this.self;
	}

	/**
	 * The assignments before each of the clauses in a set of concurrent clauses
	 * are the same as the assignments before the concurrent clauses.
	 **/
	public boolean concurrentClausesAssignmentsBefore() {
		return true;
	}

	/**
	 * The same name may not be assigned in more than one conditional expression
	 * within the same concurrent set of clauses.
	 **/
	public boolean concurrentClausesConditionAssignments() {
		return true;
	}

} // ConcurrentClausesImpl
