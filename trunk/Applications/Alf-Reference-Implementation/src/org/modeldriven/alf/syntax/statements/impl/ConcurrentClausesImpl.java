
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClausesImpl extends SyntaxElementImpl {

	private Collection<NonFinalClause> clause = new ArrayList<NonFinalClause>();

	public ConcurrentClausesImpl(ConcurrentClauses self) {
		super(self);
	}

	@Override
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
	
	/*
	 * Constraints
	 */

	/**
	 * The assignments before each of the clauses in a set of concurrent clauses
	 * are the same as the assignments before the concurrent clauses.
	 **/
	public boolean concurrentClausesAssignmentsBefore() {
	    // Note: This is handled by setAssignmentBefore.
		return true;
	}

	/**
	 * The same name may not be assigned in more than one conditional expression
	 * within the same concurrent set of clauses.
	 **/
	public boolean concurrentClausesConditionAssignments() {
	    Set<AssignedSource> previousAssignments = new HashSet<AssignedSource>();
	    for (NonFinalClause clause: this.getSelf().getClause()) {
	        Expression condition = clause.getCondition();
	        Collection<AssignedSource> assignmentsAfter = condition.getImpl().getNewAssignments();
	        Set<AssignedSource> intersection = new HashSet<AssignedSource>(previousAssignments);
	        intersection.retainAll(assignmentsAfter);
	        if (!intersection.isEmpty()) {
	            return false;
	        }
	        previousAssignments.addAll(assignmentsAfter);
	    }
		return true;
	}
	
	/*
	 * Helper Methods
	 */
	
	public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
	    for (NonFinalClause clause: this.getSelf().getClause()) {
	        clause.getImpl().setAssignmentBefore(assignmentBefore);
	    }
	}
	
	public void setEnclosingStatement(Statement enclosingStatement) {
        for (NonFinalClause clause: this.getSelf().getClause()) {
            clause.getImpl().setEnclosingStatement(enclosingStatement);
        }	    
	}
	
    public void setCurrentScope(NamespaceDefinition currentScope) {
        for (NonFinalClause clause: this.getSelf().getClause()) {
            clause.getImpl().setCurrentScope(currentScope);
        }       
    }
    
    public Collection<Block> getBlocks() {
        ConcurrentClauses self = this.getSelf();
        Collection<Block> blocks = new ArrayList<Block>();
        for (NonFinalClause clause: self.getClause()) {
            Block body = clause.getBody();
            if (body != null) {
                blocks.add(body);
            }
        }
        return blocks;
    }
	
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ConcurrentClauses) {
            ConcurrentClauses self = this.getSelf();
            for (NonFinalClause clause: ((ConcurrentClauses)base).getClause()) {
                self.addClause((NonFinalClause)clause.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // ConcurrentClausesImpl
