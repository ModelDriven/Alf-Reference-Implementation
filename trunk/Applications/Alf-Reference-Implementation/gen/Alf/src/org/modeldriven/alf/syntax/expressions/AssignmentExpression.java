
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.AssignmentExpressionImpl;

/**
 * An expression used to assign a value to a local name, parameter or property.
 **/

public class AssignmentExpression extends Expression {

	public AssignmentExpression() {
		this.impl = new AssignmentExpressionImpl(this);
	}

	public AssignmentExpressionImpl getImpl() {
		return (AssignmentExpressionImpl) this.impl;
	}

	public String getOperator() {
		return this.getImpl().getOperator();
	}

	public void setOperator(String operator) {
		this.getImpl().setOperator(operator);
	}

	public LeftHandSide getLeftHandSide() {
		return this.getImpl().getLeftHandSide();
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.getImpl().setLeftHandSide(leftHandSide);
	}

	public Expression getRightHandSide() {
		return this.getImpl().getRightHandSide();
	}

	public void setRightHandSide(Expression rightHandSide) {
		this.getImpl().setRightHandSide(rightHandSide);
	}

	public AssignedSource getAssignment() {
		return this.getImpl().getAssignment();
	}

	public void setAssignment(AssignedSource assignment) {
		this.getImpl().setAssignment(assignment);
	}

	public ElementReference getFeature() {
		return this.getImpl().getFeature();
	}

	public void setFeature(ElementReference feature) {
		this.getImpl().setFeature(feature);
	}

	public Boolean getIsIndexed() {
		return this.getImpl().getIsIndexed();
	}

	public void setIsIndexed(Boolean isIndexed) {
		this.getImpl().setIsIndexed(isIndexed);
	}

	public Boolean getIsArithmetic() {
		return this.getImpl().getIsArithmetic();
	}

	public void setIsArithmetic(Boolean isArithmetic) {
		this.getImpl().setIsArithmetic(isArithmetic);
	}

	public Boolean getIsDefinition() {
		return this.getImpl().getIsDefinition();
	}

	public void setIsDefinition(Boolean isDefinition) {
		this.getImpl().setIsDefinition(isDefinition);
	}

	public Boolean getIsSimple() {
		return this.getImpl().getIsSimple();
	}

	public void setIsSimple(Boolean isSimple) {
		this.getImpl().setIsSimple(isSimple);
	}

	public Expression getExpression() {
		return this.getImpl().getExpression();
	}

	public void setExpression(Expression expression) {
		this.getImpl().setExpression(expression);
	}

	public Boolean getIsFeature() {
		return this.getImpl().getIsFeature();
	}

	public void setIsFeature(Boolean isFeature) {
		this.getImpl().setIsFeature(isFeature);
	}

	public Boolean getIsDataValueUpdate() {
		return this.getImpl().getIsDataValueUpdate();
	}

	public void setIsDataValueUpdate(Boolean isDataValueUpdate) {
		this.getImpl().setIsDataValueUpdate(isDataValueUpdate);
	}

	public Boolean getIsCollectionConversion() {
		return this.getImpl().getIsCollectionConversion();
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.getImpl().setIsCollectionConversion(isCollectionConversion);
	}

	public Boolean getIsBitStringConversion() {
		return this.getImpl().getIsBitStringConversion();
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.getImpl().setIsBitStringConversion(isBitStringConversion);
	}

	/**
	 * An assignment expression is a simple assignment if the assignment
	 * operator is "=".
	 **/
	public boolean assignmentExpressionIsSimpleDerivation() {
		return this.getImpl().assignmentExpressionIsSimpleDerivation();
	}

	/**
	 * An assignment expression is an arithmetic assignment if its operator is a
	 * compound assignment operator for an arithmetic operation.
	 **/
	public boolean assignmentExpressionIsArithmeticDerivation() {
		return this.getImpl().assignmentExpressionIsArithmeticDerivation();
	}

	/**
	 * An assignment expression is a definition if it is a simple assignment and
	 * its left hand side is a local name for which there is no assignment
	 * before the expression.
	 **/
	public boolean assignmentExpressionIsDefinitionDerivation() {
		return this.getImpl().assignmentExpressionIsDefinitionDerivation();
	}

	/**
	 * The left hand side of an assignment expression is a feature if it is a
	 * kind of FeatureLeftHandSide.
	 **/
	public boolean assignmentExpressionIsFeatureDerivation() {
		return this.getImpl().assignmentExpressionIsFeatureDerivation();
	}

	/**
	 * The left hand side of an assignment expression is indexed if it has an
	 * index.
	 **/
	public boolean assignmentExpressionIsIndexedDerivation() {
		return this.getImpl().assignmentExpressionIsIndexedDerivation();
	}

