
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
 * A block of an accept statement that accepts one or more signals.
 **/

public class AcceptBlock extends SyntaxElement {

	private String name = "";
	private Block block = null;
	private QualifiedNameList signalNames = null;
	private ArrayList<ElementReference> signal = new ArrayList<ElementReference>(); // DERIVED

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Block getBlock() {
		return this.block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public QualifiedNameList getSignalNames() {
		return this.signalNames;
	}

	public void setSignalNames(QualifiedNameList signalNames) {
		this.signalNames = signalNames;
	}

	public ArrayList<ElementReference> getSignal() {
		return this.signal;
	}

	public void setSignal(ArrayList<ElementReference> signal) {
		this.signal = signal;
	}

	public void addSignal(ElementReference signal) {
		this.signal.add(signal);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.name);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.block != null) {
			this.block.print(prefix + " ");
		}
		if (this.signalNames != null) {
			this.signalNames.print(prefix + " ");
		}
	}
} // AcceptBlock
