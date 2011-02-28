
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
 * A binary expression with an arithmetic operator.
 **/

public class ArithmeticExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.BinaryExpressionImpl {

	public ArithmeticExpressionImpl(ArithmeticExpression self) {
		super(self);
	}

	public ArithmeticExpression getSelf() {
		return (ArithmeticExpression) this.self;
	}

	public Boolean deriveIsConcatenation() {
		return null; // STUB
	}

	/**
	 * An arithmetic expression is a string concatenation expression if its type
	 * is String.
	 **/
	public boolean arithmeticExpressionIsConcatenationDerivation() {
		this.getSelf().getIsConcatenation();
		return true;
	}

	/**
	 * The type of an arithmetic expression is the same as the type of its
	 * operands.
	 **/
	public boolean arithmeticExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * An arithmetic expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean arithmeticExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * An arithmetic expression has a multiplicity upper bound of 1.
	 **/
	public boolean arithmeticExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operands of an arithmetic expression must both have type Integer,
	 * unless the operator is +, in which case they may also both have type
	 * String.
	 **/
	public boolean arithmeticExpressionOperandTypes() {
		return true;
	}

} // ArithmeticExpressionImpl
