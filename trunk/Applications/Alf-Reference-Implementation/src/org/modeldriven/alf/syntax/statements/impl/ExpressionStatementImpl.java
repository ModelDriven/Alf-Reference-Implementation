
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

} // ExpressionStatementImpl
