
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

import org.modeldriven.alf.syntax.expressions.impl.BooleanUnaryExpressionImpl;

/**
 * A unary expression with a Boolean operator.
 **/

public class BooleanUnaryExpression extends UnaryExpression {

	public BooleanUnaryExpression() {
		this.impl = new BooleanUnaryExpressionImpl(this);
	}

	public BooleanUnaryExpressionImpl getImpl() {
		return (BooleanUnaryExpressionImpl) this.impl;
	}

	/**
	 * A Boolean unary expression has type Boolean.
	 **/
	public boolean booleanUnaryExpressionTypeDerivation() {
		return this.getImpl().booleanUnaryExpressionTypeDerivation();
	}

	/**
	 * A Boolean unary expression has the same multiplicity lower bound as its
	 * operand expression.
	 **/
	public boolean booleanUnaryExpressionLowerDerivation() {
		return this.getImpl().booleanUnaryExpressionLowerDerivation();
	}

	/**
	 * A Boolean unary expression has a multiplicity upper bound of 1.
	 **/
	public boolean booleanUnaryExpressionUpperDerivation() {
		return this.getImpl().booleanUnaryExpressionUpperDerivation();
	}

	/**
	 * The operand expression of a Boolean unary expression must have type
	 * Boolean and a multiplicity upper bound of 1.
	 **/
	public boolean booleanUnaryExpressionOperand() {
		return this.getImpl().booleanUnaryExpressionOperand();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // BooleanUnaryExpression
