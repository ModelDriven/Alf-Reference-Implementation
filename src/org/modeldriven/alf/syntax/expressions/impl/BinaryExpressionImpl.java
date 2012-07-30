
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An expression consisting of an operator acting on two operand expressions.
 **/

public abstract class BinaryExpressionImpl extends ExpressionImpl {

	private Expression operand1 = null;
	private Expression operand2 = null;
	private String operator = "";

	public BinaryExpressionImpl(BinaryExpression self) {
		super(self);
	}

	@Override
	public BinaryExpression getSelf() {
		return (BinaryExpression) this.self;
	}
	
	@Override
	public void deriveAll() {
	    BinaryExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    Expression operand2 = self.getOperand2();
	    Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
	    if (operand1 != null) {
	        operand1.getImpl().setAssignmentBefore(assignmentsBefore);
	    }
	    if (operand2 != null) {
	        operand2.getImpl().setAssignmentBefore(assignmentsBefore);
	    }
	    super.deriveAll();
	}

	public Expression getOperand1() {
		return this.operand1;
	}

	public void setOperand1(Expression operand1) {
		this.operand1 = operand1;
	}

	public Expression getOperand2() {
		return this.operand2;
	}

	public void setOperand2(Expression operand2) {
		this.operand2 = operand2;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The operands of a binary expression must both have a multiplicity upper
	 * bound of 1.
	 **/
	public boolean binaryExpressionOperandMultiplicity() {
	    BinaryExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    Expression operand2 = self.getOperand2();
		return operand1 != null && operand1.getUpper() == 1 &&
		            operand2 != null && operand2.getUpper() == 1;
	}

	/**
	 * The assignments in the operand expressions of a binary expression must be
	 * valid (as determined by the validateAssignments helper operation).
	 **/
	public boolean binaryExpressionOperandAssignments() {
		return validateAssignments();
	}

    /*
     * Helper Methods
     */
    
	/**
	 * In general the assignments before the operand expressions of a binary
	 * expression are the same as those before the binary expression and, if an
	 * assignment for a name is changed in one operand expression, then the
	 * assignment for that name may not change in the other operand expression.
	 * (This is overridden for conditional logical expressions.)
	 **/
	public Boolean validateAssignments() {
        BinaryExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        if (operand1 != null && operand2 != null) {
            self.getAssignmentAfter(); // Force computation of assignments.
            Collection<AssignedSource> assignmentsAfter1 = operand1.getImpl().getNewAssignments();
            Collection<AssignedSource> assignmentsAfter2 = operand2.getImpl().getNewAssignments();
            for (AssignedSource assignment: assignmentsAfter1) {
                if (assignment.getImpl().isAssignedIn(assignmentsAfter2)) {
                    return false;
                }
            }
        }
		return true;
	} // validateAssignments

	/**
	 * The assignments after a binary expression include all the assignments
	 * before the expression that are not reassigned in either operand
	 * expression, plus the new assignments from each of the operand
	 * expressions.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
	    BinaryExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Map<String, AssignedSource> assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsBefore);
        if (operand1 != null) {
            operand1.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsAfter.putAll(operand1.getImpl().getAssignmentAfterMap());
        }
        if (operand2 != null) {
            operand2.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsAfter.putAll(operand1.getImpl().getAssignmentAfterMap());
        }
		return assignmentsAfter;
	} // updateAssignments

    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        BinaryExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        if (operand1 != null) {
            operand1.getImpl().setCurrentScope(currentScope);
        }
        if (operand2 != null) {
            operand2.getImpl().setCurrentScope(currentScope);
        }
    }

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof BinaryExpression) {
            BinaryExpression self = this.getSelf();
            BinaryExpression baseExpression = (BinaryExpression)base;
            Expression operand1 = baseExpression.getOperand1();
            Expression operand2 = baseExpression.getOperand2();
            if (operand1 != null) {
                self.setOperand1((Expression)operand1.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (operand2 != null) {
                self.setOperand2((Expression)operand2.getImpl().
                        bind(templateParameters, templateArguments));
            }
            self.setOperator(baseExpression.getOperator());
        }
    }
    
} // BinaryExpressionImpl
