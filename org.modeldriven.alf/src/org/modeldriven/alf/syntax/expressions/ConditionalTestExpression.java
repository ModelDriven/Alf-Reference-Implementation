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
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.ConditionalTestExpressionImpl;

/**
 * An expression that uses the value of one operand expression to condition the
 * evaluation of one of two other operand expressions.
 **/

public class ConditionalTestExpression extends Expression {

	public ConditionalTestExpression() {
		this.impl = new ConditionalTestExpressionImpl(this);
	}

	public ConditionalTestExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public ConditionalTestExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public ConditionalTestExpressionImpl getImpl() {
		return (ConditionalTestExpressionImpl) this.impl;
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

	public Expression getOperand3() {
		return this.getImpl().getOperand3();
	}

	public void setOperand3(Expression operand3) {
		this.getImpl().setOperand3(operand3);
	}

	/**
	 * The type of a conditional-test operator expression is the effective
	 * common ancestor (if one exists) of the types of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionTypeDerivation() {
		return this.getImpl().conditionalTestExpressionTypeDerivation();
	}

	/**
	 * The multiplicity lower bound of a conditional-test operator expression is
	 * the minimum of the multiplicity lower bounds of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionLowerDerivation() {
		return this.getImpl().conditionalTestExpressionLowerDerivation();
	}

	/**
	 * The multiplicity upper bound of a conditional-test operator expression is
	 * the maximum of the multiplicity upper bounds of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionUpperDerivation() {
		return this.getImpl().conditionalTestExpressionUpperDerivation();
	}

	/**
	 * The first operand expression of a conditional-test expression must be of
	 * a type that conforms to type Boolean and have multiplicity lower and upper 
	 * bounds of 1.
	 **/
	public boolean conditionalTestExpressionCondition() {
		return this.getImpl().conditionalTestExpressionCondition();
	}

	/**
     * The assignments before the first operand expression of a conditional-test
     * expression are the same as those before the conditional-test expression.
     * The assignments before the second and third operand expressions are the
     * same as those after the first operand expression, adjusted for known
     * null and non-null names from the first operand expression being true,
     * for the second operand expression, or false, for the third operand
     * expression.
	 **/
	public boolean conditionalTestExpressionAssignmentsBefore() {
		return this.getImpl().conditionalTestExpressionAssignmentsBefore();
	}

    /**
     * Returns unchanged all assignments for local names that are not reassigned
     * in either the second or third operand expressions. Any local names that
     * have different assignments after the second and third operand expressions
     * are adjusted to have the conditional-test expression as their assigned
     * source. If such a local name is defined in one operand expression but not
     * the other, then it is adjusted to have multiplicity lower bound of 0 after
     * the conditional test expression. If a local name has a new assignment after
     * each of the second and third expressions, then, after the conditional-test
     * expression, it has a type that is the effective common ancestor of its type
     * after the second and third operand expressions, a multiplicity lower bound 
     * that is the minimum of the lower bounds after the second and third operand
     * expressions and a multiplicity upper bound that is the maximum of the upper
     * bounds after the second and third expressions.
     **/
	@Override
    public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getOperand1());
        addExternalReferencesFor(references, this.getOperand2());
        addExternalReferencesFor(references, this.getOperand3());
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
		Expression operand3 = this.getOperand3();
		if (operand3 != null) {
			operand3.deriveAll();
		}
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.conditionalTestExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"conditionalTestExpressionTypeDerivation", this));
		}
		if (!this.conditionalTestExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"conditionalTestExpressionLowerDerivation", this));
		}
		if (!this.conditionalTestExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"conditionalTestExpressionUpperDerivation", this));
		}
		if (!this.conditionalTestExpressionCondition()) {
			violations.add(new ConstraintViolation(
					"conditionalTestExpressionCondition", this));
		}
		if (!this.conditionalTestExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"conditionalTestExpressionAssignmentsBefore", this));
		}
		Expression operand1 = this.getOperand1();
		if (operand1 != null) {
			operand1.checkConstraints(violations);
		}
		Expression operand2 = this.getOperand2();
		if (operand2 != null) {
			operand2.checkConstraints(violations);
		}
		Expression operand3 = this.getOperand3();
		if (operand3 != null) {
			operand3.checkConstraints(violations);
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
		Expression operand3 = this.getOperand3();
		if (operand3 != null) {
			System.out.println(prefix + " operand3:");
			operand3.print(prefix + "  ", includeDerived);
		}
	}
} // ConditionalTestExpression
