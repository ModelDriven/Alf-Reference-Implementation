
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class BlockStatement extends Statement implements IBlockStatement {

	private IBlock block = null;

	public IBlock getBlock() {
		return this.block;
	}

	public void setBlock(IBlock block) {
		this.block = block;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IBlock block = this.getBlock();
		if (block != null) {
			block.print(prefix + " ");
		}
	}
} // BlockStatement
