/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

public class BitStringUnaryExpressionImpl extends UnaryExpressionImpl {

	private Boolean isBitStringConversion = null; // DERIVED

	public BitStringUnaryExpressionImpl(BitStringUnaryExpression self) {
		super(self);
	}

	@Override
	public BitStringUnaryExpression getSelf() {
		return (BitStringUnaryExpression) this.self;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.setIsBitStringConversion(this.deriveIsBitStringConversion());
		}
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

    /**
     * BitString conversion is required if the operand expression of a BitString
     * unary expression has a type that conforms to type Integer.
     **/
	protected Boolean deriveIsBitStringConversion() {
	    BitStringUnaryExpression self = this.getSelf();
	    Expression operand = self.getOperand();
	    ElementReference type = operand == null? null: operand.getType();
		return type != null && type.getImpl().isInteger();
	}
	
	/**
	 * A BitString unary expression has type BitString.
	 **/	
	@Override
    protected ElementReference deriveType() {
        return RootNamespace.getRootScope().getBitStringType();
    }
    
    /**
     * A BitString unary expression has a multiplicity lower bound of 1.
     **/
    @Override
    protected Integer deriveLower() {
        return 1;
    }   
	
    /**
     * A BitString unary expression has a multiplicity upper bound of 1.
     **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
	
	/*
	 * Derivations
	 */

	public boolean bitStringUnaryExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean bitStringUnaryExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean bitStringUnaryExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean bitStringUnaryExpressionIsBitStringConversionDerivation() {
		this.getSelf().getIsBitStringConversion();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The operand expression of a BitString unary expression must have type
	 * BitString or Integer and multiplicity lower and upper bounds of 1.
	 **/
	public boolean bitStringUnaryExpressionOperand() {
        BitStringUnaryExpression self = this.getSelf();
        Expression operand = self.getOperand();
        if (operand == null) {
            return false;
        } else {
            ElementReference operandType = operand.getType();
            return operandType == null || 
                    (operand.getLower() == 1 && operand.getUpper() == 1 &&                   
                        (operandType.getImpl().isBitString() ||
                         operandType.getImpl().isInteger()));
        }
	}

} // BitStringUnaryExpressionImpl
