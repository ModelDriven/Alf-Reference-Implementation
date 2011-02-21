
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
 * An expression used to reduce a sequence of values effectively by inserting a
 * binary operation between the values.
 **/

public class SequenceReductionExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	public SequenceReductionExpressionImpl(SequenceReductionExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.SequenceReductionExpression getSelf() {
		return (SequenceReductionExpression) this.self;
	}

	public ElementReference deriveReferent() {
		return null; // STUB
	}

	/**
	 * The referent for a sequence reduction expression is the behavior denoted
	 * by the behavior name of the expression.
	 **/
	public boolean sequenceReductionExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * A sequence reduction expression has the same type as its primary
	 * expression.
	 **/
	public boolean sequenceReductionExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A sequence reduction expression has a multiplicity upper bound of 1.
	 **/
	public boolean sequenceReductionExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * A sequence reduction expression has a multiplicity lower bound of 1.
	 **/
	public boolean sequenceReductionExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The behavior name in a sequence reduction expression must denote a
	 * behavior.
	 **/
	public boolean sequenceReductionExpressionBehavior() {
		return true;
	}

	/**
	 * The referent behavior must have two in parameters, a return parameter and
	 * no other parameters. The parameters must all have the same type as the
	 * argument expression and multiplicity [1..1].
	 **/
	public boolean sequenceReductionExpressionBehaviorParameters() {
		return true;
	}

	/**
	 * The assignments before the target expression of a sequence reduction
	 * expression are the same as the assignments before the sequence reduction
	 * expression.
	 **/
	public boolean sequenceReductionExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after a sequence reduction expression are the same as
	 * after its primary expression.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // SequenceReductionExpressionImpl
