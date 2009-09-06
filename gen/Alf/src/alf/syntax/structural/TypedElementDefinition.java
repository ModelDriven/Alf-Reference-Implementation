
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.structural;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public abstract class TypedElementDefinition extends Member {

	private TypedElementDeclaration declaration = null;

	public TypedElementDefinition(TypedElementDeclaration declaration) {
		// TODO
	} // TypedElementDefinition

	public TypedElementDeclaration getDeclaration() {
		return this.declaration;
	} // getDeclaration

	public void print(String prefix) {
		super.print(prefix);
		this.getDeclaration().printChild(prefix);
	} // print

} // TypedElementDefinition
