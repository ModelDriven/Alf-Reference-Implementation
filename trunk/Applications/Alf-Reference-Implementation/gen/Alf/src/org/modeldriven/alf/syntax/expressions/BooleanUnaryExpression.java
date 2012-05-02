
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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

import org.modeldriven.alf.syntax.expressions.impl.BooleanUnaryExpressionImpl;

/**
 * A unary expression with a Boolean operator.
 **/

public class BooleanUnaryExpression extends UnaryExpression {

	public BooleanUnaryExpression() {
		this.impl = new BooleanUnaryExpressionImpl(this);
	}

	public BooleanUnaryExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public BooleanUnaryExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.booleanUnaryExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"booleanUnaryExpressionTypeDerivation", this));
		}
		if (!this.booleanUnaryExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"booleanUnaryExpressionLowerDerivation", this));
		}
		if (!this.booleanUnaryExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"booleanUnaryExpressionUpperDerivation", this));
		}
		if (!this.booleanUnaryExpressionOperand()) {
			violations.add(new ConstraintViolation(
					"booleanUnaryExpressionOperand", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
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
} // BooleanUnaryExpression
