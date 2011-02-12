
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

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.ConcurrentClausesImpl;

/**
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClauses extends SyntaxElement {

	private ArrayList<NonFinalClause> clause = new ArrayList<NonFinalClause>();

	public ConcurrentClauses() {
		this.impl = new ConcurrentClausesImpl(this);
	}

	public ConcurrentClausesImpl getImpl() {
		return (ConcurrentClausesImpl) this.impl;
	}

	public ArrayList<NonFinalClause> getClause() {
		return this.clause;
	}

	public void setClause(ArrayList<NonFinalClause> clause) {
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
		ArrayList<NonFinalClause> clause = this.getClause();
		if (clause != null) {
			for (NonFinalClause item : this.getClause()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // ConcurrentClauses
