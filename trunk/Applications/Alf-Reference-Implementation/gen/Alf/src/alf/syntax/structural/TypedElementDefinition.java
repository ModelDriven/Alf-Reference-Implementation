
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

public abstract class TypedElementDefinition extends Member {

	private TypedElementDeclaration declaration = null;

	public TypedElementDefinition(TypedElementDeclaration declaration) {
		this.declaration = declaration;
	} // TypedElementDefinition

	public TypedElementDeclaration getDeclaration() {
		return this.declaration;
	} // getDeclaration

	public void print(String prefix) {
		super.print(prefix);
		this.getDeclaration().printChild(prefix);
	} // print

	public Member getType() {
		// System.out.println("getType: name = " + this.getName() +
		// ", namespace = " + this.getNamespace());

		return this.getDeclaration().getClassifier(this.getNamespace());
	} // getType

} // TypedElementDefinition
