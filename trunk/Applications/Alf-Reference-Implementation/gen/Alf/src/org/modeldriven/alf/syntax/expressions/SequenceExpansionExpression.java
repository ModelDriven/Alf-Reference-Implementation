
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

import org.modeldriven.alf.syntax.expressions.impl.SequenceExpansionExpressionImpl;

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

	public SequenceExpansionExpressionImpl getImpl() {
		return (SequenceExpansionExpressionImpl) this.impl;
	}

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
		if (this.variableSource == null) {
			this.variableSource = this.getImpl().deriveVariableSource();
		}
		return this.variableSource;
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

	/**
	 * The assigned source for the expansion variable of a sequence expansion
	 * expression is the expression itself.
	 **/
	public boolean sequenceExpansionExpressionVariableSourceDerivation() {
		return this.getImpl()
				.sequenceExpansionExpressionVariableSourceDerivation();
	}

	/**
	 * The assignments before the primary expression of a sequence expansion
	 * expression are the same as the assignments before the sequence expansion
	 * expression.
	 **/
	public boolean sequenceExpansionExpressionAssignmentsBeforePrimary() {
		return this.getImpl()
				.sequenceExpansionExpressionAssignmentsBeforePrimary();
	}

	/**
	 * The assignments before the argument expression of a sequence expansion
	 * expression include those after the primary expression plus one for the
	 * expansion variable.
	 **/
	public boolean sequenceExpansionExpressionAssignmentsBeforeArgument() {
		return this.getImpl()
				.sequenceExpansionExpressionAssignmentsBeforeArgument();
	}

	/**
	 * The expansion variable name may not conflict with any name already
	 * assigned after the primary expression.
	 **/
	public boolean sequenceExpansionExpressionVariableName() {
		return this.getImpl().sequenceExpansionExpressionVariableName();
	}

	/**
	 * The expansion variable may not be assigned within the argument
	 * expression.
	 **/
	public boolean sequenceExpansionExpressionVariableAssignment() {
		return this.getImpl().sequenceExpansionExpressionVariableAssignment();
	}

	/**
	 * The assignments after a sequence expansion expression are the same as
	 * after its primary expression.
	 **/
	public ArrayList<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" operation:");
		s.append(this.getOperation());
		s.append(" variable:");
		s.append(this.getVariable());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		AssignedSource variableSource = this.getVariableSource();
		if (variableSource != null) {
			System.out.println(prefix + " /" + variableSource);
		}
		Expression argument = this.getArgument();
		if (argument != null) {
			argument.print(prefix + " ");
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.print(prefix + " ");
		}
	}
} // SequenceExpansionExpression
