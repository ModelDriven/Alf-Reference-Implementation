
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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
