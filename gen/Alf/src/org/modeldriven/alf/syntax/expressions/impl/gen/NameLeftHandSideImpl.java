
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
 * A left-hand side that is a name.
 **/

public class NameLeftHandSideImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.LeftHandSideImpl {

	public NameLeftHandSideImpl(NameLeftHandSide self) {
		super(self);
	}

	public NameLeftHandSide getSelf() {
		return (NameLeftHandSide) this.self;
	}

	/**
	 * The assignments after a name left-hand side are the same as the
	 * assignments before.
	 **/
	public boolean nameLeftHandSideAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}

	/**
	 * The target of a name left hand side may not already have an assigned
	 * source that is a loop variable definition, an annotation, a sequence
	 * expansion expression or a parameter that is an in parameter.
	 **/
	public boolean nameLeftHandSideTargetAssignment() {
		return true;
	}

	/**
	 * If a name left-hand side has an index, then the target name must already
	 * have an assigned source and the assignments before the index expression
	 * are the assignments before the left-hand side.
	 **/
	public boolean nameLeftHandSideAssignmentsBefore() {
		return true;
	}

} // NameLeftHandSideImpl
