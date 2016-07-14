
/*******************************************************************************
 * Copyright 2011-2015 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class ShiftExpressionImpl extends BinaryExpressionImpl {

	private Boolean isBitStringConversion = null; // DERIVED

	public ShiftExpressionImpl(ShiftExpression self) {
		super(self);
	}

	@Override
	public ShiftExpression getSelf() {
		return (ShiftExpression) this.self;
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
	 * BitString conversion is required if the first operand expression of a
	 * shift expression has type Integer.
	 **/
	protected Boolean deriveIsBitStringConversion() {
	    Expression operand1 = this.getSelf().getOperand1();
	    ElementReference type = operand1 == null? null: operand1.getType();
		return type != null && type.getImpl().isInteger();
	}

	/**
	 * A shift expression has type BitString.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getRootScope().getBitStringType();
	}
	
    /*
     * Derivations
     */

    public boolean shiftExpressionIsBitStringConversionDerivation() {
		this.getSelf().getIsBitStringConversion();
		return true;
	}

	public boolean shiftExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean shiftExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}


	public boolean shiftExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/*
	 * Constraints
	 */
	
	/**
     * The first operand expression of a shift expression must have a type that
     * conforms to the type BitString or Integer. The second operand expression
     * must have a type that conforms to the type Integer.
	 **/
	public boolean shiftExpressionOperands() {
        ShiftExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        ElementReference type1 = operand1.getType();
        Expression operand2 = self.getOperand2();
        ElementReference type2 = operand2.getType();
		return type1 != null && 
		            (type1.getImpl().isBitString() || 
		                    type1.getImpl().isInteger()) &&
		            type2.getImpl().isInteger();
	}
	

} // ShiftExpressionImpl
