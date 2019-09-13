/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A grouping of non-final conditional clauses to be tested concurrently.
 **/

public class ConcurrentClausesImpl extends SyntaxElementImpl {

	private Collection<NonFinalClause> clause = new ArrayList<NonFinalClause>();
    private Map<String, AssignedSource> assignmentBefore = null; // DERIVED

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
	
    public Collection<AssignedSource> getAssignmentBefore() {
        return this.getAssignmentBeforeMap().values();
    }
    
    public Map<String, AssignedSource> getAssignmentBeforeMap() {
        if (this.assignmentBefore == null) {
            this.setAssignmentBefore(this.deriveAssignmentBefore());
        }
        return this.assignmentBefore;
    }
    
    public AssignedSource getAssignmentBefore(String name) {
        return this.getAssignmentBeforeMap().get(name);
    }

    public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
        if (this.assignmentBefore == null) {
            this.assignmentBefore = new HashMap<String, AssignedSource>();
        } else {
            this.assignmentBefore.clear();
        }
        for (AssignedSource assignment: assignmentBefore) {
            this.addAssignmentBefore(assignment);
        }
    }
    
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
        this.assignmentBefore = assignmentBefore;
        
        for (NonFinalClause clause: this.getSelf().getClause()) {
            clause.getImpl().setAssignmentBefore(assignmentBefore);
        }
    }

    public void addAssignmentBefore(AssignedSource assignmentBefore) {
        this.assignmentBefore.put(assignmentBefore.getName(), assignmentBefore);
    }

    /**
     * The assignments before are usually set externally.
     */
    protected Map<String, AssignedSource> deriveAssignmentBefore() {
        return new HashMap<String, AssignedSource>();
    }

	/*
	 * Constraints
	 */

	/**
     * The assignments before the condition of each of the clauses in a set of
     * concurrent clauses are the same as the assignments before the concurrent
     * clauses.
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
	        if (clause != null) {
    	        Expression condition = clause.getCondition();
    	        Collection<AssignedSource> assignmentsAfter = condition.getImpl().getNewAssignments();
    	        Set<AssignedSource> intersection = new HashSet<AssignedSource>(previousAssignments);
    	        intersection.retainAll(assignmentsAfter);
    	        if (!intersection.isEmpty()) {
    	            return false;
    	        }
    	        previousAssignments.addAll(assignmentsAfter);
	        }
	    }
		return true;
	}
	
	/*
	 * Helper Methods
	 */
	
	public Map<String, AssignedSource> updateAssignmentsBefore() {
	    ConcurrentClauses self = this.getSelf();
	    Map<String, AssignedSource> assignments = 
	            new HashMap<String, AssignedSource>(this.getAssignmentBeforeMap());
	    for (NonFinalClause clause: self.getClause()) {
	        if (clause != null) {
    	        Expression condition = clause.getCondition();
    	        if (condition != null) {
    	            assignments = condition.getImpl().adjustAssignments(assignments, false);
    	        }
	        }
	    }
	    return assignments;
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
            if (clause != null) {
                Block body = clause.getBody();
                if (body != null) {
                    blocks.add(body);
                }
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
                if (clause != null) {
                    self.addClause((NonFinalClause)clause.getImpl().
                            bind(templateParameters, templateArguments));
                }
            }
        }
    }
    
} // ConcurrentClausesImpl
