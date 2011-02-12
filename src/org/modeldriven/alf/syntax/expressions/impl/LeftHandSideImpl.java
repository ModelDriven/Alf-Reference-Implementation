
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
 * The left-hand side of an assignment expression.
 * 
 * NOTE: The derivations for the derived properties of LeftHandSide are specific
 * to its various subclasses.
 **/

public abstract class LeftHandSideImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public LeftHandSideImpl(LeftHandSide self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.expressions.LeftHandSide getSelf() {
		return (LeftHandSide) this.self;
	}

	public ArrayList<AssignedSource> deriveAssignmentBefore() {
		return null; // STUB
	}

	public ArrayList<AssignedSource> deriveAssignmentAfter() {
		return null; // STUB
	}

	/**
	 * If a left-hand side has an index, then the index expression must have a
	 * multiplicity upper bound no greater than 1.
	 **/
	public boolean leftHandSideIndexExpression() {
		return true;
	}

} // LeftHandSideImpl
