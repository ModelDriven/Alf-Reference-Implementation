
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A unary expression with a numeric operator.
 **/

public class NumericUnaryExpressionImpl extends UnaryExpressionImpl {

	public NumericUnaryExpressionImpl(NumericUnaryExpression self) {
		super(self);
	}

	public NumericUnaryExpression getSelf() {
		return (NumericUnaryExpression) this.self;
	}

	/**
	 * A numeric unary expression must have type Integer.
	 **/
	public boolean numericUnaryExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A numeric unary expression has the same multiplicity lower bound as its
	 * operand expression.
	 **/
	public boolean numericUnaryExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A numeric unary expression has a multiplicity upper bound of 1.
	 **/
	public boolean numericUnaryExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operand expression must have type Integer and a multiplicity upper
	 * bound of 1.
	 **/
	public boolean numericUnaryExpressionOperand() {
		return true;
	}

} // NumericUnaryExpressionImpl
