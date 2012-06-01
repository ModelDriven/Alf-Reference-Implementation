
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An expression that uses the value of one operand expression to condition the
 * evaluation of one of two other operand expressions.
 **/

public class ConditionalTestExpressionImpl extends ExpressionImpl {

	private Expression operand1 = null;
	private Expression operand2 = null;
	private Expression operand3 = null;

	public ConditionalTestExpressionImpl(ConditionalTestExpression self) {
		super(self);
	}

	@Override
	public ConditionalTestExpression getSelf() {
		return (ConditionalTestExpression) this.self;
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

	public Expression getOperand3() {
		return this.operand3;
	}

	public void setOperand3(Expression operand3) {
		this.operand3 = operand3;
	}

	/**
	 * The type of a conditional-test operator expression is the effective
	 * common ancestor (if one exists) of the types of its second and third
	 * operand expressions.
	 **/
	@Override
	protected ElementReference deriveType() {
	    ConditionalTestExpression self = this.getSelf();
	    Expression operand2 = self.getOperand2();
	    Expression operand3 = self.getOperand3();
	    if (operand2 == null || operand3 == null) {
	        return null;
	    } else if (operand2.getImpl().isNull()) {
	        return operand3.getType();
	    } else if (operand3.getImpl().isNull()) {
	        return operand2.getType();
	    } else {
	    return ClassifierDefinitionImpl.commonAncestor
	                        (operand2.getType(), operand3.getType());
	    }
	}
	
	/**
	 * The multiplicity lower bound of a conditional-test operator expression is
	 * the minimum of the multiplicity lower bounds of its second and third
	 * operand expressions.
	 **/
	@Override
    protected Integer deriveLower() {
        ConditionalTestExpression self = this.getSelf();
        Expression operand2 = self.getOperand2();
        Expression operand3 = self.getOperand3();
        int lower2 = operand2 == null? 0: operand2.getLower();
        int lower3 = operand3 == null? 0: operand3.getLower();
        return lower2 < lower3? lower2: lower3;
    }
    
	/**
	 * The multiplicity upper bound of a conditional-test operator expression is
	 * the maximum of the multiplicity upper bounds of its second and third
	 * operand expressions.
	 **/
    @Override
    protected Integer deriveUpper() {
        ConditionalTestExpression self = this.getSelf();
        Expression operand2 = self.getOperand2();
        Expression operand3 = self.getOperand3();
        int upper2 = operand2 == null? 0: operand2.getUpper();
        int upper3 = operand3 == null? 0: operand3.getUpper();
        return upper2 > upper3? upper2: upper3;
    }

	/*
	 * Derivations
	 */
	
	public boolean conditionalTestExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean conditionalTestExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean conditionalTestExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The first operand expression of a conditional-test expression must be of
	 * type Boolean and have a multiplicity upper bound of 1.
	 **/
	public boolean conditionalTestExpressionCondition() {
        ConditionalTestExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        ElementReference type = operand1 == null? null: operand1.getType();
		return type != null && type.getImpl().isBoolean() &&
		            operand1.getUpper() == 1;
	}

	/**
	 * The assignments before the first operand expression of a conditional-test
	 * expression are the same as those before the conditional-test expression.
	 * The assignments before the second and third operand expressions are the
	 * same as those after the first operand expression.
	 **/
	public boolean conditionalTestExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
		return true;
	}

	/**
	 * If a name is unassigned after the first operand expression and has an
	 * assigned source after one of the other operand expression, then it must
	 * have an assigned source after both of those expressions.
	 **/
	public boolean conditionalTestExpressionAssignmentsAfter() {
        ConditionalTestExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        Expression operand3 = self.getOperand3();
        Map<String, AssignedSource> assignmentsAfter1 = operand1 == null?
                this.getAssignmentBeforeMap(): 
                operand1.getImpl().getAssignmentAfterMap();
        for (AssignedSource assignment: this.getNewAssignments()) {
            String name = assignment.getName();
            if (!assignmentsAfter1.containsKey(name)) {
                if (operand2 == null || operand2.getImpl().getAssignmentAfter(name) == null ||
                    operand3 == null || operand3.getImpl().getAssignmentAfter(name) == null) {
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
	 * Returns unchanged all assignments for local names that are not reassigned
	 * in either the second or third operand expressions. Any local names that
	 * have different assignments after the second and third operand expressions
	 * are adjusted to have the conditional-test expression as their assigned
	 * source.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
        ConditionalTestExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        Expression operand3 = self.getOperand3();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Map<String, AssignedSource> assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsBefore);
        
        if (operand1 != null) {
            operand1.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsBefore = operand1.getImpl().getAssignmentAfterMap();
        }
        
        if (operand2 != null) {
            operand2.getImpl().setAssignmentBefore(assignmentsBefore);
            this.putAssignments(assignmentsAfter, operand2.getImpl().getNewAssignments());
        }
        
        if (operand3 != null) {
            operand3.getImpl().setAssignmentBefore(assignmentsBefore);
            this.putAssignments(assignmentsAfter, operand3.getImpl().getNewAssignments());
        }
        
		return assignmentsAfter;
	} // updateAssignments
	
	private void putAssignments(Map<String, AssignedSource> assignments, 
	        Collection<AssignedSource> newAssignments) {
	    ConditionalTestExpression self = this.getSelf();
	    for (AssignedSource newAssignment: newAssignments) {
	        String name = newAssignment.getName();
	        AssignedSource assignment = AssignedSourceImpl.makeAssignment(newAssignment);
	        assignment.setSource(self);
	        AssignedSource oldAssignment = assignments.get(name);
	        if (oldAssignment != null) {
	            ElementReference oldType = oldAssignment.getType();
	            ElementReference newType = newAssignment.getType();
	            if (newType != null && !newType.getImpl().equals(oldType)) {
	                assignment.setType(ClassifierDefinitionImpl.commonAncestor(oldType, newType));
	            }
	        }
	        assignments.put(name, assignment);
	    }
	}
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
        ConditionalTestExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        Expression operand3 = self.getOperand3();
        if (operand1 != null) {
            operand1.getImpl().setCurrentScope(currentScope);
        }
        if (operand2 != null) {
            operand2.getImpl().setCurrentScope(currentScope);
        }
        if (operand3 != null) {
            operand3.getImpl().setCurrentScope(currentScope);
        }
	}

    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof ConditionalTestExpression) {
            ConditionalTestExpression self = this.getSelf();
            ConditionalTestExpression baseExpression = 
                (ConditionalTestExpression)base;
            Expression operand1 = baseExpression.getOperand1();
            Expression operand2 = baseExpression.getOperand2();
            Expression operand3 = baseExpression.getOperand3();
            if (operand1 != null) {
                self.setOperand1((Expression)operand1.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (operand2 != null) {
                self.setOperand2((Expression)operand2.getImpl().
                        bind(templateParameters, templateArguments));
            }
            if (operand3 != null) {
                self.setOperand3((Expression)operand3.getImpl().
                        bind(templateParameters, templateArguments));
            }
        }
    }
    
} // ConditionalTestExpressionImpl
