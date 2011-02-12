
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
 * An expression used to filter values by type.
 **/

public class CastExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.ExpressionImpl {

	public CastExpressionImpl(CastExpression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.CastExpression getSelf() {
		return (CastExpression) this.self;
	}

	/**
	 * The type of a cast expression is the referent of the given type name (if
	 * there is one).
	 **/
	public boolean castExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	/**
	 * A cast expression has a multiplicity lower bound of 0.
	 **/
	public boolean castExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * A cast expression has a multiplicity upper bound that is the same as the
	 * upper bound of its operand expression.
	 **/
	public boolean castExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/**
	 * If the cast expression has a type name, then it must resolve to a
	 * classifier.
	 **/
	public boolean castExpressionTypeResolution() {
		return true;
	}

	/**
	 * The assignments before the operand of a cast expression are the same as
	 * those before the cast expression.
	 **/
	public boolean castExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * The assignments after a cast expression are the same as those after its
	 * operand expression.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // CastExpressionImpl
