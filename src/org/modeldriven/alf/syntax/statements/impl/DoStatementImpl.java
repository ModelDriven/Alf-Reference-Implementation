
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A looping statement for which the continuation condition is first tested
 * after the first iteration.
 **/

public class DoStatementImpl extends StatementImpl {

	private Expression condition = null;
	private Block body = null;

	public DoStatementImpl(DoStatement self) {
		super(self);
	}

	public DoStatement getSelf() {
		return (DoStatement) this.self;
	}

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Block getBody() {
		return this.body;
	}

    /**
     * The enclosing statement for all statements in the body of a do statement
     * are the do statement.
     **/

	public void setBody(Block body) {
		this.body = body;
        if (body != null) {
            body.getImpl().setEnclosingStatement(this.getSelf());
        }
	}
	
    /**
     * The assignments before the block of a do statement are the same as the
     * assignments before the do statement. The assignments before the condition
     * expression of a do statement are the same assignments after the block.
     *
     * If the assigned source for a name after the condition expression is
     * different than before the do statement, then the assigned source of the
     * name after the do statement is the do statement. Otherwise it is the same
     * as before the do statement.
     **/
	@Override
	public Map<String, AssignedSource> deriveAssignmentAfter() {
	    DoStatement self = this.getSelf();
	    Expression condition = self.getCondition();
	    Block body = self.getBody();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
	    Map<String, AssignedSource> assignmentsAfter = assignmentsBefore;
	    if (body != null) {
	        body.getImpl().setAssignmentBefore(assignmentsBefore);
	        Set<AssignedSource> newAssignments = 
	            new HashSet<AssignedSource>(body.getImpl().getNewAssignments());
	        if (condition != null) {
	            condition.getImpl().setAssignmentBefore(body.getImpl().getAssignmentAfterMap());
	            newAssignments.addAll(condition.getImpl().getNewAssignments());
	        }
	        if (!newAssignments.isEmpty()) {
	            assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsAfter);
    	        for (AssignedSource assignment: newAssignments) {
    	            AssignedSource assignmentAfter = AssignedSourceImpl.makeAssignment(assignment);
    	            assignmentAfter.setSource(self);
                    assignmentsAfter.put(assignmentAfter.getName(), assignmentAfter);
    	        }
	        }
	    }
	    return assignmentsAfter;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The assignments before the block of a do statement are the same as the
	 * assignments before the do statement. The assignments before the condition
	 * expression of a do statement are the same assignments after the block.
	 **/
	public boolean doStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * If the assigned source for a name after the condition expression is
	 * different than before the do statement, then the assigned source of the
	 * name after the do statement is the do statement. Otherwise it is the same
	 * as before the do statement.
	 **/
	public boolean doStatementAssignmentsAfter() {
	    // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}

	/**
	 * The condition expression of a do statement must have type Boolean and a
	 * multiplicity upper bound of 1.
	 **/
	public boolean doStatementCondition() {
	    Expression condition = this.getSelf().getCondition();
	    ElementReference type = condition == null? null: condition.getType();
		return type != null && type.getImpl().isBoolean() &&
		            condition.getUpper() == 1;
	}

	/**
	 * The enclosing statement for all statements in the body of a do statement
	 * are the do statement.
	 **/
	public boolean doStatementEnclosedStatements() {
	    // Note: This is handled by overriding setEncosingStatement.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    DoStatement self = this.getSelf();
	    Block body = self.getBody();
	    Expression condition = self.getCondition();
	    if (body != null) {
	        body.getImpl().setCurrentScope(currentScope);
	    }
	    if (condition != null) {
	        condition.getImpl().setCurrentScope(currentScope);
	    }
    }
    
	@Override
	protected Statement getLoopStatement() {
	    return this.getSelf();
	}
	
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof DoStatement) {
            DoStatement self = this.getSelf();
            DoStatement baseStatement = (DoStatement)base;
            Expression condition = baseStatement.getCondition();
            Block body = baseStatement.getBody();
            if (body != null) {
                self.setBody((Block)body.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (condition != null) {
                self.setCondition((Expression)condition.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // DoStatementImpl
