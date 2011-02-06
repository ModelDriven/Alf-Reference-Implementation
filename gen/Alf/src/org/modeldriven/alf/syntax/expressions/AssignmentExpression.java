
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
 * An expression used to assign a value to a local name, parameter or property.
 **/

public class AssignmentExpression extends Expression implements
		IAssignmentExpression {

	private String operator = "";
	private ILeftHandSide leftHandSide = null;
	private IExpression rightHandSide = null;

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public ILeftHandSide getLeftHandSide() {
		return this.leftHandSide;
	}

	public void setLeftHandSide(ILeftHandSide leftHandSide) {
		this.leftHandSide = leftHandSide;
	}

	public IExpression getRightHandSide() {
		return this.rightHandSide;
	}

	public void setRightHandSide(IExpression rightHandSide) {
		this.rightHandSide = rightHandSide;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operator:");
		s.append(this.getOperator());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ILeftHandSide leftHandSide = this.getLeftHandSide();
		if (leftHandSide != null) {
			leftHandSide.print(prefix + " ");
		}
		IExpression rightHandSide = this.getRightHandSide();
		if (rightHandSide != null) {
			rightHandSide.print(prefix + " ");
		}
	}
} // AssignmentExpression
