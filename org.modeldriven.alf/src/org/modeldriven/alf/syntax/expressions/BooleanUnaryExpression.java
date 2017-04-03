/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ParsedElement;
import org.modeldriven.alf.syntax.expressions.impl.BooleanUnaryExpressionImpl;

/**
 * A unary expression with a Boolean operator.
 **/

public class BooleanUnaryExpression extends UnaryExpression {

	public BooleanUnaryExpression() {
		this.impl = new BooleanUnaryExpressionImpl(this);
	}

	public BooleanUnaryExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public BooleanUnaryExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public BooleanUnaryExpressionImpl getImpl() {
		return (BooleanUnaryExpressionImpl) this.impl;
	}

	/**
	 * A Boolean unary expression has type Boolean.
	 **/
	public boolean booleanUnaryExpressionTypeDerivation() {
		return this.getImpl().booleanUnaryExpressionTypeDerivation();
	}

	/**
	 * A Boolean unary expression has the same multiplicity lower bound as its
	 * operand expression.
	 **/
	public boolean booleanUnaryExpressionLowerDerivation() {
		return this.getImpl().booleanUnaryExpressionLowerDerivation();
	}

	/**
	 * A Boolean unary expression has a multiplicity upper bound of 1.
	 **/
	public boolean booleanUnaryExpressionUpperDerivation() {
		return this.getImpl().booleanUnaryExpressionUpperDerivation();
	}

    /**
     * The operand expression of a Boolean unary expression must have a type
     * that conforms to type Boolean and a multiplicity upper bound of 1.
     **/
	public boolean booleanUnaryExpressionOperand() {
		return this.getImpl().booleanUnaryExpressionOperand();
	}

    /**
     * If the expression is a negation, then check the operand expression for
     * known nulls and non-nulls based on the negation of the given truth
     * condition.
     */
    @Override
    public Collection<AssignedSource> adjustAssignments(
            Collection<AssignedSource> assignments, boolean condition) {
        return this.getImpl().adjustAssignments(assignments, condition);
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.booleanUnaryExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"booleanUnaryExpressionTypeDerivation", this));
		}
		if (!this.booleanUnaryExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"booleanUnaryExpressionLowerDerivation", this));
		}
		if (!this.booleanUnaryExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"booleanUnaryExpressionUpperDerivation", this));
		}
		if (!this.booleanUnaryExpressionOperand()) {
			violations.add(new ConstraintViolation(
					"booleanUnaryExpressionOperand", this));
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
} // BooleanUnaryExpression
