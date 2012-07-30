
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
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

import org.modeldriven.alf.syntax.expressions.impl.ArithmeticExpressionImpl;

/**
 * A binary expression with an arithmetic operator.
 **/

public class ArithmeticExpression extends BinaryExpression {

	public ArithmeticExpression() {
		this.impl = new ArithmeticExpressionImpl(this);
	}

	public ArithmeticExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ArithmeticExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public ArithmeticExpressionImpl getImpl() {
		return (ArithmeticExpressionImpl) this.impl;
	}

	public Boolean getIsConcatenation() {
		return this.getImpl().getIsConcatenation();
	}

	public void setIsConcatenation(Boolean isConcatenation) {
		this.getImpl().setIsConcatenation(isConcatenation);
	}

	/**
	 * An arithmetic expression is a string concatenation expression if its type
	 * is String.
	 **/
	public boolean arithmeticExpressionIsConcatenationDerivation() {
		return this.getImpl().arithmeticExpressionIsConcatenationDerivation();
	}

	/**
	 * The type of an arithmetic expression is the same as the type of its
	 * operands.
	 **/
	public boolean arithmeticExpressionTypeDerivation() {
		return this.getImpl().arithmeticExpressionTypeDerivation();
	}

	/**
	 * An arithmetic expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean arithmeticExpressionLowerDerivation() {
		return this.getImpl().arithmeticExpressionLowerDerivation();
	}

	/**
	 * An arithmetic expression has a multiplicity upper bound of 1.
	 **/
	public boolean arithmeticExpressionUpperDerivation() {
		return this.getImpl().arithmeticExpressionUpperDerivation();
	}

	/**
	 * The operands of an arithmetic expression must both have type Integer,
	 * unless the operator is +, in which case they may also both have type
	 * String.
	 **/
	public boolean arithmeticExpressionOperandTypes() {
		return this.getImpl().arithmeticExpressionOperandTypes();
	}

	public void _deriveAll() {
		this.getIsConcatenation();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.arithmeticExpressionIsConcatenationDerivation()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionIsConcatenationDerivation", this));
		}
		if (!this.arithmeticExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionTypeDerivation", this));
		}
		if (!this.arithmeticExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionLowerDerivation", this));
		}
		if (!this.arithmeticExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionUpperDerivation", this));
		}
		if (!this.arithmeticExpressionOperandTypes()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionOperandTypes", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isConcatenation:");
			s.append(this.getIsConcatenation());
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
} // ArithmeticExpression
