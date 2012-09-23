
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.uml.Element;
import org.modeldriven.uml.Profile;
import org.modeldriven.uml.Stereotype;

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
