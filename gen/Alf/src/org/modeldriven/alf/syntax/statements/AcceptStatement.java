
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
 * A statement used to accept the receipt of instances of one or more signals.
 **/

public class AcceptStatement extends Statement {

	private ArrayList<AcceptBlock> acceptBlock = new ArrayList<AcceptBlock>();
	private ElementReference behavior = null; // DERIVED
	private boolean isSimple = false; // DERIVED

	public ArrayList<AcceptBlock> getAcceptBlock() {
		return this.acceptBlock;
	}

	public void setAcceptBlock(ArrayList<AcceptBlock> acceptBlock) {
		this.acceptBlock = acceptBlock;
	}

	public void addAcceptBlock(AcceptBlock acceptBlock) {
		this.acceptBlock.add(acceptBlock);
	}

	public ElementReference getBehavior() {
		return this.behavior;
	}

	public void setBehavior(ElementReference behavior) {
		this.behavior = behavior;
	}

	public boolean getIsSimple() {
		return this.isSimple;
	}

	public void setIsSimple(boolean isSimple) {
		this.isSimple = isSimple;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (AcceptBlock acceptBlock : this.getAcceptBlock()) {
			if (acceptBlock != null) {
				acceptBlock.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // AcceptStatement
