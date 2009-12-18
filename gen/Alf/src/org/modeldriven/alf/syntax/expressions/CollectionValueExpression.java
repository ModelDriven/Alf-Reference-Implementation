
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class CollectionValueExpression extends Expression {

	private CollectionElements elements = null;
	private TypedElementDeclaration declaration = null;

	public CollectionValueExpression(TypedElementDeclaration declaration,
			CollectionElements elements) {
		this.declaration = declaration;
		this.elements = elements;
	} // CollectionValueExpression

	public TypedElementDeclaration getDeclaration() {
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
