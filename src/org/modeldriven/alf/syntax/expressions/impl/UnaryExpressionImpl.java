
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.List;
import java.util.Map;

/**
 * An expression consisting of an operator acting on a single operand
 * expression.
 **/

public abstract class UnaryExpressionImpl extends ExpressionImpl {

	private String operator = "";
	private Expression operand = null;

	public UnaryExpressionImpl(UnaryExpression self) {
		super(self);
	}

	@Override
	public UnaryExpression getSelf() {
		return (UnaryExpression) this.self;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Expression getOperand() {
		return this.operand;
	}

	public void setOperand(Expression operand) {
		this.operand = operand;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The assignments before the operand of a unary expression are the same as
	 * those before the unary expression.
	 **/
	public boolean unaryExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * By default, the assignments after a unary expression are the same as
	 * those after its operand expression.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
	    UnaryExpression self = this.getSelf();
	    Expression operand = self.getOperand();
	    Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
	    if (operand != null) {
	        operand.getImpl().setAssignmentBefore(assignments);
	        assignments = operand.getImpl().getAssignmentAfterMap();
	    }
	    return assignments;
	} // updateAssignments
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
        UnaryExpression self = this.getSelf();
        Expression operand = self.getOperand();
        if (operand != null) {
            operand.getImpl().setCurrentScope(currentScope);
        }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof UnaryExpression) {
            UnaryExpression self = this.getSelf();
            UnaryExpression baseExpression = (UnaryExpression)base;
            Expression operand = baseExpression.getOperand();
            self.setOperator(baseExpression.getOperator());
            if (operand != null) {
                self.setOperand((Expression)operand.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // UnaryExpressionImpl
