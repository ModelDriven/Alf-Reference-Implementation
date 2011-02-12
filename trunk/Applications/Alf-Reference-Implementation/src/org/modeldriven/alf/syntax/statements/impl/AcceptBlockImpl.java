
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A block of an accept statement that accepts one or more signals.
 **/

public class AcceptBlockImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public AcceptBlockImpl(AcceptBlock self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.statements.AcceptBlock getSelf() {
		return (AcceptBlock) this.self;
	}

	public ArrayList<ElementReference> deriveSignal() {
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
