
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * A clause of an if statement with a conditional expression and a sequence of
 * statements that may be executed if the condition is true.
 **/

public class NonFinalClauseImpl extends SyntaxElementImpl {

	private Expression condition = null;
	private Block body = null;

	public NonFinalClauseImpl(NonFinalClause self) {
		super(self);
	}

	@Override
	public NonFinalClause getSelf() {
		return (NonFinalClause) this.self;
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

	public void setBody(Block body) {
		this.body = body;
	}

	/*
	 * Constraints
	 */
	
	/**
	 * The assignments before the body of a non-final clause are the assignments
	 * after the condition.
	 **/
	public boolean nonFinalClauseAssignmentsBeforeBody() {
	    // Note: This is handled by setAssignmentBefore.
		return true;
	}

	/**
	 * If a name is unassigned before the condition expression of a non-final
	 * clause, then it must be unassigned after that expression (i.e., new local
	 * names may not be defined in the condition).
	 **/
	public boolean nonFinalClauseConditionLocalNames() {
	    Expression condition = this.getSelf().getCondition();
	    return condition == null || 
	            condition.getImpl().getAssignmentBeforeMap().keySet().
	                containsAll(condition.getImpl().getAssignmentAfterMap().keySet());
	}

	/**
	 * The condition of a non-final clause must have type Boolean and a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean nonFinalClauseConditionType() {
        Expression condition = this.getSelf().getCondition();
		return condition == null || 
		    condition.getType().getImpl().equals(RootNamespace.getBooleanType()) &&
		    condition.getUpper() <= 1;
	}

	/*
	 * Helper Methods
	 */
	        
	/**
	 * The assignments before a non-final clause are the assignments before the
	 * condition of the clause.
	 **/
	public Collection<AssignedSource> assignmentsBefore() {
	    Expression condition = this.getSelf().getCondition();
	    return condition == null?
	            new ArrayList<AssignedSource>():
	            condition.getAssignmentBefore();
	} // assignmentsBefore

	/**
	 * The assignments after a non-final clause are the assignments after the
	 * block of the clause.
	 **/
	public Collection<AssignedSource> assignmentsAfter() {
	    Block body = this.getSelf().getBody();
        return body == null?
                new ArrayList<AssignedSource>():
                body.getAssignmentAfter();
	} // assignmentsAfter
	
    /**
     * The assignments before the body of a non-final clause are the assignments
     * after the condition.
     **/
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
        NonFinalClause self = this.getSelf();
        Expression condition = self.getCondition();
        Block body = self.getBody();
        if (condition != null) {
            condition.getImpl().setAssignmentBefore(assignmentBefore);
            assignmentBefore = condition.getImpl().getAssignmentAfterMap();
        }
        if (body != null) {
            body.getImpl().setAssignmentBefore(assignmentBefore);
        }
    }

    public void setEnclosingStatement(Statement enclosingStatement) {
        Block body = this.getSelf().getBody();
        if (body != null) {
            body.getImpl().setEnclosingStatement(enclosingStatement);
        }
    }

    public void setCurrentScope(NamespaceDefinition currentScope) {
        NonFinalClause self = this.getSelf();
        Expression condition = self.getCondition();
        Block body = self.getBody();
        if (condition != null) {
            condition.getImpl().setCurrentScope(currentScope);
        }
        if (body != null) {
            body.getImpl().setCurrentScope(currentScope);
        }
    }

} // NonFinalClauseImpl
