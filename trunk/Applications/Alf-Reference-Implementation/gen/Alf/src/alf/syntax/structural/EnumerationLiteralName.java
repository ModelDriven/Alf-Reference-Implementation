
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.nodes.*;
import alf.syntax.SyntaxNode;
import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.structural.*;

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
