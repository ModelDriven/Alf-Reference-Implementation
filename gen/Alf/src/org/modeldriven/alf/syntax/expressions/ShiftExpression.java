
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

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

import org.modeldriven.alf.syntax.expressions.impl.ShiftExpressionImpl;

public class ShiftExpression extends BinaryExpression {

	public ShiftExpression() {
		this.impl = new ShiftExpressionImpl(this);
	}

	public ShiftExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ShiftExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public ShiftExpressionImpl getImpl() {
		return (ShiftExpressionImpl) this.impl;
	}

	public Boolean getIsBitStringConversion() {
		return this.getImpl().getIsBitStringConversion();
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.getImpl().setIsBitStringConversion(isBitStringConversion);
	}

	/**
	 * A shift expression has type BitString.
	 **/
	public boolean shiftExpressionTypeDerivation() {
		return this.getImpl().shiftExpressionTypeDerivation();
	}

	/**
	 * A shift expression has a multiplicity lower bound of 0 if the lower bound
	 * if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean shiftExpressionLowerDerivation() {
		return this.getImpl().shiftExpressionLowerDerivation();
	}

	/**
	 * A shift expression has a multiplicity upper bound of 1.
	 **/
	public boolean shiftExpressionUpperDerivation() {
		return this.getImpl().shiftExpressionUpperDerivation();
	}

	/**
	 * The operands of a shift expression must have type BitString or Integer.
	 **/
	public boolean shiftExpressionOperands() {
		return this.getImpl().shiftExpressionOperands();
	}

	/**
	 * BitString conversion is required if the first operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean shiftExpressionIsBitStringConversionDerivation() {
		return this.getImpl().shiftExpressionIsBitStringConversionDerivation();
	}

	public void _deriveAll() {
		this.getIsBitStringConversion();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.shiftExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"shiftExpressionTypeDerivation", this));
		}
		if (!this.shiftExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"shiftExpressionLowerDerivation", this));
		}
		if (!this.shiftExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"shiftExpressionUpperDerivation", this));
		}
		if (!this.shiftExpressionOperands()) {
			violations.add(new ConstraintViolation("shiftExpressionOperands",
					this));
		}
		if (!this.shiftExpressionIsBitStringConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"shiftExpressionIsBitStringConversionDerivation", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isBitStringConversion:");
			s.append(this.getIsBitStringConversion());
		}
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // ShiftExpression
