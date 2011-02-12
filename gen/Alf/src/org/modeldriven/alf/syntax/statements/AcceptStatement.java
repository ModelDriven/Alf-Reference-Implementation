
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
 * A statement used to accept the receipt of instances of one or more signals.
 **/

public class AcceptStatement extends Statement implements IAcceptStatement {

	private ArrayList<IAcceptBlock> acceptBlock = new ArrayList<IAcceptBlock>();

	public ArrayList<IAcceptBlock> getAcceptBlock() {
		return this.acceptBlock;
	}

	public void setAcceptBlock(ArrayList<IAcceptBlock> acceptBlock) {
		this.acceptBlock = acceptBlock;
	}

	public void addAcceptBlock(IAcceptBlock acceptBlock) {
		this.acceptBlock.add(acceptBlock);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<IAcceptBlock> acceptBlock = this.getAcceptBlock();
		if (acceptBlock != null) {
			for (IAcceptBlock item : this.getAcceptBlock()) {
				if (item != null) {
					item.print(prefix + " ");
				} else {
					System.out.println(prefix + " null");
				}
			}
		}
	}
} // AcceptStatement
