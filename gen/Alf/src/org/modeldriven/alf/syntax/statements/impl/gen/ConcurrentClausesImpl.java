
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClausesImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private Collection<NonFinalClause> clause = new ArrayList<NonFinalClause>();

	public ConcurrentClausesImpl(ConcurrentClauses self) {
		super(self);
	}

	public ConcurrentClauses getSelf() {
		return (ConcurrentClauses) this.self;
	}

	public Collection<NonFinalClause> getClause() {
		return this.clause;
	}

	public void setClause(Collection<NonFinalClause> clause) {
		this.clause = clause;
	}

	public void addClause(NonFinalClause clause) {
		this.clause.add(clause);
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
