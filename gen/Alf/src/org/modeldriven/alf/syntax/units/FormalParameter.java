
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
 * A typed element definition for the formal parameter of an activity or
 * operation.
 **/

public class FormalParameter extends TypedElementDefinition implements
		IFormalParameter {

	private String direction = "";

	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" direction:");
		s.append(this.getDirection());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // FormalParameter
