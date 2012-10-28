
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
import java.util.TreeSet;

public class BitStringUnaryExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.UnaryExpressionImpl {

	private Boolean isBitStringConversion = null; // DERIVED

	public BitStringUnaryExpressionImpl(BitStringUnaryExpression self) {
		super(self);
	}

	public BitStringUnaryExpression getSelf() {
		return (BitStringUnaryExpression) this.self;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.setIsBitStringConversion(this.deriveIsBitStringConversion());
		}
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

	protected Boolean deriveIsBitStringConversion() {
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
