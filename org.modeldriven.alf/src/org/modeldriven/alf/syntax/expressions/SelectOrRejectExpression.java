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
import org.modeldriven.alf.syntax.expressions.impl.SelectOrRejectExpressionImpl;

/**
 * A sequence expansion expression with a select or reject operation.
 **/

public class SelectOrRejectExpression extends SequenceExpansionExpression {

	public SelectOrRejectExpression() {
		this.impl = new SelectOrRejectExpressionImpl(this);
	}

	public SelectOrRejectExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public SelectOrRejectExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
     * The argument of a select or reject expression must have a type that
     * conforms to type Boolean and a multiplicity upper bound of 1.
     **/
	public boolean selectOrRejectExpressionArgument() {
		return this.getImpl().selectOrRejectExpressionArgument();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
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
} // SelectOrRejectExpression
