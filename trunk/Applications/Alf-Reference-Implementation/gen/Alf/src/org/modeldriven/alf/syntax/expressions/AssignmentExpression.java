
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

import org.modeldriven.alf.syntax.expressions.impl.AssignmentExpressionImpl;

/**
 * An expression used to assign a value to a local name, parameter or property.
 **/

public class AssignmentExpression extends Expression {

	private String operator = "";
	private LeftHandSide leftHandSide = null;
	private Expression rightHandSide = null;
	private AssignedSource assignment = null; // DERIVED
	private ElementReference feature = null; // DERIVED
	private Boolean isIndexed = null; // DERIVED
	private Boolean isArithmetic = null; // DERIVED
	private Boolean isDefinition = null; // DERIVED
	private Boolean isSimple = null; // DERIVED
	private Expression expression = null; // DERIVED
	private Boolean isFeature = null; // DERIVED
	private Boolean isDataValueUpdate = null; // DERIVED
	private Boolean isCollectionConversion = null; // DERIVED
	private Boolean isBitStringConversion = null; // DERIVED

	public AssignmentExpression() {
		this.impl = new AssignmentExpressionImpl(this);
	}

	public AssignmentExpressionImpl getImpl() {
		return (AssignmentExpressionImpl) this.impl;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public LeftHandSide getLeftHandSide() {
		return this.leftHandSide;
	}

	public void setLeftHandSide(LeftHandSide leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	public Expression getRightHandSide() {
		return this.rightHandSide;
	}

	public void setRightHandSide(Expression rightHandSide) {
		this.rightHandSide = rightHandSide;
	}

	public AssignedSource getAssignment() {
		if (this.assignment == null) {
			this.setAssignment(this.getImpl().deriveAssignment());
		}
		return this.assignment;
	}

	public void setAssignment(AssignedSource assignment) {
		this.assignment = assignment;
	}

	public ElementReference getFeature() {
		if (this.feature == null) {
			this.setFeature(this.getImpl().deriveFeature());
		}
		return this.feature;
	}

	public void setFeature(ElementReference feature) {
		this.feature = feature;
	}

	public Boolean getIsIndexed() {
		if (this.isIndexed == null) {
			this.setIsIndexed(this.getImpl().deriveIsIndexed());
		}
		return this.isIndexed;
	}

	public void setIsIndexed(Boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public Boolean getIsArithmetic() {
		if (this.isArithmetic == null) {
			this.setIsArithmetic(this.getImpl().deriveIsArithmetic());
		}
		return this.isArithmetic;
	}

	public void setIsArithmetic(Boolean isArithmetic) {
		this.isArithmetic = isArithmetic;
	}

	public Boolean getIsDefinition() {
		if (this.isDefinition == null) {
			this.setIsDefinition(this.getImpl().deriveIsDefinition());
		}
		return this.isDefinition;
	}

	public void setIsDefinition(Boolean isDefinition) {
		this.isDefinition = isDefinition;
	}

	public Boolean getIsSimple() {
		if (this.isSimple == null) {
			this.setIsSimple(this.getImpl().deriveIsSimple());
		}
		return this.isSimple;
	}

	public void setIsSimple(Boolean isSimple) {
		this.isSimple = isSimple;
	}

	public Expression getExpression() {
		if (this.expression == null) {
			this.setExpression(this.getImpl().deriveExpression());
		}
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Boolean getIsFeature() {
		if (this.isFeature == null) {
			this.setIsFeature(this.getImpl().deriveIsFeature());
		}
		return this.isFeature;
	}

	public void setIsFeature(Boolean isFeature) {
		this.isFeature = isFeature;
	}

	public Boolean getIsDataValueUpdate() {
		if (this.isDataValueUpdate == null) {
			this.setIsDataValueUpdate(this.getImpl().deriveIsDataValueUpdate());
		}
		return this.isDataValueUpdate;
	}

	public void setIsDataValueUpdate(Boolean isDataValueUpdate) {
		this.isDataValueUpdate = isDataValueUpdate;
	}

	public Boolean getIsCollectionConversion() {
		if (this.isCollectionConversion == null) {
			this.setIsCollectionConversion(this.getImpl()
					.deriveIsCollectionConversion());
		}
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.setIsBitStringConversion(this.getImpl()
					.deriveIsBitStringConversion());
		}
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
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
	public ArrayList<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
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
