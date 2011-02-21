
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
 * An expression that uses the value of one operand expression to condition the
 * evaluation of one of two other operand expressions.
 **/

public class ConditionalTestExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public ConditionalTestExpressionImpl(ConditionalTestExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.ConditionalTestExpression getSelf() {
		return (ConditionalTestExpression) this.self;
	}

	/**
	 * The type of a conditional-test operator expression is the effective
	 * common ancestor (if one exists) of the types of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * The multiplicity lower bound of a conditional-test operator expression is
	 * the minimum of the multiplicity lower bounds of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The multiplicity upper bound of a conditional-test operator expression is
	 * the maximum of the multiplicity upper bounds of its second and third
	 * operand expressions.
	 **/
	public boolean conditionalTestExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * The first operand expression of a conditional-test expression must be of
	 * type Boolean and have a multiplicity upper bound of 1.
	 **/
	public boolean conditionalTestExpressionCondition() {
		return true;
	}

	/**
	 * The assignments before the first operand expression of a conditional-test
	 * expression are the same as those before the conditional-test expression.
	 * The assignments before the second and third operand expressions are the
	 * same as those after the first operand expression.
	 **/
	public boolean conditionalTestExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * If a name is unassigned after the first operand expression and has an
	 * assigned source after one of the other operand expression, then it must
	 * have an assigned source after both of those expressions.
	 **/
	public boolean conditionalTestExpressionAssignmentsAfter() {
		return true;
	}

	/**
	 * Returns unchanged all assignments for local names that are not reassigned
	 * in either the second or third operand expressions. Any local names that
	 * have different assignments after the second and third operand expressions
	 * are adjusted to have the conditional-test expression as their assigned
	 * source.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // ConditionalTestExpressionImpl
