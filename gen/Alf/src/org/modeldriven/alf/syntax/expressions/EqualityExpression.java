
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;

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

import org.modeldriven.alf.syntax.expressions.impl.EqualityExpressionImpl;

/**
 * A binary expression that tests the equality of its operands.
 **/

public class EqualityExpression extends BinaryExpression {

	public EqualityExpression() {
		this.impl = new EqualityExpressionImpl(this);
	}

	public EqualityExpression(AlfParser parser) {
		this();
		this.setParserInfo(parser.getFileName(), parser.getLine(), parser
				.getColumn());
	}

	public EqualityExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public EqualityExpressionImpl getImpl() {
		return (EqualityExpressionImpl) this.impl;
	}

	public Boolean getIsNegated() {
		return this.getImpl().getIsNegated();
	}

	public void setIsNegated(Boolean isNegated) {
		this.getImpl().setIsNegated(isNegated);
	}

	/**
	 * An equality expression is negated if its operator is "!=".
	 **/
	public boolean equalityExpressionIsNegatedDerivation() {
		return this.getImpl().equalityExpressionIsNegatedDerivation();
	}

	/**
	 * An equality expression has type Boolean.
	 **/
	public boolean equalityExpressionTypeDerivation() {
		return this.getImpl().equalityExpressionTypeDerivation();
	}

	/**
	 * An equality expression has a multiplicity lower bound of 1.
	 **/
	public boolean equalityExpressionLowerDerivation() {
		return this.getImpl().equalityExpressionLowerDerivation();
	}

	/**
	 * An equality expression has a multiplicity upper bound of 1.
	 **/
	public boolean equalityExpressionUpperDerivation() {
		return this.getImpl().equalityExpressionUpperDerivation();
	}

	public void _deriveAll() {
		this.getIsNegated();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.equalityExpressionIsNegatedDerivation()) {
			violations.add(new ConstraintViolation(
					"equalityExpressionIsNegatedDerivation", this));
		}
		if (!this.equalityExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"equalityExpressionTypeDerivation", this));
		}
		if (!this.equalityExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"equalityExpressionLowerDerivation", this));
		}
		if (!this.equalityExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"equalityExpressionUpperDerivation", this));
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isNegated:");
			s.append(this.getIsNegated());
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
} // EqualityExpression
