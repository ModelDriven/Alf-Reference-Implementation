
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

public class SuperclassConstructorInvocation extends Statement {

	private Tuple tuple = null;
	private QualifiedName constructor = null;

	public SuperclassConstructorInvocation(QualifiedName constructor,
			Tuple tuple, String documentation) {
		this.constructor = constructor;
		this.tuple = tuple;
		this.addDocumentation(documentation);
	} // SuperclassConstructorInvocation

	public QualifiedName getConstructor() {
		return this.constructor;
	} // getConstructor

	public Tuple getTuple() {
		return this.tuple;
	} // getTuple

	public void print(String prefix) {
		super.print(prefix);

		if (this.getConstructor() != null) {
			this.getConstructor().printChild(prefix);
		}
	} // print

} // SuperclassConstructorInvocation
