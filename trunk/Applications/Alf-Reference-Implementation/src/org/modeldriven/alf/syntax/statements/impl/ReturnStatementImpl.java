
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.List;
import java.util.Map;

/**
 * A statement that provides a value for the return parameter of an activity.
 **/

public class ReturnStatementImpl extends StatementImpl {

	private Expression expression = null;
	private ElementReference behavior = null; // DERIVED

	public ReturnStatementImpl(ReturnStatement self) {
		super(self);
	}

	public ReturnStatement getSelf() {
		return (ReturnStatement) this.self;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public ElementReference getBehavior() {
		if (this.behavior == null) {
			this.setBehavior(this.deriveBehavior());
		}
		return this.behavior;
	}

	public void setBehavior(ElementReference behavior) {
		this.behavior = behavior;
	}

    /**
     * The current scope for an accept statement should be the containing
     * behavior. It is implicitly set by setCurrentScope().
     */
	protected ElementReference deriveBehavior() {
		return null;
	}
	
    /**
     * The assignments before the expression of a return statement are the same
     * as the assignments before the statement.
     *
     * The assignments after a return statement are the same as the assignments
     * after the expression of the return statement.
     **/
	protected Map<String, AssignedSource> deriveAssignmentAfter() {
	    ReturnStatement self = this.getSelf();
	    Expression expression = self.getExpression();
	    if (expression == null) {
	        return super.deriveAssignmentAfter();
	    } else {
	        expression.getImpl().setAssignmentBefore(this.getAssignmentBeforeMap());
	        return expression.getImpl().getAssignmentAfterMap();
	    }
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The behavior containing the return statement must have a return
	 * parameter. The expression of the return statement must be assignable to
	 * that return parameter.
	 **/
	public boolean returnStatementContext() {
	    ReturnStatement self = this.getSelf();
	    ElementReference behavior = self.getBehavior();
	    if (behavior == null) {
	        return false;
	    } else {
	        FormalParameter returnParameter = behavior.getImpl().getReturnParameter();
	        Expression expression = self.getExpression(); 
	        return expression != null && returnParameter != null &&
	                    returnParameter.getImpl().isAssignableFrom(expression);
	    }
	}

	/**
	 * The assignments before the expression of a return statement are the same
	 * as the assignments before the statement.
	 **/
	public boolean returnStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * The assignments after a return statement are the same as the assignments
	 * after the expression of the return statement.
	 **/
	public boolean returnStatementAssignmentsAfter() {
	    // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        ReturnStatement self = this.getSelf();
        Expression expression = self.getExpression();
        if (expression != null) {
            expression.getImpl().setCurrentScope(currentScope);
        }
        if (currentScope != null) {
            this.getSelf().setBehavior(currentScope.getImpl().getReferent());
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ReturnStatement) {
           Expression expression = ((ReturnStatement)base).getExpression();
            if (expression != null) {
                this.getSelf().setExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
} // ReturnStatementImpl
