
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.statements.impl.AcceptBlockImpl;

/**
 * A block of an accept statement that accepts one or more signals.
 **/

public class AcceptBlock extends SyntaxElement {

	private String name = "";
	private Block block = null;
	private QualifiedNameList signalNames = null;
	private ArrayList<ElementReference> signal = null; // DERIVED

	public AcceptBlock() {
		this.impl = new AcceptBlockImpl(this);
	}

	public AcceptBlockImpl getImpl() {
		return (AcceptBlockImpl) this.impl;
	}

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
		if (this.signal == null) {
			this.signal = this.getImpl().deriveSignal();
		}
		return this.signal;
	}

	/**
	 * The signals of an accept block are the referents of the signal names of
	 * the accept block.
	 **/
	public boolean acceptBlockSignalDerivation() {
		return this.getImpl().acceptBlockSignalDerivation();
	}

	/**
	 * All signal names in an accept block must resolve to signals.
	 **/
	public boolean acceptBlockSignalNames() {
		return this.getImpl().acceptBlockSignalNames();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.getName());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Block block = this.getBlock();
		if (block != null) {
			System.out.println(prefix + " block:");
			block.print(prefix + "  ");
		}
		QualifiedNameList signalNames = this.getSignalNames();
		if (signalNames != null) {
			System.out.println(prefix + " signalNames:");
			signalNames.print(prefix + "  ");
		}
		ArrayList<ElementReference> signal = this.getSignal();
		if (signal != null) {
			if (signal.size() > 0) {
				System.out.println(prefix + " /signal:");
			}
			for (ElementReference item : this.getSignal()) {
				System.out.println(prefix + "  " + item);
			}
		}
	}
} // AcceptBlock
