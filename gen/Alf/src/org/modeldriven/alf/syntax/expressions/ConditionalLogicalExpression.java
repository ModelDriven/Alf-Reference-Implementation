
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.ConditionalLogicalExpressionImpl;

/**
 * A binary expression with a conditional logical expression, for which the
 * evaluation of the second operand expression is conditioned on the result of
 * evaluating the first operand expression.
 **/

public class ConditionalLogicalExpression extends BinaryExpression {

	public ConditionalLogicalExpression() {
		this.impl = new ConditionalLogicalExpressionImpl(this);
	}

	public ConditionalLogicalExpressionImpl getImpl() {
		return (ConditionalLogicalExpressionImpl) this.impl;
	}

	/**
	 * A conditional logical expression has type Boolean.
	 **/
	public boolean conditionalLogicalExpressionTypeDerivation() {
		return this.getImpl().conditionalLogicalExpressionTypeDerivation();
	}

	/**
	 * A conditional logical expression has a multiplicity lower bound of 0 if
	 * the lower bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean conditionalLogicalExpressionLower() {
		return this.getImpl().conditionalLogicalExpressionLower();
	}

	/**
	 * A conditional logical expression has a multiplicity upper bound of 1.
	 **/
	public boolean conditionalLogicalExpressionUpper() {
		return this.getImpl().conditionalLogicalExpressionUpper();
	}

	/**
	 * The operands of a conditional logical expression must have type Boolean.
	 **/
	public boolean conditionalLogicalExpressionOperands() {
		return this.getImpl().conditionalLogicalExpressionOperands();
	}

	/**
	 * The assignments before the first operand expression of a conditional
	 * logical expression are the same as those before the conditional logical
	 * expression. The assignments before the second operand expression are the
	 * same as those after the first operand expression.
	 **/
	public Boolean validateAssignments() {
		return this.getImpl().validateAssignments();
	}

	/**
	 * If a name has the same assigned source after the second operand
	 * expression as before it, then that is its assigned source after the
	 * conditional logical expression. If a name is unassigned before the second
	 * operand expression, then it is considered unassigned after the
	 * conditional logical expression, even if it has an assigned source after
	 * the second operand expression. Otherwise its assigned source after the
	 * conditional logical expression is the conditional logical expression
	 * itself.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // ConditionalLogicalExpression
