
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * The definition of an active class.
 **/

public class ActiveClassDefinition extends ClassDefinition {

	private ActivityDefinition classifierBehavior = null;

	public ActivityDefinition getClassifierBehavior() {
		return this.classifierBehavior;
	}

	public void setClassifierBehavior(ActivityDefinition classifierBehavior) {
		this.classifierBehavior = classifierBehavior;
	}

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns true if the given unit definition matches this active class
		 * definition considered as a class definition and the subunit is for an
		 * active class definition.
		 */
		return false; // STUB
	} // matchForStub

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.classifierBehavior != null) {
			this.classifierBehavior.print(prefix + " ");
		}
	}
} // ActiveClassDefinition
