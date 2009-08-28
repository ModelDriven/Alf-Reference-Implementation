
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package alf.syntax.expressions;

import alf.syntax.behavioral.*;
import alf.syntax.expressions.*;
import alf.syntax.namespaces.*;
import alf.syntax.nodes.*;
import alf.syntax.structural.*;

import java.util.ArrayList;

public class CollectionValueExpression extends Expression {

	private CollectionElements elements = null;
	private TypedDeclaration declaration = null;

	public CollectionValueExpression(TypedDeclaration declaration,
			CollectionElements elements) {
		this.declaration = declaration;
		this.elements = elements;
	} // CollectionValueExpression

	public TypedDeclaration getDeclaration() {
		return this.declaration;
	} // getDeclaration

	public CollectionElements getElements() {
		return this.elements;
	} // getElements

	public void print(String prefix) {
		super.print(prefix);
		this.getDeclaration().printChild(prefix);

		if (this.getElements() != null) {
			this.getElements().printChild(prefix);
		}
	} // print

} // CollectionValueExpression
