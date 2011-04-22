
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

/**
 * An expression used to evaluate its operand expression in isolation.
 **/

public class IsolationExpressionImpl extends UnaryExpressionImpl {

	public IsolationExpressionImpl(IsolationExpression self) {
		super(self);
	}

	@Override
	public IsolationExpression getSelf() {
		return (IsolationExpression) this.self;
	}

	/**
	 * An isolation expression has the type of its operand expression.
	 **/
	@Override
	protected ElementReference deriveType() {
	    IsolationExpression self = this.getSelf();
	    Expression operand = self.getOperand();
	    return operand == null? null: operand.getType();
	}
	
	/**
	 * An isolation expression has the multiplicity lower bound of its operand
	 * expression.
	 **/
    @Override
    protected Integer deriveLower() {
        IsolationExpression self = this.getSelf();
        Expression operand = self.getOperand();
        return operand == null? 0: operand.getLower();
    }
    
	/**
	 * An isolation expression has the multiplicity upper bound of its operand
	 * expression.
	 **/
    @Override
    protected Integer deriveUpper() {
        IsolationExpression self = this.getSelf();
        Expression operand = self.getOperand();
        return operand == null? 0: operand.getUpper();
    }
    
	/*
	 * Derivations
	 */
	
	public boolean isolationExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean isolationExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean isolationExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

} // IsolationExpressionImpl
