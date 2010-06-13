
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
		QualifiedName constructor = this.getConstructor();

		if (constructor != null) {
			constructor.printChild(prefix);
		}
	} // printTarget

} // InstanceCreationExpression
