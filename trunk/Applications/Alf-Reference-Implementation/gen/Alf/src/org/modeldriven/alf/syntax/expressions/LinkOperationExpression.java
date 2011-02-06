
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
 * An expression used to create or destroy the links of an association.
 **/

public class LinkOperationExpression extends InvocationExpression implements
		ILinkOperationExpression {

	private String operation = "";
	private IQualifiedName associationName = null;

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public IQualifiedName getAssociationName() {
		return this.associationName;
	}

	public void setAssociationName(IQualifiedName associationName) {
		this.associationName = associationName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operation:");
		s.append(this.getOperation());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		IQualifiedName associationName = this.getAssociationName();
		if (associationName != null) {
			associationName.print(prefix + " ");
		}
	}
} // LinkOperationExpression
