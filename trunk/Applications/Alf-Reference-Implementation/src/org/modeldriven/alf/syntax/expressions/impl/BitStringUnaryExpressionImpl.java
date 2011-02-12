
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class BitStringUnaryExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.UnaryExpressionImpl {

	public BitStringUnaryExpressionImpl(BitStringUnaryExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.BitStringUnaryExpression getSelf() {
		return (BitStringUnaryExpression) this.self;
	}

	public Boolean deriveIsBitStringConversion() {
		return null; // STUB
	}

	/**
	 * A BitString unary expression has type BitString.
	 **/
	public boolean bitStringUnaryExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A BitString unary expression has the same multiplicity lower bound as its
	 * operand expression.
	 **/
	public boolean bitStringUnaryExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A BitString unary expression has a multiplicity upper bound of 1.
	 **/
	public boolean bitStringUnaryExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operand expression of a BitString unary expression must have type
	 * BitString or Integer and a multiplicity upper bound of 1.
	 **/
	public boolean bitStringUnaryExpressionOperand() {
		return true;
	}

	/**
	 * BitString conversion is required if the operand expression of a BitString
	 * unary expression has type Integer.
	 **/
	public boolean bitStringUnaryExpressionIsBitStringConversionDerivation() {
		this.getSelf().getIsBitStringConversion();
		return true;
	}

} // BitStringUnaryExpressionImpl
