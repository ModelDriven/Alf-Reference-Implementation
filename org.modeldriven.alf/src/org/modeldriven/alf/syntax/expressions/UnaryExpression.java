
/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.UnaryExpressionImpl;

/**
 * An expression consisting of an operator acting on a single operand
 * expression.
 **/

public abstract class UnaryExpression extends Expression {

	@Override
    public UnaryExpressionImpl getImpl() {
		return (UnaryExpressionImpl) this.impl;
	}

	public String getOperator() {
		return this.getImpl().getOperator();
	}

	public void setOperator(String operator) {
		this.getImpl().setOperator(operator);
	}

	public Expression getOperand() {
		return this.getImpl().getOperand();
	}

	public void setOperand(Expression operand) {
		this.getImpl().setOperand(operand);
	}

	/**
	 * The assignments before the operand of a unary expression are the same as
	 * those before the unary expression.
	 **/
	public boolean unaryExpressionAssignmentsBefore() {
		return this.getImpl().unaryExpressionAssignmentsBefore();
	}

	/**
	 * By default, the assignments after a unary expression are the same as
	 * those after its operand expression.
	 **/
	@Override
    public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getOperand());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Expression operand = this.getOperand();
		if (operand != null) {
			operand.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.unaryExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"unaryExpressionAssignmentsBefore", this));
		}
		Expression operand = this.getOperand();
		if (operand != null) {
			operand.checkConstraints(violations);
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" operator:");
		s.append(this.getOperator());
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
		Expression operand = this.getOperand();
		if (operand != null) {
			System.out.println(prefix + " operand:");
			operand.print(prefix + "  ", includeDerived);
		}
	}
} // UnaryExpression
