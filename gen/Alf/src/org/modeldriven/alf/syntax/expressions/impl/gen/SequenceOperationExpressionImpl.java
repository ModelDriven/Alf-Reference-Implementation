
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
 * An expression used to invoke a behavior as if it was an operation on a target
 * sequence as a whole.
 **/

public class SequenceOperationExpressionImpl
		extends
		org.modeldriven.alf.syntax.expressions.impl.gen.InvocationExpressionImpl {

	public SequenceOperationExpressionImpl(SequenceOperationExpression self) {
		super(self);
	}

	public SequenceOperationExpression getSelf() {
		return (SequenceOperationExpression) this.self;
	}

	public Boolean deriveIsCollectionConversion() {
		return null; // STUB
	}

	public Boolean deriveIsBitStringConversion() {
		return null; // STUB
	}

	/**
	 * The referent for a sequence operation expression is the behavior named by
	 * the operation for the expression.
	 **/
	public boolean sequenceOperationExpressionReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * There is no feature for a sequence operation expression.
	 **/
	public boolean sequenceOperationExpressionFeatureDerivation() {
		this.getSelf().getFeature();
		return true;
	}

	/**
	 * There must be a single behavior that is a resolution of the operation
	 * qualified name of a sequence operation expression with a least one
	 * parameter, whose first parameter has direction in or inout, has
	 * multiplicity [0..*] and to which the target primary expression is
	 * assignable.
	 **/
	public boolean sequenceOperationExpressionOperationReferent() {
		return true;
	}

	/**
	 * If the first parameter of the referent has direction inout, then the
	 * parameter type must have the same type as the primary expression.
	 **/
	public boolean sequenceOperationExpressionTargetCompatibility() {
		return true;
	}

	/**
	 * The type of an input argument expression of a sequence operation
	 * parameter must be assignable to its corresponding parameter. The type of
	 * an output parameter must be assignable to its corresponding argument
	 * expression. (Note that this implies that the type of an argument
	 * expression for an inout parameter must be the same as the type of that
	 * parameter.)
	 **/
	public boolean sequenceOperationExpressionArgumentCompatibility() {
		return true;
	}

	/**
	 * The assignments before the primary expression of a sequence operation
	 * expression are the same as the assignments before the sequence operation
	 * expression.
	 **/
	public boolean sequenceOperationExpressionAssignmentsBefore() {
		return true;
	}

	/**
	 * Collection conversion is required if the type of the primary expression
	 * of a sequence operation expression is a collection class.
	 **/
	public boolean sequenceOperationExpressionIsCollectionConversionDerivation() {
		this.getSelf().getIsCollectionConversion();
		return true;
	}

	/**
	 * BitString conversion is required if type of the first parameter of the
	 * referent of a sequence operation expression is BitString and either the
	 * type of its primary expression is Integer or collection conversion is
	 * required and the type of its primary expression is a collection class
	 * whose argument type is Integer.
	 **/
	public boolean sequenceOperationExpressionIsBitStringConversionDerivation() {
		this.getSelf().getIsBitStringConversion();
		return true;
	}

	/**
	 * The assignments after a sequence operation expression include those made
	 * in the primary expression and those made in the tuple and, for an
	 * "in place" operation (one whose first parameter is inout), that made by
	 * the sequence operation expression itself.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // SequenceOperationExpressionImpl
