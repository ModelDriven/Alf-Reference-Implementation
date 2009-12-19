
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

public class SuperInvocationExpression extends InvocationExpression {

	private QualifiedName target = null;

	public SuperInvocationExpression(QualifiedName target, Tuple tuple) {
		super(tuple);
		this.target = target;
	} // SuperInvocationExpression

	public QualifiedName getTarget() {
		return this.target;
	} // getTarget

	public void printTarget(String prefix) {
		QualifiedName target = this.getTarget();
		if (target != null) {
			this.getTarget().printChild(prefix);
		}

	} // printTarget

} // SuperInvocationExpression
