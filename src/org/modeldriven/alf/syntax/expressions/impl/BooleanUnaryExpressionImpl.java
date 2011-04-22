
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
        return RootNamespace.getBooleanType();
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
	 * The operand expression of a Boolean unary expression must have type
	 * Boolean and a multiplicity upper bound of 1.
	 **/
	public boolean booleanUnaryExpressionOperand() {
        BooleanUnaryExpression self = this.getSelf();
        Expression operand = self.getOperand();
        if (operand == null) {
            return false;
        } else {
            ElementReference operandType = operand.getType();
            return operand.getUpper() == 1 && operandType != null &&
                        operandType.getImpl().isBoolean();
        }
	}

} // BooleanUnaryExpressionImpl
