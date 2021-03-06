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
import org.modeldriven.alf.syntax.expressions.impl.BinaryExpressionImpl;

/**
 * An expression consisting of an operator acting on two operand expressions.
 **/

public abstract class BinaryExpression extends Expression {

	@Override
    public BinaryExpressionImpl getImpl() {
		return (BinaryExpressionImpl) this.impl;
	}

	public Expression getOperand1() {
		return this.getImpl().getOperand1();
	}

	public void setOperand1(Expression operand1) {
		this.getImpl().setOperand1(operand1);
	}

	public Expression getOperand2() {
		return this.getImpl().getOperand2();
	}

	public void setOperand2(Expression operand2) {
		this.getImpl().setOperand2(operand2);
	}

	public String getOperator() {
		return this.getImpl().getOperator();
	}

	public void setOperator(String operator) {
		this.getImpl().setOperator(operator);
	}

    /**
     * The operands of a binary expression must both have a multiplicity lower
     * bound no less than that given by the minLowerBound helper operation. The
     * operands of a binary expression must both have a multiplicity upper bound
     * no greater than that given by the maxUpperBound helper operation.
     **/
	public boolean binaryExpressionOperandMultiplicity() {
		return this.getImpl().binaryExpressionOperandMultiplicity();
	}

	/**
	 * The assignments in the operand expressions of a binary expression must be
	 * valid (as determined by the validateAssignments helper operation).
	 **/
	public boolean binaryExpressionOperandAssignments() {
		return this.getImpl().binaryExpressionOperandAssignments();
	}

	/**
	 * In general the assignments before the operand expressions of a binary
	 * expression are the same as those before the binary expression and, if an
	 * assignment for a name is changed in one operand expression, then the
	 * assignment for that name may not change in the other operand expression.
	 * (This is overridden for conditional logical expressions.)
	 **/
	public Boolean validateAssignments() {
		return this.getImpl().validateAssignments();
	}

	/**
	 * The assignments after a binary expression include all the assignments
	 * before the expression that are not reassigned in either operand
	 * expression, plus the new assignments from each of the operand
	 * expressions.
	 **/
	@Override
    public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

    /**
     * By default, the minimum allowed lower bound for an operand of a binary
     * expression is 1.
     **/
	public Integer minLowerBound() {
		return this.getImpl().minLowerBound();
	}

    /**
     * By default, the maximum allowed upper bound for an operand of a binary
     * expression is 1.
     **/
    public Integer maxUpperBound() {
        return this.getImpl().maxUpperBound();
    }

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getOperand1());
        addExternalReferencesFor(references, this.getOperand2());
    }

	@Override
    public void _deriveAll() {
		super._deriveAll();
		Expression operand1 = this.getOperand1();
		if (operand1 != null) {
			operand1.deriveAll();
		}
		Expression operand2 = this.getOperand2();
		if (operand2 != null) {
			operand2.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.binaryExpressionOperandMultiplicity()) {
			violations.add(new ConstraintViolation(
					"binaryExpressionOperandMultiplicity", this));
		}
		if (!this.binaryExpressionOperandAssignments()) {
			violations.add(new ConstraintViolation(
					"binaryExpressionOperandAssignments", this));
		}
		Expression operand1 = this.getOperand1();
		if (operand1 != null) {
			operand1.checkConstraints(violations);
		}
		Expression operand2 = this.getOperand2();
		if (operand2 != null) {
			operand2.checkConstraints(violations);
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
		Expression operand1 = this.getOperand1();
		if (operand1 != null) {
			System.out.println(prefix + " operand1:");
			operand1.print(prefix + "  ", includeDerived);
		}
		Expression operand2 = this.getOperand2();
		if (operand2 != null) {
			System.out.println(prefix + " operand2:");
			operand2.print(prefix + "  ", includeDerived);
		}
	}
} // BinaryExpression
