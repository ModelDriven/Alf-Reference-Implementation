
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An expression used to carry out one of a predefined set of operations over
 * each of the elements in a sequence.
 **/

public abstract class SequenceExpansionExpressionImpl extends ExpressionImpl {

	private String operation = "";
	private String variable = "";
	private AssignedSource variableSource = null; // DERIVED
	private Expression argument = null;
	private ExtentOrExpression primary = null;

	public SequenceExpansionExpressionImpl(SequenceExpansionExpression self) {
		super(self);
	}

	@Override
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
		if (primary != null) {
		    primary.getImpl().setContainingExpression(this.getSelf());
		}
	}

	/**
	 * The assigned source for the expansion variable of a sequence expansion
	 * expression is the expression itself. The type of the assigned source is
	 * the type of the primary expression and the multiplicity bounds are both
	 * 1.
	 **/
	protected AssignedSource deriveVariableSource() {
	    SequenceExpansionExpression self = this.getSelf();
	    ExtentOrExpression primary = self.getPrimary();
	    AssignedSource variableSource = new AssignedSource();
	    variableSource.setName(self.getVariable());
	    variableSource.setSource(self);
	    variableSource.setType(primary == null? null: 
	        primary.getExpression().getType());
	    variableSource.setLower(1);
	    variableSource.setUpper(1);
		return variableSource;
	}
	
	/*
	 * Derivations
	 */

	public boolean sequenceExpansionExpressionVariableSourceDerivation() {
		this.getSelf().getVariableSource();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The assignments before the primary expression of a sequence expansion
	 * expression are the same as the assignments before the sequence expansion
	 * expression.
	 **/
	public boolean sequenceExpansionExpressionAssignmentsBeforePrimary() {
	    // Note: This is handled by updateAssignments.
		return true;
	}

	/**
	 * The assignments before the argument expression of a sequence expansion
	 * expression include those after the primary expression plus one for the
	 * expansion variable.
	 **/
	public boolean sequenceExpansionExpressionAssignmentsBeforeArgument() {
        // Note: This is handled by updateAssignments.
		return true;
	}

	/**
	 * The expansion variable name may not conflict with any name already
	 * assigned after the primary expression.
	 **/
	public boolean sequenceExpansionExpressionVariableName() {
	    SequenceExpansionExpression self = this.getSelf();
	    ExtentOrExpression primary = self.getPrimary();
	    this.getAssignmentAfterMap(); // Force computation of assignments.
	    return !primary.getExpression().getImpl().
	                getAssignmentAfterMap().containsKey(self.getVariable());
	}

	/**
	 * The expansion variable may not be assigned within the argument
	 * expression.
	 * 
	 * Note: This constraint needs to be expanded to:
	 * No local variable assigned before the argument expression may be 
	 * reassigned within that expression.
	 **/
	public boolean sequenceExpansionExpressionVariableAssignment() {
        SequenceExpansionExpression self = this.getSelf();
        Expression argument = self.getArgument();
        if (argument != null) {           
            this.getAssignmentAfterMap(); // Force computation of assignments.
            for (AssignedSource assignmentAfter: argument.getAssignmentAfter()) {
                AssignedSource assignmentBefore = 
                    argument.getImpl().getAssignmentBefore(assignmentAfter.getName());
                if (assignmentBefore != null && 
                        assignmentBefore.getSource() != assignmentAfter.getSource()) {
                    return false;
                }
            }
        }
        return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * The assignments after a sequence expansion expression are the same as
	 * after its primary expression.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
        SequenceExpansionExpression self = this.getSelf();
        ExtentOrExpression primary = self.getPrimary();
        Expression argument = self.getArgument();
        Map<String, AssignedSource> assignments = this.getAssignmentBeforeMap();
        if (primary != null) {
            Expression expression = primary.getExpression();
            if (expression != null) {
                expression.getImpl().setAssignmentBefore(assignments);
                assignments = expression.getImpl().getAssignmentAfterMap();
            }
        }
        if (argument != null) {
            Map<String, AssignedSource> assignmentsBeforeArgument =
                new HashMap<String, AssignedSource>(assignments);
            AssignedSource variableSource = self.getVariableSource();
            assignmentsBeforeArgument.put(variableSource.getName(), variableSource);
            argument.getImpl().setAssignmentBefore(assignmentsBeforeArgument);
        }
		return assignments;
	} // updateAssignments
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
        SequenceExpansionExpression self = this.getSelf();
        ExtentOrExpression primary = self.getPrimary();
        Expression argument = self.getArgument();
        if (primary != null) {
            primary.getImpl().setCurrentScope(currentScope);
        }
        if (argument != null) {
            argument.getImpl().setCurrentScope(currentScope);
        }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof SequenceExpansionExpression) {
            SequenceExpansionExpression self = this.getSelf();
            SequenceExpansionExpression baseExpression = 
                (SequenceExpansionExpression)base;
            Expression argument = baseExpression.getArgument();
            ExtentOrExpression primary = baseExpression.getPrimary();
            self.setOperation(baseExpression.getOperation());
            self.setVariable(baseExpression.getVariable());
            if (argument != null) {
                self.setArgument((Expression)argument.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (primary != null) {
                self.setPrimary((ExtentOrExpression)primary.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }

} // SequenceExpansionExpressionImpl
