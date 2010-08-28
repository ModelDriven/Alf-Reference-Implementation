
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
 * An expression used to carry out one of a predefined set of operations over
 * each of the elements in a sequence.
 **/

public abstract class SequenceExpansionExpression extends Expression {

	private String operation = "";
	private String variable = "";
	private AssignedSource variableSource = null; // DERIVED
	private Expression argument = null;
	private ExtentOrExpression primary = null;

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getVariable() {
		return this.variable;
	}

	public void setVariable(String variable) {
		this.variable = variable;
	}

	public AssignedSource getVariableSource() {
		return this.variableSource;
	}

	public void setVariableSource(AssignedSource variableSource) {
		this.variableSource = variableSource;
	}

	public Expression getArgument() {
		return this.argument;
	}

	public void setArgument(Expression argument) {
		this.argument = argument;
	}

	public ExtentOrExpression getPrimary() {
		return this.primary;
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.primary = primary;
	}

	public ArrayList<AssignedSource> updateAssignments() {
		/*
		 * The assignments after a sequence expansion expression are the same as
		 * after its primary expression.
		 */
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operation:");
		s.append(this.operation);
		s.append(" variable:");
		s.append(this.variable);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.argument != null) {
			this.argument.print(prefix + " ");
		}
		if (this.primary != null) {
			this.primary.print(prefix + " ");
		}
	}
} // SequenceExpansionExpression
