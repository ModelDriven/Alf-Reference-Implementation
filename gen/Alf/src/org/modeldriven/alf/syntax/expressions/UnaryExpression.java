
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
 * An expression consisting of an operator acting on a single operand
 * expression.
 **/

public abstract class UnaryExpression extends Expression {

	private String operator = "";
	private Expression operand = null;

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Expression getOperand() {
		return this.operand;
	}

	public void setOperand(Expression operand) {
		this.operand = operand;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * By default, the assignments after a unary expression are the same as
		 * those after its operand expression.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operator:");
		s.append(this.operator);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.operand != null) {
			this.operand.print(prefix + " ");
		}
	}
} // UnaryExpression
