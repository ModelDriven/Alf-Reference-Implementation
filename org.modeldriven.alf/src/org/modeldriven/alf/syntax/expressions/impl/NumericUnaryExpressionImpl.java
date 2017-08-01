/*******************************************************************************
 * Copyright 2011-2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.RootNamespace;

/**
 * A unary expression with a numeric operator.
 **/

public class NumericUnaryExpressionImpl extends UnaryExpressionImpl {

	public NumericUnaryExpressionImpl(NumericUnaryExpression self) {
		super(self);
	}

	@Override
	public NumericUnaryExpression getSelf() {
		return (NumericUnaryExpression) this.self;
	}
	
    /**
     * If the operand of a numeric unary expression is of type Integer, then the
     * type of the expression is Integer. If the operand is of type Real, then
     * the type of the expression is Real. Otherwise it has no type.
     **/
	@Override
	protected ElementReference deriveType() {
	    Expression operand = this.getSelf().getOperand();
	    ElementReference type = operand == null? null: operand.getType();
	    return type == null? null:
	           type.getImpl().isInteger()? RootNamespace.getRootScope().getIntegerType(): 
	           type.getImpl().isReal()? RootNamespace.getRootScope().getRealType():
	           null;
	}

	/**
	 * A numeric unary expression has the same multiplicity lower bound as its
	 * operand expression.
	 **/
	@Override
	protected Integer deriveLower() {
	    Expression operand = this.getSelf().getOperand();
	    return operand == null? 0: operand.getLower();
	}
	
	/**
	 * A numeric unary expression has a multiplicity upper bound of 1.
	 **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
    	
	/*
	 * Derivations
	 */
	
	public boolean numericUnaryExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean numericUnaryExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean numericUnaryExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

    /**
     * The operand expression must have a type that conforms to type Integer or
     * Real and a multiplicity upper bound of 1.
     **/
	public boolean numericUnaryExpressionOperand() {
	    Expression operand = this.getSelf().getOperand();
	    ElementReference type = operand == null? null: operand.getType();
		return type == null || 
		            type.getImpl().isIntegerOrReal() &&
		            operand.getUpper() == 1;
	}

} // NumericUnaryExpressionImpl
