
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A statement that executes a block.
 **/

public class BlockStatement extends Statement {

	private Block block = null;
	private boolean isParallel = false; // DERIVED

	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public boolean getIsParallel() {
		return this.isParallel;
	}

	public void setIsParallel(boolean isParallel) {
		this.isParallel = isParallel;
	}

	public boolean annotationAllowed(Annotation annotation) {
		/*
		 * In addition to an @isolated annotation, a block statement may have a
		 * @parallel annotation. It may not have any arguments.
		 */
		return false; // STUB
	} // annotationAllowed

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.block != null) {
			this.block.print(prefix + " ");
		}
	}
} // BlockStatement
