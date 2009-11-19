
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.structural;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

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
