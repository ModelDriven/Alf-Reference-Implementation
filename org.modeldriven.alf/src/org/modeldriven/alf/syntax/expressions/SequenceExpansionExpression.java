
/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ExternalElementReference;
import org.modeldriven.alf.syntax.expressions.impl.SequenceExpansionExpressionImpl;

/**
 * An expression used to carry out one of a predefined set of operations over
 * each of the elements in a sequence.
 **/

public abstract class SequenceExpansionExpression extends Expression {

	public SequenceExpansionExpression() {
	}

	public SequenceExpansionExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public SequenceExpansionExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	 * expression is the expression itself. The type for the assignment is the
	 * type of the primary expression of the sequence expansion expression and
	 * the multiplicity lower and upper bounds are 1.
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
	 * The assignments after the argument expression of a sequence expansion
	 * expression must be the same as the assignments before the argument
	 * expression.
	 **/
	public boolean sequenceExpansionExpressionAssignmentsAfterArgument() {
		return this.getImpl()
				.sequenceExpansionExpressionAssignmentsAfterArgument();
	}

	/**
	 * The assignments after a sequence expansion expression are the same as
	 * after its primary expression.
	 **/
	@Override
    public Collection<AssignedSource> updateAssignments() {
		return this.getImpl().updateAssignments();
	}

    @Override
    public void _addExternalReferences(Collection<ExternalElementReference> references) {
        super._addExternalReferences(references);
        addExternalReferencesFor(references, this.getArgument());
        ExtentOrExpression.addExternalReferencesFor(references, this.getPrimary());
    }

	@Override
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

	@Override
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
		if (!this.sequenceExpansionExpressionAssignmentsAfterArgument()) {
			violations
					.add(new ConstraintViolation(
							"sequenceExpansionExpressionAssignmentsAfterArgument",
							this));
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" operation:");
		s.append(this.getOperation());
		s.append(" variable:");
		s.append(this.getVariable());
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
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
