
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

import org.modeldriven.alf.syntax.expressions.impl.ThisExpressionImpl;

/**
 * An expression comprising the keyword “this”.
 **/

public class ThisExpression extends Expression {

	public ThisExpression() {
		this.impl = new ThisExpressionImpl(this);
	}

	public ThisExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ThisExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public ThisExpressionImpl getImpl() {
		return (ThisExpressionImpl) this.impl;
	}

	/**
	 * The static type of a this expression is the statically determined context
	 * classifier for the context in which the this expression occurs.
	 **/
	public boolean thisExpressionTypeDerivation() {
		return this.getImpl().thisExpressionTypeDerivation();
	}

	/**
	 * The multiplicity upper bound of a this expression is always 1.
	 **/
	public boolean thisExpressionUpperDerivation() {
		return this.getImpl().thisExpressionUpperDerivation();
	}

	/**
	 * The multiplicity lower bound of a this expression is always 1.
	 **/
	public boolean thisExpressionLowerDerivation() {
		return this.getImpl().thisExpressionLowerDerivation();
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.thisExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"thisExpressionTypeDerivation", this));
		}
		if (!this.thisExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"thisExpressionUpperDerivation", this));
		}
		if (!this.thisExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"thisExpressionLowerDerivation", this));
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
} // ThisExpression
