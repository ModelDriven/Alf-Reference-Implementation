
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
 * A model of the common properties derived for any Alf expression.
 * 
 * NOTE: The derivations for all properties of Expression except
 * AssignmentsAfter are specific to its various subclasses.
 **/

public abstract class ExpressionImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	public ExpressionImpl(Expression self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.Expression getSelf() {
		return (Expression) this.self;
	}

	public ArrayList<AssignedSource> deriveAssignmentBefore() {
		return null; // STUB
	}

	public ArrayList<AssignedSource> deriveAssignmentAfter() {
		return null; // STUB
	}

	public Integer deriveUpper() {
		return null; // STUB
	}

	public Integer deriveLower() {
		return null; // STUB
	}

	public ElementReference deriveType() {
		return null; // STUB
	}

	/**
	 * The assignments after an expression are given by the result of the
	 * updateAssignments helper operation.
	 **/
	public boolean expressionAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}

	/**
	 * No name may be assigned more than once before or after an expression.
	 **/
	public boolean expressionUniqueAssignments() {
		return true;
	}

	/**
	 * Returns the assignments from before this expression updated for any
	 * assignments made in the expression. By default, this is the same set as
	 * the assignments before the expression. This operation is redefined only
	 * in subclasses of Expression for kinds of expressions that make
	 * assignments.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // ExpressionImpl
