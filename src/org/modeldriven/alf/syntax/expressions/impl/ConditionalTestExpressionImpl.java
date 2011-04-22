
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;

import java.util.Collection;
import java.util.HashMap;
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
	    ElementReference type2 = operand2 == null? null: operand2.getType();
	    ElementReference type3 = operand3 == null? null: operand3.getType();
	    return ClassifierDefinitionImpl.commonAncestor(type2, type3);
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
		return operand1 != null && 
		            operand1.getType().getImpl().isBoolean() &&
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
            this.putAssignments(assignmentsAfter, operand2.getImpl().getNewAssignments());
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
	            if (!oldType.getImpl().equals(newType)) {
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

} // ConditionalTestExpressionImpl
