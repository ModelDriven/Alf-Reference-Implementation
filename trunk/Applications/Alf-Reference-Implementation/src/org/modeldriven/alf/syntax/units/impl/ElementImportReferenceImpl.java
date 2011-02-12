
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
 * An import reference to a single element to be imported into a unit.
 **/

public class ElementImportReferenceImpl extends
		org.modeldriven.alf.syntax.units.impl.ImportReferenceImpl {

	public ElementImportReferenceImpl(ElementImportReference self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.ElementImportReference getSelf() {
		return (ElementImportReference) this.self;
	}

} // ElementImportReferenceImpl
