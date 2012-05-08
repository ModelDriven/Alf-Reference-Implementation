
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.List;
import java.util.Map;

/**
 * A statement that evaluates an expression when executed.
 **/

public class ExpressionStatementImpl extends StatementImpl {

	private Expression expression = null;

	public ExpressionStatementImpl(ExpressionStatement self) {
		super(self);
	}

	@Override
	public ExpressionStatement getSelf() {
		return (ExpressionStatement) this.self;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	@Override
	public Map<String, AssignedSource> deriveAssignmentAfter() {
	    ExpressionStatement self = this.getSelf();
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
	 * The assignments before the expression of an expression statement are the
	 * same as the assignments before the statement.
	 **/
	public boolean expressionStatementAssignmentsBefore() {
	    // Note: This is handled by deriveAssignmentAfter.
		return true;
	}

	/**
	 * The assignments after an expression statement are the same as the
	 * assignments after its expression.
	 **/
	public boolean expressionStatementAssignmentsAfter() {
        // Note: This is handled by overriding deriveAssignmentAfter.
		return true;
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    Expression expression = this.getSelf().getExpression();
	    if (expression != null) {
	        expression.getImpl().setCurrentScope(currentScope);
	    }
	}
	
	@Override
	public void setEnclosingBlock(Block enclosingBlock) {
	    Expression expression = this.getSelf().getExpression();
	    if (expression != null) {
	        expression.getImpl().setEnclosingBlock(enclosingBlock);
	    }
	}
	
	@Override
	public boolean isSuperConstructorInvocation() {
	    Expression expression = this.getSelf().getExpression();
	    return expression instanceof SuperInvocationExpression &&
	            ((SuperInvocationExpression)expression).getReferent().getImpl().
	                isConstructor();
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ExpressionStatement) {
           Expression expression = ((ExpressionStatement)base).getExpression();
            if (expression != null) {
                this.getSelf().setExpression((Expression)expression.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // ExpressionStatementImpl
