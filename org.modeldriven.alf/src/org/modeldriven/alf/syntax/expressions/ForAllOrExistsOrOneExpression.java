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

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ParsedElement;
import org.modeldriven.alf.syntax.expressions.impl.ForAllOrExistsOrOneExpressionImpl;

/**
 * A sequence expansion expression with a forAll, exists or one operation.
 **/

public class ForAllOrExistsOrOneExpression extends SequenceExpansionExpression {

	public ForAllOrExistsOrOneExpression() {
		this.impl = new ForAllOrExistsOrOneExpressionImpl(this);
	}

	public ForAllOrExistsOrOneExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public ForAllOrExistsOrOneExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ForAllOrExistsOrOneExpressionImpl getImpl() {
		return (ForAllOrExistsOrOneExpressionImpl) this.impl;
	}

	/**
	 * A forAll, exists or one expression has the type Boolean.
	 **/
	public boolean forAllOrExistsOrOneExpressionTypeDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionTypeDerivation();
	}

	/**
	 * A forAll, exists or one expression has a multiplicity lower bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionLowerDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionLowerDerivation();
	}

	/**
	 * A forAll, exists or one expression has a multiplicity upper bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionUpperDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionUpperDerivation();
	}

    /**
     * The argument of a forAll, exists or one expression must have a type that
     * conforms to type Boolean and a multiplicity upper bound of 1.
     **/
	public boolean forAllOrExistsOrOneExpressionArgument() {
		return this.getImpl().forAllOrExistsOrOneExpressionArgument();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.forAllOrExistsOrOneExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"forAllOrExistsOrOneExpressionTypeDerivation", this));
		}
		if (!this.forAllOrExistsOrOneExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"forAllOrExistsOrOneExpressionLowerDerivation", this));
		}
		if (!this.forAllOrExistsOrOneExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"forAllOrExistsOrOneExpressionUpperDerivation", this));
		}
		if (!this.forAllOrExistsOrOneExpressionArgument()) {
			violations.add(new ConstraintViolation(
					"forAllOrExistsOrOneExpressionArgument", this));
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
} // ForAllOrExistsOrOneExpression
