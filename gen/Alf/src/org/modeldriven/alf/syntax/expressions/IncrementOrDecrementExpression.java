
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.IncrementOrDecrementExpressionImpl;

/**
 * A unary expression with either an increment or decrement operator.
 **/

public class IncrementOrDecrementExpression extends Expression {

	public IncrementOrDecrementExpression() {
		this.impl = new IncrementOrDecrementExpressionImpl(this);
	}

	public IncrementOrDecrementExpression(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public IncrementOrDecrementExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public IncrementOrDecrementExpressionImpl getImpl() {
		return (IncrementOrDecrementExpressionImpl) this.impl;
	}

	public String getOperator() {
		return this.getImpl().getOperator();
	}

	public void setOperator(String operator) {
		this.getImpl().setOperator(operator);
	}

	public AssignedSource getAssignment() {
		return this.getImpl().getAssignment();
	}

	public void setAssignment(AssignedSource assignment) {
		this.getImpl().setAssignment(assignment);
	}

	public LeftHandSide getOperand() {
		return this.getImpl().getOperand();
	}

	public void setOperand(LeftHandSide operand) {
		this.getImpl().setOperand(operand);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public ElementReference getFeature() {
		return this.getImpl().getFeature();
	}

	public void setFeature(ElementReference feature) {
		this.getImpl().setFeature(feature);
	}

	public Boolean getIsPrefix() {
		return this.getImpl().getIsPrefix();
	}

	public void setIsPrefix(Boolean isPrefix) {
		this.getImpl().setIsPrefix(isPrefix);
	}

	public Boolean getIsFeature() {
		return this.getImpl().getIsFeature();
	}

	public void setIsFeature(Boolean isFeature) {
		this.getImpl().setIsFeature(isFeature);
	}

	public Boolean getIsIndexed() {
		return this.getImpl().getIsIndexed();
	}

	public void setIsIndexed(Boolean isIndexed) {
		this.getImpl().setIsIndexed(isIndexed);
	}

	public Boolean getIsDataValueUpdate() {
		return this.getImpl().getIsDataValueUpdate();
	}

	public void setIsDataValueUpdate(Boolean isDataValueUpdate) {
		this.getImpl().setIsDataValueUpdate(isDataValueUpdate);
	}

	/**
	 * If the operand of an increment or decrement expression is a name, then
	 * the assignment for the expression is a new assigned source for the name
	 * with the expression as the source.
	 **/
	public boolean incrementOrDecrementExpressionAssignment() {
		return this.getImpl().incrementOrDecrementExpressionAssignment();
	}

	/**
	 * An increment or decrement expression has a feature as its operand if the
	 * operand is a kind of FeatureLeftHandSide.
	 **/
	public boolean incrementOrDecrementExpressionIsFeatureDerivation() {
		return this.getImpl()
				.incrementOrDecrementExpressionIsFeatureDerivation();
	}

	/**
	 * An increment or decrement expression is indexed if its operand is
	 * indexed.
	 **/
	public boolean incrementOrDecrementExpressionIsIndexedDerivation() {
		return this.getImpl()
				.incrementOrDecrementExpressionIsIndexedDerivation();
	}

	/**
	 * An increment or decrement expression is a data value update if its
	 * operand is an attribute of a data value held in a local name or
	 * parameter.
	 **/
	public boolean incrementOrDecrementExpressionIsDataValueUpdate() {
		return this.getImpl().incrementOrDecrementExpressionIsDataValueUpdate();
	}

	/**
	 * If the operand of an increment or decrement expression is a feature, then
	 * the referent for the operand.
	 **/
	public boolean incrementOrDecrementExpressionFeature() {
		return this.getImpl().incrementOrDecrementExpressionFeature();
	}

	/**
	 * The effective expression for the operand of an increment or decrement
	 * expression is the operand treated as a name expression, property access
	 * expression or sequence access expression, as appropriate for evaluation
	 * to obtain the original value to be updated.
	 **/
	public boolean incrementOrDecrementExpressionExpressionDerivation() {
		return this.getImpl()
				.incrementOrDecrementExpressionExpressionDerivation();
	}

	/**
	 * An increment or decrement expression has type Integer.
	 **/
	public boolean incrementOrDecrementExpressionTypeDerivation() {
		return this.getImpl().incrementOrDecrementExpressionTypeDerivation();
	}

	/**
	 * An increment or decrement expression has the same multiplicity lower
	 * bound as its operand expression.
	 **/
	public boolean incrementOrDecrementExpressionLowerDerivation() {
		return this.getImpl().incrementOrDecrementExpressionLowerDerivation();
	}

	/**
	 * An increment or decrement expression has a multiplicity upper bound of 1.
	 **/
	public boolean incrementOrDecrementExpressionUpperDerivation() {
		return this.getImpl().incrementOrDecrementExpressionUpperDerivation();
	}

	/**
	 * The operand expression must have type Integer and a multiplicity upper
	 * bound of 1.
	 **/
	public boolean incrementOrDecrementExpressionOperand() {
		return this.getImpl().incrementOrDecrementExpressionOperand();
	}

	/**
	 * The assignments before the operand of an increment or decrement
	 * expression are the same as those before the increment or decrement
	 * expression.
	 **/
	public boolean incrementOrDecrementExpressionAssignmentsBefore() {
		return this.getImpl().incrementOrDecrementExpressionAssignmentsBefore();
	}

	/**
	 * The assignments after an increment and decrement expression include all
	 * those after its operand expression. Further, if the operand expression,
	 * considered as a left hand side, is a local name, then this is reassigned.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public void _deriveAll() {
		this.getAssignment();
		this.getExpression();
		this.getFeature();
		this.getIsFeature();
		this.getIsIndexed();
		this.getIsDataValueUpdate();
		super._deriveAll();
		LeftHandSide operand = this.getOperand();
		if (operand != null) {
			operand.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.incrementOrDecrementExpressionAssignment()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionAssignment", this));
		}
		if (!this.incrementOrDecrementExpressionIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionIsFeatureDerivation", this));
		}
		if (!this.incrementOrDecrementExpressionIsIndexedDerivation()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionIsIndexedDerivation", this));
		}
		if (!this.incrementOrDecrementExpressionIsDataValueUpdate()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionIsDataValueUpdate", this));
		}
		if (!this.incrementOrDecrementExpressionFeature()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionFeature", this));
		}
		if (!this.incrementOrDecrementExpressionExpressionDerivation()) {
			violations
					.add(new ConstraintViolation(
							"incrementOrDecrementExpressionExpressionDerivation",
							this));
		}
		if (!this.incrementOrDecrementExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionTypeDerivation", this));
		}
		if (!this.incrementOrDecrementExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionLowerDerivation", this));
		}
		if (!this.incrementOrDecrementExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionUpperDerivation", this));
		}
		if (!this.incrementOrDecrementExpressionOperand()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionOperand", this));
		}
		if (!this.incrementOrDecrementExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"incrementOrDecrementExpressionAssignmentsBefore", this));
		}
		LeftHandSide operand = this.getOperand();
		if (operand != null) {
			operand.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" operator:");
		s.append(this.getOperator());
		s.append(" isPrefix:");
		s.append(this.getIsPrefix());
		if (includeDerived) {
			s.append(" /isFeature:");
			s.append(this.getIsFeature());
		}
		if (includeDerived) {
			s.append(" /isIndexed:");
			s.append(this.getIsIndexed());
		}
		if (includeDerived) {
			s.append(" /isDataValueUpdate:");
			s.append(this.getIsDataValueUpdate());
		}
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		if (includeDerived) {
			AssignedSource assignment = this.getAssignment();
			if (assignment != null) {
				System.out.println(prefix + " /assignment:"
						+ assignment.toString(includeDerived));
			}
		}
		LeftHandSide operand = this.getOperand();
		if (operand != null) {
			System.out.println(prefix + " operand:");
			operand.print(prefix + "  ", includeDerived);
		}
		if (includeDerived) {
			Expression expression = this.getExpression();
			if (expression != null) {
				System.out.println(prefix + " /expression:"
						+ expression.toString(includeDerived));
			}
		}
		if (includeDerived) {
			ElementReference feature = this.getFeature();
			if (feature != null) {
				System.out.println(prefix + " /feature:"
						+ feature.toString(includeDerived));
			}
		}
	}
} // IncrementOrDecrementExpression
