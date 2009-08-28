
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

public class InstanceCreationExpression extends InvocationExpression {

	private QualifiedName constructor = null;

	public InstanceCreationExpression(QualifiedName constructor, Tuple tuple) {
		super(tuple);
		this.constructor = constructor;
	} // InstanceCreationExpression

	public QualifiedName getConstructor() {
		return this.constructor;
	} // getConstructor

	public void printTarget(String prefix) {
		this.getConstructor().printChild(prefix);
	} // printTarget

} // InstanceCreationExpression
