
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * A unary expression with either an increment or decrement operator.
 **/

public class IncrementOrDecrementExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public IncrementOrDecrementExpressionImpl(
			IncrementOrDecrementExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.IncrementOrDecrementExpression getSelf() {
		return (IncrementOrDecrementExpression) this.self;
	}

	public AssignedSource deriveAssignment() {
		return null; // STUB
	}

	public Expression deriveExpression() {
		return null; // STUB
	}

	public ElementReference deriveFeature() {
		return null; // STUB
	}

	public Boolean deriveIsFeature() {
		return null; // STUB
	}

	public Boolean deriveIsIndexed() {
		return null; // STUB
	}

	public Boolean deriveIsDataValueUpdate() {
		return null; // STUB
	}

	/**
	 * If the operand of an increment or decrement expression is a name, then
	 * the assignment for the expression is a new assigned source for the name
	 * with the expression as the source.
	 **/
	public boolean incrementOrDecrementExpressionAssignment() {
		return true;
	}

	/**
	 * An increment or decrement expression has a feature as its operand if the
	 * operand is a kind of FeatureLeftHandSide.
	 **/
	public boolean incrementOrDecrementExpressionIsFeatureDerivation() {
		this.getSelf().getIsFeature();
		return true;
	}

	/**
	 * An increment or decrement expression is indexed if its operand is
	 * indexed.
	 **/
	public boolean incrementOrDecrementExpressionIsIndexedDerivation() {
		this.getSelf().getIsIndexed();
		return true;
	}

	/**
	 * An increment or decrement expression is a data value update if its
	 * operand is an attribute of a data value held in a local name or
	 * parameter.
	 **/
	public boolean incrementOrDecrementExpressionIsDataValueUpdate() {
		return true;
	}

	/**
	 * If the operand of an increment or decrement expression is a feature, then
	 * the referent for the operand.
	 **/
	public boolean incrementOrDecrementExpressionFeature() {
		return true;
	}

	/**
	 * The effective expression for the operand of an increment or decrement
	 * expression is the operand treated as a name expression, property access
	 * expression or sequence access expression, as appropriate for evaluation
	 * to obtain the original value to be updated.
	 **/
	public boolean incrementOrDecrementExpressionExpressionDerivation() {
		this.getSelf().getExpression();
		return true;
	}

	/**
	 * An increment or decrement expression has type Integer.
	 **/
	public boolean incrementOrDecrementExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * An increment or decrement expression has the same multiplicity lower
	 * bound as its operand expression.
	 **/
	public boolean incrementOrDecrementExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * An increment or decrement expression has a multiplicity upper bound of 1.
	 **/
	public boolean incrementOrDecrementExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The operand expression must have type Integer and a multiplicity upper
	 * bound of 1.
	 **/
	public boolean incrementOrDecrementExpressionOperand() {
		return true;
	}

	/**
	 * The assignments before the operand of an increment or decrement
	 * expression are the same as those before the increment or decrement
	 * expression.
	 **/
	public boolean incrementOrDecrementExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after an increment and decrement expression include all
	 * those after its operand expression. Further, if the operand expression,
	 * considered as a left hand side, is a local name, then this is reassigned.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // IncrementOrDecrementExpressionImpl
