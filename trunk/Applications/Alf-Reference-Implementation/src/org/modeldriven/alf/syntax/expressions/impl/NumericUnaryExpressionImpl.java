
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0
 * (http://www.opensource.org/licenses/afl-3.0.php)
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

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
	 * A numeric unary expression must have type Integer.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getIntegerType();
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
	 * The operand expression must have type Integer and a multiplicity upper
	 * bound of 1.
	 **/
	public boolean numericUnaryExpressionOperand() {
	    Expression operand = this.getSelf().getOperand();
		return operand != null && operand.getType().getImpl().isInteger() &&
		            operand.getUpper() == 1;
	}

} // NumericUnaryExpressionImpl
