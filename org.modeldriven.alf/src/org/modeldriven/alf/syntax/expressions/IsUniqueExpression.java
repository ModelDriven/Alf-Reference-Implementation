/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.expressions.impl.IsUniqueExpressionImpl;

/**
 * A sequence expansion expression with a isUnique.
 **/

public class IsUniqueExpression extends SequenceExpansionExpression {

	public IsUniqueExpression() {
		this.impl = new IsUniqueExpressionImpl(this);
	}

	public IsUniqueExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public IsUniqueExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // IsUniqueExpression
