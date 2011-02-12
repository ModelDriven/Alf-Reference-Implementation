
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * A reference to an element or package to be imported into a unit.
 **/

public abstract class ImportReferenceImpl extends
		org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl {

	public ImportReferenceImpl(ImportReference self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.ImportReference getSelf() {
		return (ImportReference) this.self;
	}

	public ElementReference deriveReferent() {
		return null; // STUB
	}

	/**
	 * The referent of an import reference is the element denoted by the
	 * referent name.
	 **/
	public boolean importReferenceReferentDerivation() {
		this.getSelf().getReferent();
		return true;
	}

	/**
	 * The referent name of an import reference must resolve to a single element
	 * with public or empty visibility.
	 **/
	public boolean importReferenceReferent() {
		return true;
	}

} // ImportReferenceImpl
