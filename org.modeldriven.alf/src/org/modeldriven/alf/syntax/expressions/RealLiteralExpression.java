/*******************************************************************************
 * Copyright 2016, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.syntax.expressions.impl.RealLiteralExpressionImpl;

/**
 * An expression that comprises a real literal.
 **/

public class RealLiteralExpression extends LiteralExpression {

	public RealLiteralExpression() {
		this.impl = new RealLiteralExpressionImpl(this);
	}

	public RealLiteralExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public RealLiteralExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public RealLiteralExpressionImpl getImpl() {
		return (RealLiteralExpressionImpl) this.impl;
	}

	public String getImage() {
		return this.getImpl().getImage();
	}

	public void setImage(String image) {
		this.getImpl().setImage(image);
	}

	/**
	 * The type of a real literal expression is the Alf library type Real.
	 **/
	public boolean realLiteralExpressionTypeDerivation() {
		return this.getImpl().realLiteralExpressionTypeDerivation();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.realLiteralExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"realLiteralExpressionTypeDerivation", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" image:");
		s.append(this.getImage());
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
}
