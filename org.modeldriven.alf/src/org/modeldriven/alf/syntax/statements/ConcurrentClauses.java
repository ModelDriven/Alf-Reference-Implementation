/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.statements.impl.ConcurrentClausesImpl;

/**
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClauses extends SyntaxElement {

	public ConcurrentClauses() {
		this.impl = new ConcurrentClausesImpl(this);
	}

	public ConcurrentClauses(Parser parser) {
		this();
		this.init(parser);
	}

	public ConcurrentClauses(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	
	public Collection<AssignedSource> getAssignmentBefore() {
	    return this.getImpl().getAssignmentBefore();
	}
	
	public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
	    this.getImpl().setAssignmentBefore(assignmentBefore);
	}
	
	public void addAssignmentBefore(AssignedSource assignmentBefore) {
	    this.getImpl().addAssignmentBefore(assignmentBefore);
	}

    /**
     * The assignments before the condition of each of the clauses in a set of
     * concurrent clauses are the same as the assignments before the concurrent
     * clauses.
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

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getClause());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Collection<NonFinalClause> clause = this.getClause();
		if (clause != null) {
			for (Object _clause : clause.toArray()) {
				((NonFinalClause) _clause).deriveAll();
			}
		}
	}

	@Override
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
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
