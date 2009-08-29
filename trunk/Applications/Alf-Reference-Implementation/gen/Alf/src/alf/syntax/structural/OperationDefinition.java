
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

public class OperationDefinition extends Member {

	private OperationDeclaration declaration = null;
	private Block body = null;

	public OperationDefinition(OperationDeclaration declaration, Block body) {
		this.declaration = declaration;
		this.setName(declaration.getName());
		this.body = body;
	} // OperationDefinition

	public OperationDeclaration getDeclaration() {
		return this.declaration;
	} // getDeclaration

	public Block getBody() {
		return this.body;
	} // getBody

	public void print(String prefix) {
		super.print(prefix);
		this.getDeclaration().printChild(prefix);
		this.getBody().printChild(prefix);
	} // print

} // OperationDefinition
