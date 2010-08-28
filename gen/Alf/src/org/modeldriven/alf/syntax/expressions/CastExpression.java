
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
 * An expression used to filter values by type.
 **/

public class CastExpression extends Expression {

	private Expression operand = null;
	private QualifiedName typeName = null;

	public Expression getOperand() {
		return this.operand;
	}

	public void setOperand(Expression operand) {
		this.operand = operand;
	}

	public QualifiedName getTypeName() {
		return this.typeName;
	}

	public void setTypeName(QualifiedName typeName) {
		this.typeName = typeName;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after a cast expression are the same as those after
		 * its operand expression.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.operand != null) {
			this.operand.print(prefix + " ");
		}
		if (this.typeName != null) {
			this.typeName.print(prefix + " ");
		}
	}
} // CastExpression
