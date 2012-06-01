
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
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

import org.modeldriven.alf.syntax.expressions.impl.SelectOrRejectExpressionImpl;

/**
 * A sequence expansion expression with a select or reject operation.
 **/

public class SelectOrRejectExpression extends SequenceExpansionExpression {

	public SelectOrRejectExpression() {
		this.impl = new SelectOrRejectExpressionImpl(this);
	}

	public SelectOrRejectExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SelectOrRejectExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SelectOrRejectExpressionImpl getImpl() {
		return (SelectOrRejectExpressionImpl) this.impl;
	}

	/**
	 * A select or reject expression has the same type as its primary
	 * expression.
	 **/
	public boolean selectOrRejectExpressionTypeDerivation() {
		return this.getImpl().selectOrRejectExpressionTypeDerivation();
	}

	/**
	 * A select or reject expression has a multiplicity lower bound of 0.
	 **/
	public boolean selectOrRejectExpressionLowerDerivation() {
		return this.getImpl().selectOrRejectExpressionLowerDerivation();
	}

	/**
	 * A select or reject expression has a multiplicity upper bound of *.
	 **/
	public boolean selectOrRejectExpressionUpperDerivation() {
		return this.getImpl().selectOrRejectExpressionUpperDerivation();
	}

	/**
	 * The argument of a select or reject expression must have type Boolean and
	 * a multiplicity upper bound of 1.
	 **/
	public boolean selectOrRejectExpressionArgument() {
		return this.getImpl().selectOrRejectExpressionArgument();
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.selectOrRejectExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"selectOrRejectExpressionTypeDerivation", this));
		}
		if (!this.selectOrRejectExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"selectOrRejectExpressionLowerDerivation", this));
		}
		if (!this.selectOrRejectExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"selectOrRejectExpressionUpperDerivation", this));
		}
		if (!this.selectOrRejectExpressionArgument()) {
			violations.add(new ConstraintViolation(
					"selectOrRejectExpressionArgument", this));
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
} // SelectOrRejectExpression
