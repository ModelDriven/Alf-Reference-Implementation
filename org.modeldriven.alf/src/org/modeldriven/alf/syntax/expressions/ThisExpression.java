/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
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
import org.modeldriven.alf.syntax.expressions.impl.ThisExpressionImpl;

/**
 * An expression comprising the keyword �this�.
 **/

public class ThisExpression extends Expression {

	public ThisExpression() {
		this.impl = new ThisExpressionImpl(this);
	}

	public ThisExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public ThisExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
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
} // ThisExpression
