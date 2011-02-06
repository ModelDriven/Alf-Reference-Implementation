
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
 * The definition of an activity, with any formal parameters defined as owned
 * members.
 **/

public class ActivityDefinition extends ClassifierDefinition implements
		IActivityDefinition {

	private IBlock body = null;

	public IBlock getBody() {
		return this.body;
	}

	public void setBody(IBlock body) {
		this.body = body;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IBlock body = this.getBody();
		if (body != null) {
			body.print(prefix + " ");
		}
	}
} // ActivityDefinition
