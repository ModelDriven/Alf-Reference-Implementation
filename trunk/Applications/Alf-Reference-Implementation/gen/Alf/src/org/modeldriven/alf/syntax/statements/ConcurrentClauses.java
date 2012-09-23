
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.statements.impl.ConcurrentClausesImpl;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

/**
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClauses extends SyntaxElement {

	public ConcurrentClauses() {
		this.impl = new ConcurrentClausesImpl(this);
	}

	public ConcurrentClauses(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ConcurrentClauses(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public void _deriveAll() {
		super._deriveAll();
		Collection<NonFinalClause> clause = this.getClause();
		if (clause != null) {
			for (Object _clause : clause.toArray()) {
				((NonFinalClause) _clause).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.concurrentClausesAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"concurrentClausesAssignmentsBefore", this));
		}
		if (!this.concurrentClausesConditionAssignments()) {
			violations.add(new ConstraintViolation(
					"concurrentClausesConditionAssignments", this));
		}
		Collection<NonFinalClause> clause = this.getClause();
		if (clause != null) {
			for (Object _clause : clause.toArray()) {
				((NonFinalClause) _clause).checkConstraints(violations);
			}
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Collection<NonFinalClause> clause = this.getClause();
		if (clause != null && clause.size() > 0) {
			System.out.println(prefix + " clause:");
			for (Object _object : clause.toArray()) {
				NonFinalClause _clause = (NonFinalClause) _object;
				if (_clause != null) {
					_clause.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // ConcurrentClauses
