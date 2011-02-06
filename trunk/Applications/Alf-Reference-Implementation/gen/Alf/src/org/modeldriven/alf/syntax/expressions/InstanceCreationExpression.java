
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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
 * An expression used to create a new instance of a class or data type.
 **/

public class InstanceCreationExpression extends InvocationExpression implements
		IInstanceCreationExpression {

	private IQualifiedName constructor = null;

	public IQualifiedName getConstructor() {
		return this.constructor;
	}

	public void setConstructor(IQualifiedName constructor) {
		this.constructor = constructor;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedName constructor = this.getConstructor();
		if (constructor != null) {
			constructor.print(prefix + " ");
		}
	}
} // InstanceCreationExpression
