
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

public class PropertyDefinition extends Member {

	private PropertyDeclaration declaration = null;
	private Expression initializer = null;

	public PropertyDefinition(PropertyDeclaration declaration) {
		this.declaration = declaration;
		this.setName(declaration.getName());
	} // PropertyDefinition

	public PropertyDeclaration getDeclaration() {
		return this.declaration;
	} // getDeclaration

	public void setInitializer(Expression initializer) {
		this.initializer = initializer;
	} // setInitializer

	public Expression getInitializer() {
		return this.initializer;
	} // getInitializer

	public void print(String prefix) {
		super.print(prefix);
		this.getDeclaration().printChild(prefix);

		Expression initializer = this.getInitializer();
		if (initializer != null) {
			initializer.printChild(prefix);
		}
	} // print

} // PropertyDefinition
