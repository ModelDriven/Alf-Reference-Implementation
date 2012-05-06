
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

import org.modeldriven.alf.syntax.expressions.impl.IsUniqueExpressionImpl;

/**
 * A sequence expansion expression with a isUnique.
 **/

public class IsUniqueExpression extends SequenceExpansionExpression {

	public IsUniqueExpression() {
		this.impl = new IsUniqueExpressionImpl(this);
	}

	public IsUniqueExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public IsUniqueExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public IsUniqueExpressionImpl getImpl() {
		return (IsUniqueExpressionImpl) this.impl;
	}

	/**
	 * An isUnique expression has the type Boolean.
	 **/
	public boolean isUniqueExpressionTypeDerivation() {
		return this.getImpl().isUniqueExpressionTypeDerivation();
	}

	/**
	 * An isUnique expression has a multiplicity lower bound of 1.
	 **/
	public boolean isUniqueExpressionLowerDerivation() {
		return this.getImpl().isUniqueExpressionLowerDerivation();
	}

	/**
	 * An isUnique expression has a multiplicity upper bound of 1.
	 **/
	public boolean isUniqueExpressionUpperDerivation() {
		return this.getImpl().isUniqueExpressionUpperDerivation();
	}

	/**
	 * The argument of an isUnique expression must have a multiplicity upper
	 * bound of 1.
	 **/
	public boolean isUniqueExpressionExpressionArgument() {
		return this.getImpl().isUniqueExpressionExpressionArgument();
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.isUniqueExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"isUniqueExpressionTypeDerivation", this));
		}
		if (!this.isUniqueExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"isUniqueExpressionLowerDerivation", this));
		}
		if (!this.isUniqueExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"isUniqueExpressionUpperDerivation", this));
		}
		if (!this.isUniqueExpressionExpressionArgument()) {
			violations.add(new ConstraintViolation(
					"isUniqueExpressionExpressionArgument", this));
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
} // IsUniqueExpression
