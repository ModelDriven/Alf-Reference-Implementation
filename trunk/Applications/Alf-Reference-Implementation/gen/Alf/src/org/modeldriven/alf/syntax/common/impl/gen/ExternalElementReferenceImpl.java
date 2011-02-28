
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

/**
 * A direct reference to a UML model element.
 **/

public class ExternalElementReferenceImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.ElementReferenceImpl {

	public ExternalElementReferenceImpl(ExternalElementReference self) {
		super(self);
	}

	public ExternalElementReference getSelf() {
		return (ExternalElementReference) this.self;
	}

} // ExternalElementReferenceImpl
