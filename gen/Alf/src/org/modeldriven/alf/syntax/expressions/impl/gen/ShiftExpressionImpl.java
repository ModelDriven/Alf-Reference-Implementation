
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

public class ShiftExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.BinaryExpressionImpl {

	public ShiftExpressionImpl(ShiftExpression self) {
		super(self);
	}

	public ShiftExpression getSelf() {
		return (ShiftExpression) this.self;
	}

	public Boolean deriveIsBitStringConversion() {
		return null; // STUB
	}

	/**
	 * A shift expression has type BitString.
	 **/
	public boolean shiftExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A shift expression has a multiplicity lower bound of 0 if the lower bound
	 * if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean shiftExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A shift expression has a multiplicity upper bound of 1.
	 **/
	public boolean shiftExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operands of a shift expression must have type BitString or Integer.
	 **/
	public boolean shiftExpressionOperands() {
		return true;
	}

	/**
	 * BitString conversion is required if the first operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean shiftExpressionIsBitStringConversionDerivation() {
		this.getSelf().getIsBitStringConversion();
		return true;
	}

} // ShiftExpressionImpl
