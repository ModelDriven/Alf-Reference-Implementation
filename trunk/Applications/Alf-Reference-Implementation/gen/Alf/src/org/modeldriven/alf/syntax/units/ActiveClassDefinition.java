
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class ActiveClassDefinition extends ClassDefinition implements
		IActiveClassDefinition {

	private IActivityDefinition classifierBehavior = null;

	public IActivityDefinition getClassifierBehavior() {
		return this.classifierBehavior;
	}

	public void setClassifierBehavior(IActivityDefinition classifierBehavior) {
		this.classifierBehavior = classifierBehavior;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IActivityDefinition classifierBehavior = this.getClassifierBehavior();
		if (classifierBehavior != null) {
			classifierBehavior.print(prefix + " ");
		}
	}
} // ActiveClassDefinition
