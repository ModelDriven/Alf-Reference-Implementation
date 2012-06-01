
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A block of an accept statement that accepts one or more signals.
 **/

public class AcceptBlockImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private String name = "";
	private Block block = null;
	private QualifiedNameList signalNames = null;
	private Collection<ElementReference> signal = null; // DERIVED

	public AcceptBlockImpl(AcceptBlock self) {
		super(self);
	}

	public AcceptBlock getSelf() {
		return (AcceptBlock) this.self;
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

	public Collection<ElementReference> getSignal() {
		if (this.signal == null) {
			this.setSignal(this.deriveSignal());
		}
		return this.signal;
	}

	public void setSignal(Collection<ElementReference> signal) {
		this.signal = signal;
	}

	public void addSignal(ElementReference signal) {
		this.signal.add(signal);
	}

	protected Collection<ElementReference> deriveSignal() {
		return null; // STUB
	}

	/**
	 * The signals of an accept block are the referents of the signal names of
	 * the accept block.
	 **/
	public boolean acceptBlockSignalDerivation() {
		this.getSelf().getSignal();
		return true;
	}

	/**
	 * All signal names in an accept block must resolve to signals.
	 **/
	public boolean acceptBlockSignalNames() {
		return true;
	}

} // AcceptBlockImpl
