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
import org.modeldriven.alf.syntax.expressions.impl.OutputNamedExpressionImpl;

/**
 * A named argument expression for an output parameter.
 **/

public class OutputNamedExpression extends NamedExpression {

	public OutputNamedExpression() {
		this.impl = new OutputNamedExpressionImpl(this);
	}

	public OutputNamedExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public OutputNamedExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public OutputNamedExpressionImpl getImpl() {
		return (OutputNamedExpressionImpl) this.impl;
	}

	public LeftHandSide getLeftHandSide() {
		return this.getImpl().getLeftHandSide();
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.getImpl().setLeftHandSide(leftHandSide);
	}

	/**
	 * The equivalent left-hand side for an output named expression depends on
	 * the kind of expression. If the expression is a name expression with no
	 * disambiguation, then the left-hand side is a name left-hand side with the
	 * name from the name expression. If the expression is a name expression
	 * that disambiguates to a feature reference, then the left-hand side is a
	 * feature left-hand side for that feature reference. If the expression is a
	 * property access expression, then the left-hand side is a feature
	 * left-hand side for the feature reference of the property access
	 * expression. If the expression is a sequence access expression, then the
	 * left-hand side is a name left-hand side or feature left-hand side, as
	 * above, depending on whether the primary expression of the sequence access
	 * expression is a name expression or property access expression, and an
	 * index given by the index expression of the sequence access expression.
	 * Otherwise the left-hand side is empty.
	 **/
	public boolean outputNamedExpressionLeftHandSideDerivation() {
		return this.getImpl().outputNamedExpressionLeftHandSideDerivation();
	}

	/**
	 * The argument for an output parameter must be either be null, a name
	 * expression, a property access expression, or a sequence access expression
	 * whose primary expression is a name expression or a property access
	 * expression.
	 **/
	public boolean outputNamedExpressionForm() {
		return this.getImpl().outputNamedExpressionForm();
	}

	@Override
    public void _deriveAll() {
		this.getLeftHandSide();
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.outputNamedExpressionLeftHandSideDerivation()) {
			violations.add(new ConstraintViolation(
					"outputNamedExpressionLeftHandSideDerivation", this));
		}
		if (!this.outputNamedExpressionForm()) {
			violations.add(new ConstraintViolation("outputNamedExpressionForm",
					this));
		}
		LeftHandSide lhs = this.getLeftHandSide();
		if (lhs != null) {
			lhs.checkConstraints(violations);
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
		if (includeDerived) {
			LeftHandSide leftHandSide = this.getLeftHandSide();
			if (leftHandSide != null) {
				System.out.println(prefix + " /leftHandSide:"
						+ leftHandSide.toString(includeDerived));
			}
		}
	}
} // OutputNamedExpression