	/**
	 * An assignment expression is a data value update if its left hand side is
	 * an attribute of a data value held in a local name or parameter.
	 **/
	public boolean assignmentExpressionIsDataValueUpdateDerivation() {
		return this.getImpl().assignmentExpressionIsDataValueUpdateDerivation();
	}

	/**
	 * The new assigned source for an assignment to a local name is the
	 * assignment expression. If the assignment is a definition, then the type
	 * is given by the right hand side, the multiplicity upper bound is 1 if the
	 * upper bound of the right hand side is 1 and otherwise * and the
	 * multiplicity lower bound is 0. Otherwise, the type and multiplicity are
	 * the same as the left hand side.
	 **/
	public boolean assignmentExpressionAssignmentDerivation() {
		return this.getImpl().assignmentExpressionAssignmentDerivation();
	}

	/**
	 * If the left-hand side of an assignment expression is a feature, then the
	 * feature of the assignment is the referent of the left-hand side.
	 **/
	public boolean assignmentExpressionFeatureDerivation() {
		return this.getImpl().assignmentExpressionFeatureDerivation();
	}

	/**
	 * For a compound assignment, the effective expression is the left-hand side
	 * treated as a name expression, property access expression or sequence
	 * access expression, as appropriate for evaluation to obtain the original
	 * value to be updated.
	 **/
	public boolean assignmentExpressionExpressionDerivation() {
		return this.getImpl().assignmentExpressionExpressionDerivation();
	}

	/**
	 * An assignment expression has the same type as its right-hand side
	 * expression.
	 **/
	public boolean assignmentExpressionTypeDerivation() {
		return this.getImpl().assignmentExpressionTypeDerivation();
	}

	/**
	 * An assignment expression has the same multiplicity upper bound as its
	 * right-hand side expression.
	 **/
	public boolean assignmentExpressionUpperDerivation() {
		return this.getImpl().assignmentExpressionUpperDerivation();
	}

	/**
	 * An assignment expression has the same multiplicity lower bound as its
	 * right-hand side expression.
	 **/
	public boolean assignmentExpressionLowerDerivation() {
		return this.getImpl().assignmentExpressionLowerDerivation();
	}

	/**
	 * If the left-hand side of a simple assignment is not a new local name, and
	 * the right-hand side is not null, then the left-hand side must either be
	 * untyped or have a type that conforms to the type of the right-hand side
	 * expression.
	 **/
	public boolean assignmentExpressionSimpleAssignmentTypeConformance() {
		return this.getImpl()
				.assignmentExpressionSimpleAssignmentTypeConformance();
	}

	/**
	 * If the left-hand side of a simple assignment is not a new local name and
	 * the multiplicity upper bound of the left-hand side is less than or equal
	 * to 1, then the multiplicity upper bound of the right-hand side cannot be
	 * greater than that of the left-hand side.
	 **/
	public boolean assignmentExpressionSimpleAssignmentMultiplicityConformance() {
		return this.getImpl()
				.assignmentExpressionSimpleAssignmentMultiplicityConformance();
	}

	/**
	 * For a compound assignment, both the left-hand side and the right-hand
	 * side must have the same type, consistent with the arithmetic or logical
	 * operator used in the compound assignment operator.
	 **/
	public boolean assignmentExpressionCompoundAssignmentTypeConformance() {
		return this.getImpl()
				.assignmentExpressionCompoundAssignmentTypeConformance();
	}

	/**
	 * For a compound assignment, both the left-hand and right-hand sides must
	 * have a multiplicity upper bound of 1.
	 **/
	public boolean assignmentExpressionCompoundAssignmentMultiplicityConformance() {
		return this
				.getImpl()
				.assignmentExpressionCompoundAssignmentMultiplicityConformance();
	}

	/**
	 * The assigned source of a name before the right-hand side expression of an
	 * assignment expression is the same as the assigned source before the
	 * assignment expression. The assigned source of a name before the left-hand
	 * side is the assigned source after the right-hand side expression.
	 **/
	public boolean assignmentExpressionAssignmentsBefore() {
		return this.getImpl().assignmentExpressionAssignmentsBefore();
	}

	/**
	 * An assignment requires collection conversion if the type of the
	 * right-hand side is a collection class and its multiplicity upper bound is
	 * 1, and the type of the left-hand side is not a collection class.
	 **/
	public boolean assignmentExpressionIsCollectionConversionDerivation() {
		return this.getImpl()
				.assignmentExpressionIsCollectionConversionDerivation();
	}

	/**
	 * An assignment requires BitString conversion if the type of the left-hand
	 * side is BitString and either the type of the right-hand side is Integer
	 * or collection conversion is required and the type of the right-hand side
	 * is a collection class whose argument type is Integer.
	 **/
	public boolean assignmentExpressionIsBitStringConversionDerivation() {
		return this.getImpl()
				.assignmentExpressionIsBitStringConversionDerivation();
	}

