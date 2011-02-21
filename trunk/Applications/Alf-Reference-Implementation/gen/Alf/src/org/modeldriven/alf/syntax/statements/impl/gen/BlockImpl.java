
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * A grouped sequence of statements.
 **/

public class BlockImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	public BlockImpl(Block self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.Block getSelf() {
		return (Block) this.self;
	}

	public ArrayList<AssignedSource> deriveAssignmentAfter() {
		return null; // STUB
	}

	public ArrayList<AssignedSource> deriveAssignmentBefore() {
		return null; // STUB
	}

	/**
	 * The assignments before each statement in a block other than the first are
	 * the same as the assignments after the previous statement.
	 **/
	public boolean blockAssignmentsBeforeStatements() {
		return true;
	}

	public boolean blockAssignmentsBefore() {
		return true;
	}

	/**
	 * If a block is not empty, then the assignments after the block are the
	 * same as the assignments after the last statement of the block. Otherwise
	 * they are the same as the assignments before the block.
	 **/
	public boolean blockAssignmentAfterDerivation() {
		this.getSelf().getAssignmentAfter();
		return true;
	}

} // BlockImpl
