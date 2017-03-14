
/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * the same as the assignments before the while statement, except that any
     * local names with a multiplicity lower bound of 0 after the block are
     * adjusted to also have a multiplicity lower bound of 0 before the
     * condition expression. The assignments before the block of the while
     * statement are the same as the assignments after the condition expression,
     * adjusted for known null and non-null names and type classifications due
     * to the condition expression being true.
     *
     * If the assigned source for a name after the block of a while statement is
     * different than before the while statement, then the assigned source of
     * the name after the while statement is the while statement. Otherwise it
     * is the same as before the while statement. If a name is unassigned before
     * the block of a while statement and assigned after the block, then it has
     * multiplicity lower bound of 0 after the while statement. Otherwise, the
     * assignments after the while statement are adjusted for known null and non-
     * null names and type classifications due to the condition expression being
     * false.
     **/
    @Override
    public Map<String, AssignedSource> deriveAssignmentAfter() {
        Map<String, AssignedSource> assignmentsBefore = 
                new HashMap<String, AssignedSource>(this.getAssignmentBeforeMap());
        Map<String, AssignedSource> assignmentsAfter = 
                new HashMap<String,AssignedSource>(assignmentsBefore);
        if (this.computeAssignmentsAfter(assignmentsBefore, assignmentsAfter)) {
            assignmentsAfter = new HashMap<String,AssignedSource>(assignmentsBefore);
            this.computeAssignmentsAfter(assignmentsBefore, assignmentsAfter);
        }
        
        Expression condition = this.getSelf().getCondition();
        if (condition != null) {
            condition.getImpl().adjustAssignments(assignmentsAfter, false);
        }
        
        return assignmentsAfter;
    }
    
    protected boolean computeAssignmentsAfter(
            Map<String, AssignedSource> assignmentsBefore, 
            Map<String, AssignedSource> assignmentsAfter) {
        WhileStatement self = this.getSelf();
        Expression condition = self.getCondition();
        Block body = self.getBody();
        boolean recompute = false;
        if (condition != null) {
            condition.getImpl().setAssignmentBefore(assignmentsBefore);        
            Map<String, AssignedSource> assignmentsAfterCondition = 
                    condition.getImpl().adjustAssignments(
                            new HashMap<String, AssignedSource>(condition.getImpl().getAssignmentAfterMap()),
                            true);
            Collection<AssignedSource> newAssignments = condition.getImpl().getNewAssignments();
            if (body != null) {
                body.getImpl().setAssignmentBefore(assignmentsAfterCondition);
                newAssignments.addAll(body.getImpl().getNewAssignments());
            }
            if (!newAssignments.isEmpty()) {
                for (AssignedSource assignment: newAssignments) {
                    String name = assignment.getName();
                    AssignedSource oldAssignment = assignmentsAfterCondition.get(name);
                    AssignedSource newAssignment = AssignedSourceImpl.makeAssignment(
                            oldAssignment == null? assignment: oldAssignment);
                    newAssignment.setSource(self);
                    if (oldAssignment == null) {
                        newAssignment.setLower(0);
                    } else if (oldAssignment.getLower() > 0 && assignment.getLower() == 0) {
                        newAssignment.setLower(0);
                        assignmentsBefore.put(name, newAssignment);
                        recompute = true;
                    }
                    assignmentsAfter.put(name, newAssignment);
                }
            }
        }
        return recompute;
    }
    
    /*
     * Constraints
     */
    
    /**
     * The assignments before the condition expression of a while statement are
     * the same as the assignments before the while statement, except that any
     * local names with a multiplicity lower bound of 0 after the block are
     * adjusted to also have a multiplicity lower bound of 0 before the
     * condition expression. The assignments before the block of the while
     * statement are the same as the assignments after the condition expression,
     * adjusted for known null and non-null names and type classifications due
     * to the condition expression being true.
     **/
	public boolean whileStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
     * If the assigned source for a name after the block of a while statement is
     * different than before the while statement, then the assigned source of
     * the name after the while statement is the while statement. Otherwise it
     * is the same as before the while statement. If a name is unassigned before
     * the block of a while statement and assigned after the block, then it has
     * multiplicity lower bound of 0 after the while statement. Otherwise, the
     * assignments after the while statement are adjusted for known null and non-
     * null names and type classifications due to the condition expression being
     * false.
	 **/
	public boolean whileStatementAssignmentsAfter() {
	    // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}

	/**
     * The condition expression of a while statement must have a type that
     * conforms to type Boolean and a multiplicity upper bound of 1.
	 **/
	public boolean whileStatementCondition() {
        Expression condition = this.getSelf().getCondition();
        ElementReference type = condition == null? null: condition.getType();
        return type != null && type.getImpl().isBoolean() &&
               condition.getLower() == 1 && condition.getUpper() == 1;
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
        super.setCurrentScope(currentScope);
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
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof WhileStatement) {
            WhileStatement self = this.getSelf();
            WhileStatement baseStatement = (WhileStatement)base;
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

} // WhileStatementImpl
