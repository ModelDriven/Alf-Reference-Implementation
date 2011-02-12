
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

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.BitStringUnaryExpressionImpl;

public class BitStringUnaryExpression extends UnaryExpression {

	private Boolean isBitStringConversion = null; // DERIVED

	public BitStringUnaryExpression() {
		this.impl = new BitStringUnaryExpressionImpl(this);
	}

	public BitStringUnaryExpressionImpl getImpl() {
		return (BitStringUnaryExpressionImpl) this.impl;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.isBitStringConversion = this.getImpl()
					.deriveIsBitStringConversion();
		}
		return this.isBitStringConversion;
	}

	/**
	 * A BitString unary expression has type BitString.
	 **/
	public boolean bitStringUnaryExpressionTypeDerivation() {
		return this.getImpl().bitStringUnaryExpressionTypeDerivation();
	}

	/**
	 * A BitString unary expression has the same multiplicity lower bound as its
	 * operand expression.
	 **/
	public boolean bitStringUnaryExpressionLowerDerivation() {
		return this.getImpl().bitStringUnaryExpressionLowerDerivation();
	}

	/**
	 * A BitString unary expression has a multiplicity upper bound of 1.
	 **/
	public boolean bitStringUnaryExpressionUpperDerivation() {
		return this.getImpl().bitStringUnaryExpressionUpperDerivation();
	}

	/**
	 * The operand expression of a BitString unary expression must have type
	 * BitString or Integer and a multiplicity upper bound of 1.
	 **/
	public boolean bitStringUnaryExpressionOperand() {
		return this.getImpl().bitStringUnaryExpressionOperand();
	}

	/**
	 * BitString conversion is required if the operand expression of a BitString
	 * unary expression has type Integer.
	 **/
	public boolean bitStringUnaryExpressionIsBitStringConversionDerivation() {
		return this.getImpl()
				.bitStringUnaryExpressionIsBitStringConversionDerivation();
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
} // BitStringUnaryExpression
