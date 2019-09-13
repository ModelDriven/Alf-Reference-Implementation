/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Map;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A unary expression with a Boolean operator.
 **/

public class BooleanUnaryExpressionImpl extends UnaryExpressionImpl {

	public BooleanUnaryExpressionImpl(BooleanUnaryExpression self) {
		super(self);
	}

	@Override
	public BooleanUnaryExpression getSelf() {
		return (BooleanUnaryExpression) this.self;
	}

    /**
     * A Boolean unary expression has type Boolean.
     **/    
    @Override
    protected ElementReference deriveType() {
        return RootNamespace.getRootScope().getBooleanType();
    }
    
    /**
     * A Boolean unary expression has the same multiplicity lower bound as its
     * operand expression.
     **/
    @Override
    protected Integer deriveLower() {
        BooleanUnaryExpression self = this.getSelf();
        Expression operand = self.getOperand();
        return operand == null? 1: operand.getLower();
    }   
    
    /**
     * A Boolean unary expression has a multiplicity upper bound of 1.
     **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
    
    /*
     * Derivations
     */
    
	public boolean booleanUnaryExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean booleanUnaryExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean booleanUnaryExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

    /**
     * The operand expression of a Boolean unary expression must have a type
     * that conforms to type Boolean and a multiplicity upper bound of 1.
     **/
	public boolean booleanUnaryExpressionOperand() {
        BooleanUnaryExpression self = this.getSelf();
        Expression operand = self.getOperand();
        if (operand == null) {
            return false;
        } else {
            ElementReference operandType = operand.getType();
            return operandType == null || 
                    (operand.getUpper() == 1 &&
                        operandType.getImpl().isBoolean());
        }
	}

    /*
     * Helper Methods
     */

	/**
	 * If the expression is a negation, then check the operand expression for
	 * known nulls and non-nulls based on the negation of the given truth
	 * condition.
	 */
    @Override
    public Map<String, AssignedSource> adjustAssignments(
            Map<String, AssignedSource> assignmentsMap, boolean condition) {
        BooleanUnaryExpression self = this.getSelf();
        String operator = self.getOperator();
        Expression operand = self.getOperand();
        if ("!".equals(operator) && operand != null) {
            assignmentsMap = operand.getImpl().adjustAssignments(assignmentsMap, !condition);
        }
        return assignmentsMap;
    }
} // BooleanUnaryExpressionImpl
