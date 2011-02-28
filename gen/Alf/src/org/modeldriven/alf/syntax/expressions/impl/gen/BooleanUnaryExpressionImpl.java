
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
 * A unary expression with a Boolean operator.
 **/

public class BooleanUnaryExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.UnaryExpressionImpl {

	public BooleanUnaryExpressionImpl(BooleanUnaryExpression self) {
		super(self);
	}

	public BooleanUnaryExpression getSelf() {
		return (BooleanUnaryExpression) this.self;
	}

	/**
	 * A Boolean unary expression has type Boolean.
	 **/
	public boolean booleanUnaryExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A Boolean unary expression has the same multiplicity lower bound as its
	 * operand expression.
	 **/
	public boolean booleanUnaryExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A Boolean unary expression has a multiplicity upper bound of 1.
	 **/
	public boolean booleanUnaryExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operand expression of a Boolean unary expression must have type
	 * Boolean and a multiplicity upper bound of 1.
	 **/
	public boolean booleanUnaryExpressionOperand() {
		return true;
	}

} // BooleanUnaryExpressionImpl
