
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.uml.Element;
import org.modeldriven.uml.Profile;
import org.modeldriven.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
