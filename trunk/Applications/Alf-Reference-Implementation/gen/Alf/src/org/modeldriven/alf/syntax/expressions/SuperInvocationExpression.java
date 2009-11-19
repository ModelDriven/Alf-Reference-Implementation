
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.SyntaxNode;
import org.modeldriven.alf.syntax.behavioral.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.namespaces.*;
import org.modeldriven.alf.syntax.structural.*;

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
		this.getTarget().printChild(prefix);
	} // printTarget

} // SuperInvocationExpression
