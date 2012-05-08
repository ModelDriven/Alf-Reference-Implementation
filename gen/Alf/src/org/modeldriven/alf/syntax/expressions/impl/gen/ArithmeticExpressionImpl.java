
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A binary expression with an arithmetic operator.
 **/

public class ArithmeticExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.BinaryExpressionImpl {

	private Boolean isConcatenation = null; // DERIVED

	public ArithmeticExpressionImpl(ArithmeticExpression self) {
		super(self);
	}

	public ArithmeticExpression getSelf() {
		return (ArithmeticExpression) this.self;
	}

	public Boolean getIsConcatenation() {
		if (this.isConcatenation == null) {
			this.setIsConcatenation(this.deriveIsConcatenation());
		}
		return this.isConcatenation;
	}

	public void setIsConcatenation(Boolean isConcatenation) {
		this.isConcatenation = isConcatenation;
	}

	protected Boolean deriveIsConcatenation() {
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
