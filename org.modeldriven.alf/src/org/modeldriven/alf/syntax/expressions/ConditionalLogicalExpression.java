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
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.expressions.impl.ConditionalLogicalExpressionImpl;

/**
 * A binary expression with a conditional logical expression, for which the
 * evaluation of the second operand expression is conditioned on the result of
 * evaluating the first operand expression.
 **/

public class ConditionalLogicalExpression extends BinaryExpression {

	public ConditionalLogicalExpression() {
		this.impl = new ConditionalLogicalExpressionImpl(this);
	}

	public ConditionalLogicalExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public ConditionalLogicalExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ConditionalLogicalExpressionImpl getImpl() {
		return (ConditionalLogicalExpressionImpl) this.impl;
	}

	/**
	 * A conditional logical expression has type Boolean.
	 **/
	public boolean conditionalLogicalExpressionTypeDerivation() {
		return this.getImpl().conditionalLogicalExpressionTypeDerivation();
	}

	/**
	 * A conditional logical expression has a multiplicity lower bound of 1.
	 **/
	public boolean conditionalLogicalExpressionLowerDerivation() {
		return this.getImpl().conditionalLogicalExpressionLowerDerivation();
	}

	/**
	 * A conditional logical expression has a multiplicity upper bound of 1.
	 **/
	public boolean conditionalLogicalExpressionUpperDerivation() {
		return this.getImpl().conditionalLogicalExpressionUpperDerivation();
	}

	/**
	 * The operands of a conditional logical expression must have a type that 
	 * conforms to type Boolean.
	 **/
	public boolean conditionalLogicalExpressionOperands() {
		return this.getImpl().conditionalLogicalExpressionOperands();
	}

	/**
     * The assignments before the first operand expression of a conditional
     * logical expression are the same as those before the conditional logical
     * expression. The assignments before the second operand expression are the
     * same as those after the first operand expression, adjusted for known
     * nulls and non-nulls based on the first operand expression being true,
     * for a conditional-and expression, or false, for a conditional-or
     * expression.
	 **/
	@Override
    public Boolean validateAssignments() {
		return this.getImpl().validateAssignments();
	}

	/**
     * If a name has the same assigned source after the second operand
     * expression as before it, then that is its assigned source after the
     * conditional logical expression. Otherwise its assigned source after the
     * conditional logical expression is the conditional logical expression
     * itself. If a name is unassigned before the second operand expression but
     * assigned after it, then it has a multiplicity lower bound of 0 after the
     * conditional logical expression.
	 **/
	@Override
    public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	@Override
    public void _deriveAll() {
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.conditionalLogicalExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"conditionalLogicalExpressionTypeDerivation", this));
		}
		if (!this.conditionalLogicalExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"conditionalLogicalExpressionLowerDerivation", this));
		}
		if (!this.conditionalLogicalExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"conditionalLogicalExpressionUpperDerivation", this));
		}
		if (!this.conditionalLogicalExpressionOperands()) {
			violations.add(new ConstraintViolation(
					"conditionalLogicalExpressionOperands", this));
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
} // ConditionalLogicalExpression
