
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An expression used to assign a value to a local name, parameter or property.
 **/

public class AssignmentExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.ExpressionImpl {

	public AssignmentExpressionImpl(AssignmentExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.AssignmentExpression getSelf() {
		return (AssignmentExpression) this.self;
	}

	public AssignedSource deriveAssignment() {
		return null; // STUB
	}

	public ElementReference deriveFeature() {
		return null; // STUB
	}

	public Boolean deriveIsIndexed() {
		return null; // STUB
	}

	public Boolean deriveIsArithmetic() {
		return null; // STUB
	}

	public Boolean deriveIsDefinition() {
		return null; // STUB
	}

	public Boolean deriveIsSimple() {
		return null; // STUB
	}

	public Expression deriveExpression() {
		return null; // STUB
	}

	public Boolean deriveIsFeature() {
		return null; // STUB
	}

	public Boolean deriveIsDataValueUpdate() {
		return null; // STUB
	}

	public Boolean deriveIsCollectionConversion() {
		return null; // STUB
	}

	public Boolean deriveIsBitStringConversion() {
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
	 * multiplicity lower bound is 0. Otherwise, the type and multiplicity are
	 * the same as the left hand side.
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
	 * An assignment expression has the same type as its right-hand side
	 * expression.
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
	 * An assignment expression has the same multiplicity lower bound as its
	 * right-hand side expression.
	 **/
	public boolean assignmentExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * If the left-hand side of a simple assignment is not a new local name, and
	 * the right-hand side is not null, then the left-hand side must either be
	 * untyped or have a type that conforms to the type of the right-hand side
	 * expression.
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
	 * For a compound assignment, both the left-hand side and the right-hand
	 * side must have the same type, consistent with the arithmetic or logical
	 * operator used in the compound assignment operator.
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
	 * The assignments after an assignment expression are the assignments after
	 * the left-hand side, updated by the assignment from the assignment
	 * statement, if any.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // AssignmentExpressionImpl
