
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.ConcurrentClausesImpl;

/**
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClauses extends SyntaxElement {

	public ConcurrentClauses() {
		this.impl = new ConcurrentClausesImpl(this);
	}

	public ConcurrentClausesImpl getImpl() {
		return (ConcurrentClausesImpl) this.impl;
	}

	public Collection<NonFinalClause> getClause() {
		return this.getImpl().getClause();
	}

	public void setClause(Collection<NonFinalClause> clause) {
		this.getImpl().setClause(clause);
	}

	public void addClause(NonFinalClause clause) {
		this.getImpl().addClause(clause);
	}

	/**
	 * The assignments before each of the clauses in a set of concurrent clauses
	 * are the same as the assignments before the concurrent clauses.
	 **/
	public boolean concurrentClausesAssignmentsBefore() {
		return this.getImpl().concurrentClausesAssignmentsBefore();
	}

	/**
	 * The same name may not be assigned in more than one conditional expression
	 * within the same concurrent set of clauses.
	 **/
	public boolean concurrentClausesConditionAssignments() {
		return this.getImpl().concurrentClausesConditionAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<NonFinalClause> clause = this.getClause();
		if (clause != null) {
			if (clause.size() > 0) {
				System.out.println(prefix + " clause:");
			}
			for (NonFinalClause _clause : clause) {
				if (_clause != null) {
					_clause.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // ConcurrentClauses
