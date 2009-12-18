
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class EnumerationLiteralName extends Member {

	public EnumerationLiteralName(String name, String documentation) {
		this.setName(name);
		this.addDocumentation(documentation);
	} // EnumerationLiteralName

	public boolean isDistinguishableFrom(Member other,
			NamespaceDefinition namespace) {
		return !(other instanceof EnumerationLiteralName)
				|| super.isDistinguishableFrom(other, namespace);
	} // isDistinguishableFrom

} // EnumerationLiteralName
