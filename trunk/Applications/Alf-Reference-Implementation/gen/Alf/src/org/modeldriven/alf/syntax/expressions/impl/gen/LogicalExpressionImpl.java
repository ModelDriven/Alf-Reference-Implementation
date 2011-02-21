
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

/**
 * A binary expression with a logical operator.
 **/

public class LogicalExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.BinaryExpressionImpl {

	public LogicalExpressionImpl(LogicalExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.LogicalExpression getSelf() {
		return (LogicalExpression) this.self;
	}

	public Boolean deriveIsBitWise() {
		return null; // STUB
	}

	public Boolean deriveIsBitStringConversion1() {
		return null; // STUB
	}

	public Boolean deriveIsBitStringConversion2() {
		return null; // STUB
	}

	/**
	 * A logical expression has type Boolean.
	 **/
	public boolean logicalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A logical expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean logicalExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A logical expression has a multiplicity upper bound of 1.
	 **/
	public boolean logicalExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operands of a logical expression must have type Boolean.
	 **/
	public boolean logicalExpressionOperands() {
		return true;
	}

	/**
	 * BitString conversion is required if the first operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion1Derivation() {
		this.getSelf().getIsBitStringConversion1();
		return true;
	}

	/**
	 * BitString conversion is required if the second operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion2Derivation() {
		this.getSelf().getIsBitStringConversion2();
		return true;
	}

	/**
	 * A logical expression is bit-wise if the type of its first operand is not
	 * Boolean.
	 **/
	public boolean logicalExpressionIsBitWiseDerivation() {
		this.getSelf().getIsBitWise();
		return true;
	}

} // LogicalExpressionImpl
