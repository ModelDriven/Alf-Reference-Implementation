
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
 * The definition of an active class.
 **/

public class ActiveClassDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.ClassDefinitionImpl {

	public ActiveClassDefinitionImpl(ActiveClassDefinition self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.ActiveClassDefinition getSelf() {
		return (ActiveClassDefinition) this.self;
	}

	/**
	 * Returns true if the given unit definition matches this active class
	 * definition considered as a class definition and the subunit is for an
	 * active class definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

} // ActiveClassDefinitionImpl
