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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A clause in a switch statement with a set of cases and a sequence of
 * statements that may be executed if one of the cases matches the switch value.
 **/

public class SwitchClauseImpl extends SyntaxElementImpl {

	private Collection<Expression> case_ = new ArrayList<Expression>();
	private Block block = null;

	public SwitchClauseImpl(SwitchClause self) {
		super(self);
	}

	public SwitchClause getSelf() {
		return (SwitchClause) this.self;
	}

	public Collection<Expression> getCase() {
		return this.case_;
	}

	public void setCase(Collection<Expression> case_) {
		this.case_ = case_;
	}

	public void addCase(Expression case_) {
		this.case_.add(case_);
	}

	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The assignments before any case expression of a switch clause are the
	 * same as the assignments before the clause. The assignments before the
	 * block of a switch clause are the assignments after all case expressions.
	 **/
	public boolean switchClauseAssignmentsBefore() {
	    // Note: This is handled by setAssignmentBefore.
		return true;
	}

	/**
	 * If a name is unassigned before a switch clause, then it must be
	 * unassigned after all case expressions of the clause (i.e., new local
	 * names may not be defined in case expressions).
	 **/
	public boolean switchClauseCaseLocalNames() {
	    SwitchClause self = this.getSelf();
	    Collection<Expression> cases = self.getCase();
	    Map<String, AssignedSource> assignmentsBefore = this.assignmentsBeforeMap();
	    for (Expression expression: cases) {
	        if (expression != null) {
    	        for (AssignedSource assignmentAfter: expression.getAssignmentAfter()) {
        	        if (!assignmentsBefore.containsKey(assignmentAfter.getName()) &&
        	                !assignmentAfter.getImpl().isOutParameter()) {
        	            return false;
        	        }
    	        }
	        }
	    }
		return true;
	}
	
    /**
     * All the case expressions of a switch clause must have a multiplicity no
     * greater than 1.
     **/
    public boolean switchClauseCases() {
        for (Expression expression: this.getSelf().getCase()) {
            if (expression == null || expression.getUpper() > 1) {
                return false;
            }
        }
        return true;
    }

	/*
	 * Helper Methods
	 */

	/**
	 * The assignments before a switch clause are the assignments before any
	 * case expression of the clause.
	 **/
    public Collection<AssignedSource> assignmentsBefore() {
        return this.assignmentsBeforeMap().values();
    }
    
	public Map<String, AssignedSource> assignmentsBeforeMap() {
	    Object[] cases = this.getSelf().getCase().toArray();
	    return cases.length == 0 || cases[0] == null? new HashMap<String, AssignedSource>():
	        ((Expression)cases[0]).getImpl().getAssignmentBeforeMap();
	} // assignmentsBefore

	/**
	 * The assignments after a switch clause are the assignments after the block
	 * of the switch clause.
	 **/
	public Collection<AssignedSource> assignmentsAfter() {
	    Block block = this.getSelf().getBlock();
		return block == null? new ArrayList<AssignedSource>():
		                      block.getAssignmentAfter();
	} // assignmentsAfter
	
    /**
     * The assignments before any case expression of a switch clause are the
     * same as the assignments before the clause. The assignments before the
     * block of a switch clause are the assignments after all case expressions.
     **/
	public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
	    SwitchClause self = this.getSelf();
	    Collection<Expression> cases = self.getCase();
	    Block block = self.getBlock();
	    Map<String, AssignedSource> assignmentsAfterCases = 
	        new HashMap<String, AssignedSource>(assignmentBefore);
	    for (Expression expression: cases) {
	        if (expression != null) {
    	        expression.getImpl().setAssignmentBefore(assignmentBefore);
    	        for (AssignedSource assignment: expression.getImpl().getNewAssignments()) {
    	            assignmentsAfterCases.put(assignment.getName(), assignment);
    	        }
	        }
	    }
	    if (block != null) {
	        block.getImpl().setAssignmentBefore(assignmentsAfterCases);
	    }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof SwitchClause) {
            SwitchClause self = this.getSelf();
            SwitchClause baseClause = (SwitchClause)base;
            Block block = baseClause.getBlock();
            for (Expression case_: baseClause.getCase()) {
                self.addCase((Expression)case_.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (block != null) {
                self.setBlock((Block)block.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // SwitchClauseImpl
