
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
import java.util.TreeSet;

import org.modeldriven.alf.syntax.expressions.impl.LiteralExpressionImpl;

/**
 * An expression that comprises a primitive literal.
 **/

public abstract class LiteralExpression extends Expression {

	public LiteralExpression() {
	}

	public LiteralExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public LiteralExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public LiteralExpressionImpl getImpl() {
		return (LiteralExpressionImpl) this.impl;
	}

	/**
	 * The multiplicity upper bound of a literal expression is always 1.
	 **/
	public boolean literalExpressionUpperDerivation() {
		return this.getImpl().literalExpressionUpperDerivation();
	}

	/**
	 * The multiplicity lower bound of a literal expression is always 1.
	 **/
	public boolean literalExpressionLowerDerivation() {
		return this.getImpl().literalExpressionLowerDerivation();
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.literalExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"literalExpressionUpperDerivation", this));
		}
		if (!this.literalExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"literalExpressionLowerDerivation", this));
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
} // LiteralExpression
