
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An invocation expression used to invoke an operation of a superclass.
 **/

public class SuperInvocationExpression extends InvocationExpression {

	private QualifiedName target = null;

	public QualifiedName getTarget() {
		return this.target;
	}

	public void setTarget(QualifiedName target) {
		this.target = target;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.target != null) {
			this.target.print(prefix + " ");
		}
	}
} // SuperInvocationExpression
