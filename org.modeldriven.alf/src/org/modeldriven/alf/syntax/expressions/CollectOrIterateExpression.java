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
import org.modeldriven.alf.syntax.expressions.impl.CollectOrIterateExpressionImpl;

/**
 * A sequence expansion expression with a collect or iterate operation.
 **/

public class CollectOrIterateExpression extends SequenceExpansionExpression {

	public CollectOrIterateExpression() {
		this.impl = new CollectOrIterateExpressionImpl(this);
	}

	public CollectOrIterateExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public CollectOrIterateExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public CollectOrIterateExpressionImpl getImpl() {
		return (CollectOrIterateExpressionImpl) this.impl;
	}

	/**
	 * A collect or iterate expression has the same type as its argument
	 * expression.
	 **/
	public boolean collectOrIterateExpressionTypeDerivation() {
		return this.getImpl().collectOrIterateExpressionTypeDerivation();
	}

	/**
	 * A collect or iterate expression has a multiplicity lower bound that is
	 * the product of the bounds of its primary and argument expressions.
	 **/
	public boolean collectOrIterateExpressionLowerDerivation() {
		return this.getImpl().collectOrIterateExpressionLowerDerivation();
	}

	/**
	 * A collect or iterate expression has a multiplicity upper bound that is
	 * the product of the bounds of its primary and argument expressions.
	 **/
	public boolean collectOrIterateExpressionUpperDerivation() {
		return this.getImpl().collectOrIterateExpressionUpperDerivation();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.collectOrIterateExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"collectOrIterateExpressionTypeDerivation", this));
		}
		if (!this.collectOrIterateExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"collectOrIterateExpressionLowerDerivation", this));
		}
		if (!this.collectOrIterateExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"collectOrIterateExpressionUpperDerivation", this));
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
} // CollectOrIterateExpression
