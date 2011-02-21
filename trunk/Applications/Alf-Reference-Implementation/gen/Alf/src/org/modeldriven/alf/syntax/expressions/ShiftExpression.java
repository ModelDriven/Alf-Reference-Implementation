
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.ShiftExpressionImpl;

public class ShiftExpression extends BinaryExpression {

	private Boolean isBitStringConversion = null; // DERIVED

	public ShiftExpression() {
		this.impl = new ShiftExpressionImpl(this);
	}

	public ShiftExpressionImpl getImpl() {
		return (ShiftExpressionImpl) this.impl;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.isBitStringConversion = this.getImpl()
					.deriveIsBitStringConversion();
		}
		return this.isBitStringConversion;
	}

	/**
	 * A shift expression has type BitString.
	 **/
	public boolean shiftExpressionTypeDerivation() {
		return this.getImpl().shiftExpressionTypeDerivation();
	}

	/**
	 * A shift expression has a multiplicity lower bound of 0 if the lower bound
	 * if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean shiftExpressionLowerDerivation() {
		return this.getImpl().shiftExpressionLowerDerivation();
	}

	/**
	 * A shift expression has a multiplicity upper bound of 1.
	 **/
	public boolean shiftExpressionUpperDerivation() {
		return this.getImpl().shiftExpressionUpperDerivation();
	}

	/**
	 * The operands of a shift expression must have type BitString or Integer.
	 **/
	public boolean shiftExpressionOperands() {
		return this.getImpl().shiftExpressionOperands();
	}

	/**
	 * BitString conversion is required if the first operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean shiftExpressionIsBitStringConversionDerivation() {
		return this.getImpl().shiftExpressionIsBitStringConversionDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		Boolean isBitStringConversion = this.getIsBitStringConversion();
		if (isBitStringConversion != null) {
			s.append(" /isBitStringConversion:");
			s.append(isBitStringConversion);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // ShiftExpression
