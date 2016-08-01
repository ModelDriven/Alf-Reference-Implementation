/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
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
	
	@Override
    public void setAssignmentBefore(Collection<AssignedSource> assignmentBefore) {
        super.setAssignmentBefore(assignmentBefore);
        BinaryExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        Map<String, AssignedSource> assignmentBeforeMap = this.getAssignmentBeforeMap();
        if (operand1 != null) {
            operand1.getImpl().setAssignmentBefore(assignmentBeforeMap);
        }
        if (operand2 != null) {
            operand2.getImpl().setAssignmentBefore(assignmentBeforeMap);
        }
    }
    
	@Override
    public void setAssignmentBefore(Map<String, AssignedSource> assignmentBefore) {
        super.setAssignmentBefore(assignmentBefore);
        BinaryExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        if (operand1 != null) {
            operand1.getImpl().setAssignmentBefore(assignmentBefore);
        }
        if (operand2 != null) {
            operand2.getImpl().setAssignmentBefore(assignmentBefore);
        }
    }

    /**
     * If minLowerBound is greater than 0, then a binary expression has a
     * multiplicity lower bound of 1. Otherwise, the expression has a
     * multiplicity lower bound of 0 if the lower bound of either operand
     * expression is 0 and 1 otherwise.
     **/
    @Override
    protected Integer deriveLower() {
        BinaryExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        return self.minLowerBound() > 0? 1: 
               (operand1 != null && operand1.getLower() == 0) ||
               (operand2 != null && operand2.getLower() == 0)? 0: 1;
    }
    
    /**
     * By default, a binary expression has multiplicity upper bound of 1.
     */
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
    
	/*
	 * Constraints
	 */

    /**
     * The operands of a binary expression must both have a multiplicity lower
     * bound no less than that given by the minLowerBound helper operation. The
     * operands of a binary expression must both have a multiplicity upper bound
     * no greater than that given by the maxUpperBound helper operation.
     **/
	public boolean binaryExpressionOperandMultiplicity() {
	    BinaryExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    Expression operand2 = self.getOperand2();
	    int minLower = self.minLowerBound();
	    int maxUpper = self.maxUpperBound();
		return operand1 != null && operand2 != null && 
		       operand1.getLower() >= minLower && operand2.getLower() >= minLower &&
		       (maxUpper == -1 || 
		        (operand1.getUpper() != -1 || operand1.getUpper() <= maxUpper) &&
		        (operand2.getUpper() != -1 || operand2.getUpper() <= maxUpper));
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
            AssignedSourceImpl.putAssignments(assignmentsAfter, operand1.getImpl().getNewAssignments());            
        }
        if (operand2 != null) {
            AssignedSourceImpl.putAssignments(assignmentsAfter, operand2.getImpl().getNewAssignments());         
        }
		return assignmentsAfter;
	} // updateAssignments

    /**
     * By default, the minimum allowed lower bound for an operand of a binary
     * expression is 1.
     **/
    public Integer minLowerBound() {
        return 1;
    }

    /**
     * By default, the maximum allowed upper bound for an operand of a binary
     * expression is 1.
     **/
    public Integer maxUpperBound() {
        return 1;
    }

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
