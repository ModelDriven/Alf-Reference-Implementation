
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
 * An expression that uses the value of one operand expression to condition the
 * evaluation of one of two other operand expressions.
 **/

public class ConditionalTestExpression extends Expression {

	private Expression operand1 = null;
	private Expression operand2 = null;
	private Expression operand3 = null;

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

	public Expression getOperand3() {
		return this.operand3;
	}

	public void setOperand3(Expression operand3) {
		this.operand3 = operand3;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * Returns unchanged all assignments for local names that are not
		 * reassigned in either the second or third operand expressions. Any
		 * local names that have different assignments after the second and
		 * third operand expressions are adjusted to have the conditional-test
		 * expression as their assigned source.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
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
		if (this.operand3 != null) {
			this.operand3.print(prefix + " ");
		}
	}
} // ConditionalTestExpression
