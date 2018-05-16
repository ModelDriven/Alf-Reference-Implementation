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
import org.modeldriven.alf.syntax.expressions.impl.IsolationExpressionImpl;

/**
 * An expression used to evaluate its operand expression in isolation.
 **/

public class IsolationExpression extends UnaryExpression {

	public IsolationExpression() {
		this.impl = new IsolationExpressionImpl(this);
	}

	public IsolationExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public IsolationExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public IsolationExpressionImpl getImpl() {
		return (IsolationExpressionImpl) this.impl;
	}

	/**
	 * An isolation expression has the type of its operand expression.
	 **/
	public boolean isolationExpressionTypeDerivation() {
		return this.getImpl().isolationExpressionTypeDerivation();
	}

	/**
	 * An isolation expression has the multiplicity lower bound of its operand
	 * expression.
	 **/
	public boolean isolationExpressionLowerDerivation() {
		return this.getImpl().isolationExpressionLowerDerivation();
	}

	/**
	 * An isolation expression has the multiplicity upper bound of its operand
	 * expression.
	 **/
	public boolean isolationExpressionUpperDerivation() {
		return this.getImpl().isolationExpressionUpperDerivation();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.isolationExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"isolationExpressionTypeDerivation", this));
		}
		if (!this.isolationExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"isolationExpressionLowerDerivation", this));
		}
		if (!this.isolationExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"isolationExpressionUpperDerivation", this));
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
} // IsolationExpression
