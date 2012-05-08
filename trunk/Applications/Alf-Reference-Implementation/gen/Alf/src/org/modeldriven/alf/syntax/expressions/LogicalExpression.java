
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
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

import org.modeldriven.alf.syntax.expressions.impl.LogicalExpressionImpl;

/**
 * A binary expression with a logical operator.
 **/

public class LogicalExpression extends BinaryExpression {

	public LogicalExpression() {
		this.impl = new LogicalExpressionImpl(this);
	}

	public LogicalExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public LogicalExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public LogicalExpressionImpl getImpl() {
		return (LogicalExpressionImpl) this.impl;
	}

	public Boolean getIsBitWise() {
		return this.getImpl().getIsBitWise();
	}

	public void setIsBitWise(Boolean isBitWise) {
		this.getImpl().setIsBitWise(isBitWise);
	}

	public Boolean getIsBitStringConversion1() {
		return this.getImpl().getIsBitStringConversion1();
	}

	public void setIsBitStringConversion1(Boolean isBitStringConversion1) {
		this.getImpl().setIsBitStringConversion1(isBitStringConversion1);
	}

	public Boolean getIsBitStringConversion2() {
		return this.getImpl().getIsBitStringConversion2();
	}

	public void setIsBitStringConversion2(Boolean isBitStringConversion2) {
		this.getImpl().setIsBitStringConversion2(isBitStringConversion2);
	}

	/**
	 * A logical expression has type Boolean.
	 **/
	public boolean logicalExpressionTypeDerivation() {
		return this.getImpl().logicalExpressionTypeDerivation();
	}

	/**
	 * A logical expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean logicalExpressionLowerDerivation() {
		return this.getImpl().logicalExpressionLowerDerivation();
	}

	/**
	 * A logical expression has a multiplicity upper bound of 1.
	 **/
	public boolean logicalExpressionUpperDerivation() {
		return this.getImpl().logicalExpressionUpperDerivation();
	}

	/**
	 * The operands of a logical expression must have type Boolean.
	 **/
	public boolean logicalExpressionOperands() {
		return this.getImpl().logicalExpressionOperands();
	}

	/**
	 * BitString conversion is required if the first operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion1Derivation() {
		return this.getImpl()
				.logicalExpressionIsBitStringConversion1Derivation();
	}

	/**
	 * BitString conversion is required if the second operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion2Derivation() {
		return this.getImpl()
				.logicalExpressionIsBitStringConversion2Derivation();
	}

	/**
	 * A logical expression is bit-wise if the type of its first operand is not
	 * Boolean.
	 **/
	public boolean logicalExpressionIsBitWiseDerivation() {
		return this.getImpl().logicalExpressionIsBitWiseDerivation();
	}

	public void _deriveAll() {
		this.getIsBitWise();
		this.getIsBitStringConversion1();
		this.getIsBitStringConversion2();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.logicalExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionTypeDerivation", this));
		}
		if (!this.logicalExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionLowerDerivation", this));
		}
		if (!this.logicalExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionUpperDerivation", this));
		}
		if (!this.logicalExpressionOperands()) {
			violations.add(new ConstraintViolation("logicalExpressionOperands",
					this));
		}
		if (!this.logicalExpressionIsBitStringConversion1Derivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionIsBitStringConversion1Derivation", this));
		}
		if (!this.logicalExpressionIsBitStringConversion2Derivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionIsBitStringConversion2Derivation", this));
		}
		if (!this.logicalExpressionIsBitWiseDerivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionIsBitWiseDerivation", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isBitWise:");
			s.append(this.getIsBitWise());
		}
		if (includeDerived) {
			s.append(" /isBitStringConversion1:");
			s.append(this.getIsBitStringConversion1());
		}
		if (includeDerived) {
			s.append(" /isBitStringConversion2:");
			s.append(this.getIsBitStringConversion2());
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
} // LogicalExpression
