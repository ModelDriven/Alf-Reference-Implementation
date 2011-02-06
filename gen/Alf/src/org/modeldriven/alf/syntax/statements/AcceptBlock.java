
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
 * A block of an accept statement that accepts one or more signals.
 **/

public class AcceptBlock extends SyntaxElement implements IAcceptBlock {

	private String name = "";
	private IBlock block = null;
	private IQualifiedNameList signalNames = null;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IBlock getBlock() {
		return this.block;
	}

	public void setBlock(IBlock block) {
		this.block = block;
	}

	public IQualifiedNameList getSignalNames() {
		return this.signalNames;
	}

	public void setSignalNames(IQualifiedNameList signalNames) {
		this.signalNames = signalNames;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.getName());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IBlock block = this.getBlock();
		if (block != null) {
			block.print(prefix + " ");
		}
		IQualifiedNameList signalNames = this.getSignalNames();
		if (signalNames != null) {
			signalNames.print(prefix + " ");
		}
	}
} // AcceptBlock
