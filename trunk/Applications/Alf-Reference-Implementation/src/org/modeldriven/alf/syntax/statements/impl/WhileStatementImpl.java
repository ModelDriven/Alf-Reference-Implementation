
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A looping statement for which the continuation condition is first tested
 * before the first iteration.
 **/

public class WhileStatementImpl extends StatementImpl {

	private Block body = null;
	private Expression condition = null;

	public WhileStatementImpl(WhileStatement self) {
		super(self);
	}

	public WhileStatement getSelf() {
		return (WhileStatement) this.self;
	}

	public Block getBody() {
		return this.body;
	}

    /**
     * The enclosing statement for all statements in the body of a while statement
     * are the while statement.
     **/
	public void setBody(Block body) {
		this.body = body;
        if (body != null) {
            body.getImpl().setEnclosingStatement(this.getSelf());
        }
	}

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}
    
    /**
     * The assignments before the condition expression of a while statement are
     * the same as the assignments before the while statement. The assignments
     * before the block of the while statement are the same as the assignments
     * after the condition expression.
     *
     * If a name is assigned before the block, but the assigned source for the
     * name after the block is different than before the block, then the
     * assigned source of the name after the while statement is the while
     * statement. Otherwise it is the same as before the block. If a name is
     * unassigned before the block of a while statement, then it is unassigned
     * after the while statement, even if it is assigned after the block.
     **/
    @Override
    public Map<String, AssignedSource> deriveAssignmentAfter() {
        WhileStatement self = this.getSelf();
        Expression condition = self.getCondition();
        Block body = self.getBody();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Map<String, AssignedSource> assignmentsAfter = assignmentsBefore;
        if (condition != null) {
            condition.getImpl().setAssignmentBefore(assignmentsBefore);            
            Set<AssignedSource> newAssignments = 
                new HashSet<AssignedSource>(condition.getImpl().getNewAssignments());
            if (body != null) {
                body.getImpl().setAssignmentBefore(condition.getImpl().getAssignmentAfterMap());
                newAssignments.addAll(body.getAssignmentAfter());
                if (!newAssignments.isEmpty()) {
                    assignmentsAfter = new HashMap<String,AssignedSource>(assignmentsAfter);
                    for (AssignedSource assignment: newAssignments) {
                        String name = assignment.getName();
                        if (assignmentsBefore.containsKey(name)) {
                            AssignedSource assignmentAfter = AssignedSourceImpl.makeAssignment(assignment);
                            assignmentAfter.setSource(self);
                            assignmentsAfter.put(name, assignmentAfter);
                        }
                    }
                }
            }
        }
        return assignmentsAfter;
    }
    
    /*
     * Constraints
     */
    
	/**
	 * The assignments before the condition expression of a while statement are
	 * the same as the assignments before the while statement. The assignments
	 * before the block of the while statement are the same as the assignments
	 * after the condition expression.
	 **/
	public boolean whileStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * If a name is assigned before the block, but the assigned source for the
	 * name after the block is different than before the block, then the
	 * assigned source of the name after the while statement is the while
	 * statement. Otherwise it is the same as before the block. If a name is
	 * unassigned before the block of a while statement, then it is unassigned
	 * after the while statement, even if it is assigned after the block.
	 **/
	public boolean whileStatementAssignmentsAfter() {
	    // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}

	/**
	 * The condition expression of a while statement must have type Boolean and
	 * a multiplicity upper bound of 1.
	 **/
	public boolean whileStatementCondition() {
        Expression condition = this.getSelf().getCondition();
        ElementReference type = condition == null? null: condition.getType();
        return type != null && type.getImpl().isBoolean() &&
                    condition.getUpper() == 1;
	}

	/**
	 * The enclosing statement for all statements in the body of a while
	 * statement are the while statement.
	 **/
	public boolean whileStatementEnclosedStatements() {
	    // Note: This is handled by overriding setEnclosingStatement.
		return true;
	}

    /*
     * Helper Methods
     */

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        WhileStatement self = this.getSelf();
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
    
} // WhileStatementImpl
