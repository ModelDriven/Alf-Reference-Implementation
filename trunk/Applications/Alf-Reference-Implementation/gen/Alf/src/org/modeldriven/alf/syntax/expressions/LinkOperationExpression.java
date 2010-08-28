
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
 * An expression used to create or destroy the links of an association.
 **/

public class LinkOperationExpression extends InvocationExpression {

	private String operation = "";
	private boolean isCreation = false; // DERIVED
	private boolean isClear = false; // DERIVED
	private QualifiedName associationName = null;

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public boolean getIsCreation() {
		return this.isCreation;
	}

	public void setIsCreation(boolean isCreation) {
		this.isCreation = isCreation;
	}

	public boolean getIsClear() {
		return this.isClear;
	}

	public void setIsClear(boolean isClear) {
		this.isClear = isClear;
	}

	public QualifiedName getAssociationName() {
		return this.associationName;
	}

	public void setAssociationName(QualifiedName associationName) {
		this.associationName = associationName;
	}

	public ArrayList<ElementReference> parameterElements() {
		/*
		 * For a clear association operation, returns a single, typeless
		 * parameter. Otherwise, returns the ends of the named association.
		 */
		return new ArrayList<ElementReference>(); // STUB
	} // parameterElements

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operation:");
		s.append(this.operation);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.associationName != null) {
			this.associationName.print(prefix + " ");
		}
	}
} // LinkOperationExpression
