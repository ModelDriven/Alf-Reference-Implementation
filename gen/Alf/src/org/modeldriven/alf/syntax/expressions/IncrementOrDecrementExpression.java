
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
 * A unary expression with either an increment or decrement operator.
 **/

public class IncrementOrDecrementExpression extends Expression {

	private String operator = "";
	private AssignedSource assignment = null; // DERIVED
	private LeftHandSide operand = null;
	private Expression expression = null; // DERIVED
	private ElementReference feature = null; // DERIVED
	private boolean isPrefix = false;
	private boolean isFeature = false; // DERIVED
	private boolean isIndexed = false; // DERIVED
	private boolean isDataValueUpdate = false; // DERIVED

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public AssignedSource getAssignment() {
		return this.assignment;
	}

	public void setAssignment(AssignedSource assignment) {
		this.assignment = assignment;
	}

	public LeftHandSide getOperand() {
		return this.operand;
	}

	public void setOperand(LeftHandSide operand) {
		this.operand = operand;
	}

	public Expression getExpression() {
		return this.expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	public ElementReference getFeature() {
		return this.feature;
	}

	public void setFeature(ElementReference feature) {
		this.feature = feature;
	}

	public boolean getIsPrefix() {
		return this.isPrefix;
	}

	public void setIsPrefix(boolean isPrefix) {
		this.isPrefix = isPrefix;
	}

	public boolean getIsFeature() {
		return this.isFeature;
	}

	public void setIsFeature(boolean isFeature) {
		this.isFeature = isFeature;
	}

	public boolean getIsIndexed() {
		return this.isIndexed;
	}

	public void setIsIndexed(boolean isIndexed) {
		this.isIndexed = isIndexed;
	}

	public boolean getIsDataValueUpdate() {
		return this.isDataValueUpdate;
	}

	public void setIsDataValueUpdate(boolean isDataValueUpdate) {
		this.isDataValueUpdate = isDataValueUpdate;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after an increment and decrement expression include
		 * all those after its operand expression. Further, if the operand
		 * expression, considered as a left hand side, is a local name, then
		 * this is reassigned.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operator:");
		s.append(this.operator);
		s.append(" isPrefix:");
		s.append(this.isPrefix);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.operand != null) {
			this.operand.print(prefix + " ");
		}
	}
} // IncrementOrDecrementExpression
