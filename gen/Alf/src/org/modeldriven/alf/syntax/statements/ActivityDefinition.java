
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.statements;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class ActivityDefinition extends BehaviorDefinition {

	public void print(String prefix) {
		super.print(prefix);

		Block body = this.getBody();
		if (body != null) {
			body.printChild(prefix);
		}
	} // print

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return !(other instanceof ActivityDefinition)
				|| super.isDistinguishableFrom(other, namespace);
	} // isDistinguishableFrom

} // ActivityDefinition
