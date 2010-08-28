
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
 * An expression used to invoke a behavior as if it was an operation on a target
 * sequence as a whole.
 **/

public class SequenceOperationExpression extends InvocationExpression {

	private ExtentOrExpression primary = null;
	private QualifiedName operation = null;
	private boolean isCollectionConversion = false; // DERIVED
	private boolean isBitStringConversion = false; // DERIVED

	public ExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.primary = primary;
	}

	public QualifiedName getOperation() {
		return this.operation;
	}

	public void setOperation(QualifiedName operation) {
		this.operation = operation;
	}

	public boolean getIsCollectionConversion() {
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public boolean getIsBitStringConversion() {
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after a sequence operation expression include those
		 * made in the primary expression and those made in the tuple and, for
		 * an "in place" operation (one whose first parameter is inout), that
		 * made by the sequence operation expression itself.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.primary != null) {
			this.primary.print(prefix + " ");
		}
		if (this.operation != null) {
			this.operation.print(prefix + " ");
		}
	}
} // SequenceOperationExpression
