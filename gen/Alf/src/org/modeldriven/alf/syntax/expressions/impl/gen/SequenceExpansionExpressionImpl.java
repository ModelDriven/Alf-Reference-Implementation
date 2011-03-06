
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An expression used to carry out one of a predefined set of operations over
 * each of the elements in a sequence.
 **/

public abstract class SequenceExpansionExpressionImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.ExpressionImpl {

	private String operation = "";
	private String variable = "";
	private AssignedSource variableSource = null; // DERIVED
	private Expression argument = null;
	private ExtentOrExpression primary = null;

	public SequenceExpansionExpressionImpl(SequenceExpansionExpression self) {
		super(self);
	}

	public SequenceExpansionExpression getSelf() {
		return (SequenceExpansionExpression) this.self;
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
			this.setVariableSource(this.deriveVariableSource());
		}
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

	protected AssignedSource deriveVariableSource() {
		return null; // STUB
	}

	/**
	 * The assigned source for the expansion variable of a sequence expansion
	 * expression is the expression itself.
	 **/
	public boolean sequenceExpansionExpressionVariableSourceDerivation() {
		this.getSelf().getVariableSource();
		return true;
	}

	/**
	 * The assignments before the primary expression of a sequence expansion
	 * expression are the same as the assignments before the sequence expansion
	 * expression.
	 **/
	public boolean sequenceExpansionExpressionAssignmentsBeforePrimary() {
		return true;
	}

	/**
	 * The assignments before the argument expression of a sequence expansion
	 * expression include those after the primary expression plus one for the
	 * expansion variable.
	 **/
	public boolean sequenceExpansionExpressionAssignmentsBeforeArgument() {
		return true;
	}

	/**
	 * The expansion variable name may not conflict with any name already
	 * assigned after the primary expression.
	 **/
	public boolean sequenceExpansionExpressionVariableName() {
		return true;
	}

	/**
	 * The expansion variable may not be assigned within the argument
	 * expression.
	 **/
	public boolean sequenceExpansionExpressionVariableAssignment() {
		return true;
	}

	/**
	 * The assignments after a sequence expansion expression are the same as
	 * after its primary expression.
	 **/
	public Collection<AssignedSource> updateAssignments() {
		return new ArrayList<AssignedSource>(); // STUB
	} // updateAssignments

} // SequenceExpansionExpressionImpl