	/**
	 * The assignments after an assignment expression are the assignments after
	 * the left-hand side, updated by the assignment from the assignment
	 * statement, if any.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.assignmentExpressionIsSimpleDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsSimpleDerivation", this));
		}
		if (!this.assignmentExpressionIsArithmeticDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsArithmeticDerivation", this));
		}
		if (!this.assignmentExpressionIsDefinitionDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsDefinitionDerivation", this));
		}
		if (!this.assignmentExpressionIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsFeatureDerivation", this));
		}
		if (!this.assignmentExpressionIsIndexedDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsIndexedDerivation", this));
		}
		if (!this.assignmentExpressionIsDataValueUpdateDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsDataValueUpdateDerivation", this));
		}
		if (!this.assignmentExpressionAssignmentDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionAssignmentDerivation", this));
		}
		if (!this.assignmentExpressionFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionFeatureDerivation", this));
		}
		if (!this.assignmentExpressionExpressionDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionExpressionDerivation", this));
		}
		if (!this.assignmentExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionTypeDerivation", this));
		}
		if (!this.assignmentExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionUpperDerivation", this));
		}
		if (!this.assignmentExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionLowerDerivation", this));
		}
		if (!this.assignmentExpressionSimpleAssignmentTypeConformance()) {
			violations
					.add(new ConstraintViolation(
							"assignmentExpressionSimpleAssignmentTypeConformance",
							this));
		}
		if (!this.assignmentExpressionSimpleAssignmentMultiplicityConformance()) {
			violations
					.add(new ConstraintViolation(
							"assignmentExpressionSimpleAssignmentMultiplicityConformance",
							this));
		}
		if (!this.assignmentExpressionCompoundAssignmentTypeConformance()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionCompoundAssignmentTypeConformance",
					this));
		}
		if (!this
				.assignmentExpressionCompoundAssignmentMultiplicityConformance()) {
			violations
					.add(new ConstraintViolation(
							"assignmentExpressionCompoundAssignmentMultiplicityConformance",
							this));
		}
		if (!this.assignmentExpressionAssignmentsBefore()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionAssignmentsBefore", this));
		}
		if (!this.assignmentExpressionIsCollectionConversionDerivation()) {
			violations.add(new ConstraintViolation(
					"assignmentExpressionIsCollectionConversionDerivation",
					this));
		}
		if (!this.assignmentExpressionIsBitStringConversionDerivation()) {
			violations
					.add(new ConstraintViolation(
							"assignmentExpressionIsBitStringConversionDerivation",
							this));
		}
		LeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			leftHandSide.checkConstraints(violations);
		}
		Expression rightHandSide = this.getRightHandSide();
		if (rightHandSide != null) {
			rightHandSide.checkConstraints(violations);
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		s.append(" operator:");
		s.append(this.getOperator());
		Boolean isIndexed = this.getIsIndexed();
		if (isIndexed != null) {
			s.append(" /isIndexed:");
			s.append(isIndexed);
		}
		Boolean isArithmetic = this.getIsArithmetic();
		if (isArithmetic != null) {
			s.append(" /isArithmetic:");
			s.append(isArithmetic);
		}
		Boolean isDefinition = this.getIsDefinition();
		if (isDefinition != null) {
			s.append(" /isDefinition:");
			s.append(isDefinition);
		}
		Boolean isSimple = this.getIsSimple();
		if (isSimple != null) {
			s.append(" /isSimple:");
			s.append(isSimple);
		}
		Boolean isFeature = this.getIsFeature();
		if (isFeature != null) {
			s.append(" /isFeature:");
			s.append(isFeature);
		}
		Boolean isDataValueUpdate = this.getIsDataValueUpdate();
		if (isDataValueUpdate != null) {
			s.append(" /isDataValueUpdate:");
			s.append(isDataValueUpdate);
		}
		Boolean isCollectionConversion = this.getIsCollectionConversion();
		if (isCollectionConversion != null) {
			s.append(" /isCollectionConversion:");
			s.append(isCollectionConversion);
		}
		Boolean isBitStringConversion = this.getIsBitStringConversion();
		if (isBitStringConversion != null) {
			s.append(" /isBitStringConversion:");
			s.append(isBitStringConversion);
		}
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		LeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			System.out.println(prefix + " leftHandSide:");
			leftHandSide.print(prefix + "  ");
		}
		Expression rightHandSide = this.getRightHandSide();
		if (rightHandSide != null) {
			System.out.println(prefix + " rightHandSide:");
			rightHandSide.print(prefix + "  ");
		}
		AssignedSource assignment = this.getAssignment();
		if (assignment != null) {
			System.out.println(prefix + " /assignment:" + assignment);
		}
		ElementReference feature = this.getFeature();
		if (feature != null) {
			System.out.println(prefix + " /feature:" + feature);
		}
		Expression expression = this.getExpression();
		if (expression != null) {
			System.out.println(prefix + " /expression:" + expression);
		}
	}
} // AssignmentExpression
