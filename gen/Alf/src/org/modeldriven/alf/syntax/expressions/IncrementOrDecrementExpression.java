
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
 * A unary expression with either an increment or decrement operator.
 **/

public class IncrementOrDecrementExpression extends Expression implements
		IIncrementOrDecrementExpression {

	private String operator = "";
	private ILeftHandSide operand = null;
	private Boolean isPrefix = false;

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public ILeftHandSide getOperand() {
		return this.operand;
	}

	public void setOperand(ILeftHandSide operand) {
		this.operand = operand;
	}

	public Boolean getIsPrefix() {
		return this.isPrefix;
	}

	public void setIsPrefix(Boolean isPrefix) {
		this.isPrefix = isPrefix;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operator:");
		s.append(this.getOperator());
		s.append(" isPrefix:");
		s.append(this.getIsPrefix());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ILeftHandSide operand = this.getOperand();
		if (operand != null) {
			operand.print(prefix + " ");
		}
	}
} // IncrementOrDecrementExpression
