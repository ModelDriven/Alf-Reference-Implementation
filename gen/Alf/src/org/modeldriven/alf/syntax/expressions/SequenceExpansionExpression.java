
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.AlfParser;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.SequenceExpansionExpressionImpl;

/**
 * An expression used to carry out one of a predefined set of operations over
 * each of the elements in a sequence.
 **/

public abstract class SequenceExpansionExpression extends Expression {

	public SequenceExpansionExpression() {
	}

	public SequenceExpansionExpression(AlfParser parser) {
		this();
		this.setParserInfo(parser.getFileName(), parser.getLine(), parser
				.getColumn());
	}

	public SequenceExpansionExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SequenceExpansionExpressionImpl getImpl() {
		return (SequenceExpansionExpressionImpl) this.impl;
	}

	public String getOperation() {
		return this.getImpl().getOperation();
	}

	public void setOperation(String operation) {
		this.getImpl().setOperation(operation);
	}

	public String getVariable() {
		return this.getImpl().getVariable();
	}

	public void setVariable(String variable) {
		this.getImpl().setVariable(variable);
	}

	public AssignedSource getVariableSource() {
		return this.getImpl().getVariableSource();
	}

	public void setVariableSource(AssignedSource variableSource) {
		this.getImpl().setVariableSource(variableSource);
	}

	public Expression getArgument() {
		return this.getImpl().getArgument();
	}

	public void setArgument(Expression argument) {
		this.getImpl().setArgument(argument);
	}

	public ExtentOrExpression getPrimary() {
		return this.getImpl().getPrimary();
	}

	public void setPrimary(ExtentOrExpression primary) {
		this.getImpl().setPrimary(primary);
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
	public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

	public void _deriveAll() {
		this.getVariableSource();
		super._deriveAll();
		Expression argument = this.getArgument();
		if (argument != null) {
			argument.deriveAll();
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.deriveAll();
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceExpansionExpressionVariableSourceDerivation()) {
			violations
					.add(new ConstraintViolation(
							"sequenceExpansionExpressionVariableSourceDerivation",
							this));
		}
		if (!this.sequenceExpansionExpressionAssignmentsBeforePrimary()) {
			violations
					.add(new ConstraintViolation(
							"sequenceExpansionExpressionAssignmentsBeforePrimary",
							this));
		}
		if (!this.sequenceExpansionExpressionAssignmentsBeforeArgument()) {
			violations.add(new ConstraintViolation(
					"sequenceExpansionExpressionAssignmentsBeforeArgument",
					this));
		}
		if (!this.sequenceExpansionExpressionVariableName()) {
			violations.add(new ConstraintViolation(
					"sequenceExpansionExpressionVariableName", this));
		}
		if (!this.sequenceExpansionExpressionVariableAssignment()) {
			violations.add(new ConstraintViolation(
					"sequenceExpansionExpressionVariableAssignment", this));
		}
		Expression argument = this.getArgument();
		if (argument != null) {
			argument.checkConstraints(violations);
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			primary.checkConstraints(violations);
		}
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" operation:");
		s.append(this.getOperation());
		s.append(" variable:");
		s.append(this.getVariable());
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		if (includeDerived) {
			AssignedSource variableSource = this.getVariableSource();
			if (variableSource != null) {
				System.out.println(prefix + " /variableSource:"
						+ variableSource.toString(includeDerived));
			}
		}
		Expression argument = this.getArgument();
		if (argument != null) {
			System.out.println(prefix + " argument:");
			argument.print(prefix + "  ", includeDerived);
		}
		ExtentOrExpression primary = this.getPrimary();
		if (primary != null) {
			System.out.println(prefix + " primary:");
			primary.print(prefix + "  ", includeDerived);
		}
	}
} // SequenceExpansionExpression
