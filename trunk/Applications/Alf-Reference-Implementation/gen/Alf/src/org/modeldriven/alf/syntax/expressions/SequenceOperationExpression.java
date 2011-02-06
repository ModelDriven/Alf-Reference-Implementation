
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
 * An expression used to invoke a behavior as if it was an operation on a target
 * sequence as a whole.
 **/

public class SequenceOperationExpression extends InvocationExpression implements
		ISequenceOperationExpression {

	private IExtentOrExpression primary = null;
	private IQualifiedName operation = null;

	public IExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(IExtentOrExpression primary) {
		this.primary = primary;
	}

	public IQualifiedName getOperation() {
		return this.operation;
	}

	public void setOperation(IQualifiedName operation) {
		this.operation = operation;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.print(prefix + " ");
		}
		IQualifiedName operation = this.getOperation();
		if (operation != null) {
			operation.print(prefix + " ");
		}
	}
} // SequenceOperationExpression
