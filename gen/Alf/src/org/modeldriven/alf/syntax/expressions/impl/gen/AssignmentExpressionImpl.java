
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl.gen;

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
import java.util.TreeSet;

/**
 * An expression used to assign a value to a local name, parameter or property.
 **/

public class AssignmentExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

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

	public AssignmentExpressionImpl(AssignmentExpression self) {
		super(self);
	}

	public AssignmentExpression getSelf() {
		return (AssignmentExpression) this.self;
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
			this.setAssignment(this.deriveAssignment());
		}
		return this.assignment;
	}

	public void setAssignment(AssignedSource assignment) {
		this.assignment = assignment;
	}

	public ElementReference getFeature() {
		if (this.feature == null) {
			this.setFeature(this.deriveFeature());
		}
		return this.feature;
	}

	public void setFeature(ElementReference feature) {
		this.feature = feature;
	}

	public Boolean getIsIndexed() {
		if (this.isIndexed == null) {
			this.setIsIndexed(this.deriveIsIndexed());
		}
		return this.isIndexed;
	}

	public void setIsIndexed(Boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public Boolean getIsArithmetic() {
		if (this.isArithmetic == null) {
			this.setIsArithmetic(this.deriveIsArithmetic());
		}
		return this.isArithmetic;
	}

	public void setIsArithmetic(Boolean isArithmetic) {
		this.isArithmetic = isArithmetic;
	}

	public Boolean getIsDefinition() {
		if (this.isDefinition == null) {
			this.setIsDefinition(this.deriveIsDefinition());
		}
		return this.isDefinition;
	}

	public void setIsDefinition(Boolean isDefinition) {
		this.isDefinition = isDefinition;
	}

	public Boolean getIsSimple() {
		if (this.isSimple == null) {
			this.setIsSimple(this.deriveIsSimple());
		}
		return this.isSimple;
	}

	public void setIsSimple(Boolean isSimple) {
		this.isSimple = isSimple;
	}

	public Expression getExpression() {
		if (this.expression == null) {
			this.setExpression(this.deriveExpression());
		}
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public Boolean getIsFeature() {
		if (this.isFeature == null) {
			this.setIsFeature(this.deriveIsFeature());
		}
		return this.isFeature;
	}

	public void setIsFeature(Boolean isFeature) {
		this.isFeature = isFeature;
	}

	public Boolean getIsDataValueUpdate() {
		if (this.isDataValueUpdate == null) {
			this.setIsDataValueUpdate(this.deriveIsDataValueUpdate());
		}
		return this.isDataValueUpdate;
	}

	public void setIsDataValueUpdate(Boolean isDataValueUpdate) {
		this.isDataValueUpdate = isDataValueUpdate;
	}

	public Boolean getIsCollectionConversion() {
		if (this.isCollectionConversion == null) {
			this.setIsCollectionConversion(this.deriveIsCollectionConversion());
		}
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(Boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public Boolean getIsBitStringConversion() {
		if (this.isBitStringConversion == null) {
			this.setIsBitStringConversion(this.deriveIsBitStringConversion());
		}
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(Boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

	protected AssignedSource deriveAssignment() {
		return null; // STUB
	}

	protected ElementReference deriveFeature() {
		return null; // STUB
	}

	protected Boolean deriveIsIndexed() {
		return null; // STUB
	}

	protected Boolean deriveIsArithmetic() {
		return null; // STUB
	}

	protected Boolean deriveIsDefinition() {
		return null; // STUB
	}

	protected Boolean deriveIsSimple() {
		return null; // STUB
	}

	protected Expression deriveExpression() {
		return null; // STUB
	}

	protected Boolean deriveIsFeature() {
		return null; // STUB
	}

	protected Boolean deriveIsDataValueUpdate() {
		return null; // STUB
	}

	protected Boolean deriveIsCollectionConversion() {
		return null; // STUB
	}

	protected Boolean deriveIsBitStringConversion() {
		return null; // STUB
	}

	/**
	 * An assignment expression is a simple assignment if the assignment
	 * operator is "=".
	 **/
	public boolean assignmentExpressionIsSimpleDerivation() {
		this.getSelf().getIsSimple();
		return true;
	}

	/**
	 * An assignment expression is an arithmetic assignment if its operator is a
	 * compound assignment operator for an arithmetic operation.
	 **/
	public boolean assignmentExpressionIsArithmeticDerivation() {
		this.getSelf().getIsArithmetic();
		return true;
	}

	/**
	 * An assignment expression is a definition if it is a simple assignment and
	 * its left hand side is a local name for which there is no assignment
	 * before the expression.
	 **/
	public boolean assignmentExpressionIsDefinitionDerivation() {
		this.getSelf().getIsDefinition();
		return true;
	}

	/**
	 * The left hand side of an assignment expression is a feature if it is a
	 * kind of FeatureLeftHandSide.
	 **/
	public boolean assignmentExpressionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

	/**
	 * The left hand side of an assignment expression is indexed if it has an
	 * index.
	 **/
	public boolean assignmentExpressionIsIndexedDerivation() {
		this.getSelf().getIsIndexed();
		return true;
	}

	/**
	 * An assignment expression is a data value update if its left hand side is
	 * an attribute of a data value held in a local name or parameter.
	 **/
	public boolean assignmentExpressionIsDataValueUpdateDerivation() {
		this.getSelf().getIsDataValueUpdate();
		return true;
	}

	/**
	 * The new assigned source for an assignment to a local name is the
	 * assignment expression. If the assignment is a definition, then the type
	 * is given by the right hand side, the multiplicity upper bound is 1 if the
	 * upper bound of the right hand side is 1 and otherwise * and the
	 * multiplicity lower bound is 0. Otherwise, the type is the same as the
	 * left-hand side and the multiplicity is also the same as the left-hand
	 * side, if the left-hand side is not indexed, and is * if it is indexed.
	 **/
	public boolean assignmentExpressionAssignmentDerivation() {
		this.getSelf().getAssignment();
		return true;
	}

	/**
	 * If the left-hand side of an assignment expression is a feature, then the
	 * feature of the assignment is the referent of the left-hand side.
	 **/
	public boolean assignmentExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * For a compound assignment, the effective expression is the left-hand side
	 * treated as a name expression, property access expression or sequence
	 * access expression, as appropriate for evaluation to obtain the original
	 * value to be updated.
	 **/
	public boolean assignmentExpressionExpressionDerivation() {
		this.getSelf().getExpression();
		return true;
	}

	/**
	 * A simple assignment expression has the same type as its right-hand side
	 * expression. A compound assignment expression has the same type as its
	 * left-hand side.
	 **/
	public boolean assignmentExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * An assignment expression has the same multiplicity upper bound as its
	 * right-hand side expression.
	 **/
	public boolean assignmentExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * A simple assignment expression has the same multiplicity lower bound as
	 * its right-hand side expression. A compound assignment expression has the
	 * same multiplicity as its left-hand side.
	 **/
	public boolean assignmentExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * If the left-hand side of a simple assignment is not a new local name, and
	 * the right-hand side is not null, then the left-hand side must either be
	 * untyped or the right-hand side expression must have a type that conforms
	 * to the type of the left-hand side.
	 **/
	public boolean assignmentExpressionSimpleAssignmentTypeConformance() {
		return true;
	}

	/**
	 * If the left-hand side of a simple assignment is not a new local name and
	 * the multiplicity upper bound of the left-hand side is less than or equal
	 * to 1, then the multiplicity upper bound of the right-hand side cannot be
	 * greater than that of the left-hand side.
	 **/
	public boolean assignmentExpressionSimpleAssignmentMultiplicityConformance() {
		return true;
	}

	/**
	 * For a compound assignment, if the operator is an arithmetic operator,
	 * then either the left-hand side and the right-hand side both have type
	 * Integer or they both have type String and the operator is +. If the
	 * operator is a logical operator, then either the left-hand side and the
	 * right-hand side both have type Boolean or Bit String or the left-hand
	 * side has type Bit String and the right-hand side has type Integer. If the
	 * operator is a shift operator, then the left-hand side must have type Bit
	 * String and the right-hand side must have type Integer.
	 **/
	public boolean assignmentExpressionCompoundAssignmentTypeConformance() {
		return true;
	}

	/**
	 * For a compound assignment, both the left-hand and right-hand sides must
	 * have a multiplicity upper bound of 1.
	 **/
	public boolean assignmentExpressionCompoundAssignmentMultiplicityConformance() {
		return true;
	}

	/**
	 * The assigned source of a name before the right-hand side expression of an
	 * assignment expression is the same as the assigned source before the
	 * assignment expression. The assigned source of a name before the left-hand
	 * side is the assigned source after the right-hand side expression.
	 **/
	public boolean assignmentExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * An assignment requires collection conversion if the type of the
	 * right-hand side is a collection class and its multiplicity upper bound is
	 * 1, and the type of the left-hand side is not a collection class.
	 **/
	public boolean assignmentExpressionIsCollectionConversionDerivation() {
		this.getSelf().getIsCollectionConversion();
		return true;
	}

	/**
	 * An assignment requires BitString conversion if the type of the left-hand
	 * side is BitString and either the type of the right-hand side is Integer
	 * or collection conversion is required and the type of the right-hand side
	 * is a collection class whose argument type is Integer.
	 **/
	public boolean assignmentExpressionIsBitStringConversionDerivation() {
		this.getSelf().getIsBitStringConversion();
		return true;
	}

	/**
	 * If an assignment expression has a feature with a primary expression whose
	 * type is a data type, then the assignment expression must be a data value
	 * update.
	 **/
	public boolean assignmentExpressionDataValueUpdateLegality() {
		return true;
	}

	/**
	 * The assignments after an assignment expression are the assignments after
	 * the left-hand side, updated by the assignment from the assignment
	 * statement, if any.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // AssignmentExpressionImpl
