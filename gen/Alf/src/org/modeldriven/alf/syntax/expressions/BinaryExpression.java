
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
 * An expression consisting of an operator acting on two operand expressions.
 **/

public abstract class BinaryExpression extends Expression {

	private Expression operand1 = null;
	private Expression operand2 = null;
	private String operator = "";

	public Expression getOperand1() {
		return this.operand1;
	}

	public void setOperand1(Expression operand1) {
		this.operand1 = operand1;
	}

	public Expression getOperand2() {
		return this.operand2;
	}

	public void setOperand2(Expression operand2) {
		this.operand2 = operand2;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public boolean validateAssignments() {
		/*
		 * In general the assignments before the operand expressions of a binary
		 * expression are the same as those before the binary expression and, if
		 * an assignment for a name is changed in one operand expression, then
		 * the assignment for that name may not change in the other operand
		 * expression. (This is overridden for conditional logical expressions.)
		 */
		return false; // STUB
	} // validateAssignments

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after a binary expression include all the assignments
		 * before the expression that are not reassigned in either operand
		 * expression, plus the new assignments from each of the operand
		 * expressions.
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
		if (this.operand1 != null) {
			this.operand1.print(prefix + " ");
		}
		if (this.operand2 != null) {
			this.operand2.print(prefix + " ");
		}
	}
} // BinaryExpression
