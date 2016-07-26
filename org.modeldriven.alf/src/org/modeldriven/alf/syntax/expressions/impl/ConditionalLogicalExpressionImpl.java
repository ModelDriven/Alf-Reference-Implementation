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
import java.util.Map;

/**
 * A binary expression with a conditional logical expression, for which the
 * evaluation of the second operand expression is conditioned on the result of
 * evaluating the first operand expression.
 **/

public class ConditionalLogicalExpressionImpl extends BinaryExpressionImpl {
    
    private ConditionalTestExpression conditionalTestExpression = null;

	public ConditionalLogicalExpressionImpl(ConditionalLogicalExpression self) {
		super(self);
	}

	@Override
	public ConditionalLogicalExpression getSelf() {
		return (ConditionalLogicalExpression) this.self;
	}

	/**
	 * A conditional logical expression has type Boolean.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getRootScope().getBooleanType();
	}
	
	/*
	 * Derivations
	 */
	
	public boolean conditionalLogicalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean conditionalLogicalExpressionLowerDerivation() {
	    this.getSelf().getLower();
		return true;
	}

	public boolean conditionalLogicalExpressionUpperDerivation() {
	    this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
     * The operands of a conditional logical expression must have a type that 
     * conforms to type Boolean.
	 **/
	public boolean conditionalLogicalExpressionOperands() {
        ConditionalLogicalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        ElementReference type1 = operand1 == null? null: operand1.getType();
        ElementReference type2 = operand1 == null? null: operand2.getType();
        return type1 != null && type1.getImpl().isBoolean() &&
               type2 != null && type2.getImpl().isBoolean();
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * The assignments before the first operand expression of a conditional
	 * logical expression are the same as those before the conditional logical
	 * expression. The assignments before the second operand expression are the
	 * same as those after the first operand expression, adjusted for known
	 * nulls and non-nulls based on the first operand expression being true,
	 * for a conditional-and expression, or false, for a conditional-or
	 * expression.
	 **/
	public Boolean validateAssignments() {
	    this.getSelf().getAssignmentAfter(); // Force computation of assignments.
		return true;
	} // validateAssignments

    /**
     * If a name has the same assigned source after the second operand
     * expression as before it, then that is its assigned source after the
     * conditional logical expression. Otherwise its assigned source after the
     * conditional logical expression is the conditional logical expression
     * itself. If a name is unassigned before the second operand expression but
     * assigned after it, then it has a multiplicity lower bound of 0 after the
     * conditional logical expression.
     **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
        ConditionalLogicalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        String operator = self.getOperator();
        Map<String, AssignedSource> assignmentsBefore = self.getImpl().getAssignmentBeforeMap();
        Map<String, AssignedSource> assignmentsAfter = assignmentsBefore;
        if (operand1 != null) {
            operand1.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsAfter = operand1.getImpl().getAssignmentAfterMap();
        }
        if (operand2 != null) {
            Map<String, AssignedSource> assignmentsBefore2 =
                operand1 == null? assignmentsAfter:
                operand1.getImpl().adjustAssignments(
                    new HashMap<String, AssignedSource>(assignmentsAfter),
                    "&&".equals(operator));
            operand2.getImpl().setAssignmentBefore(assignmentsBefore2);
            Collection<AssignedSource> newAssignments = operand2.getImpl().getNewAssignments();
            if (!newAssignments.isEmpty()) {
                assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsAfter);
                for (AssignedSource assignment: newAssignments) {
                    String name = assignment.getName();
                    AssignedSource oldAssignment = assignmentsAfter.get(name);
                    AssignedSource newAssignment = AssignedSourceImpl.makeAssignment(
                            oldAssignment == null? assignment: oldAssignment);
                    if (oldAssignment == null || assignment.getLower() == 0) {
                        newAssignment.setLower(0);
                    }
                    newAssignment.setSource(self);
                    assignmentsAfter.put(name, newAssignment);
                }
            }
        }
        return assignmentsAfter;
	} // updateAssignments
	
    /**
     * If the expression is a conditional-and expression and the truth condition
     * is true, then check for known nulls and non-nulls based on both of the
     * operand expressions being true. If the expression is a conditional-or
     * expression, then check for known nulls and non-nulls based on both of the
     * operand expressions being false.
     */
	@Override
	public Map<String, AssignedSource> adjustAssignments(Map<String, AssignedSource> assignments, boolean condition) {
	    ConditionalLogicalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
	    String operator = self.getOperator();
	    if (operand2 != null) {
	        if (condition && "&&".equals(operator)) {
	            self.getAssignmentAfter(); // Force updates to assignments.
	            return operand2.getImpl().adjustAssignments(
	                    operand1.getImpl().adjustAssignments(assignments, true), 
	                    true);
	        } else if (!condition && "||".equals(operator)) {
	            self.getAssignmentAfter(); // Force updates to assignments.
                return operand2.getImpl().adjustAssignments(
                        operand1.getImpl().adjustAssignments(assignments, false), 
                        false);
	        }
	    }
	    return assignments;
	}
	
	/**
     * A conditional-and expression is equivalent to a conditional-test
     * expression whose first two operand expressions are the same as those of
     * the conditional-and expression and whose third operand expression is
     * false.
     * 
     * A conditional-or expression is equivalent to a conditional-test
     * expression whose first and third operand expressions are the same as the
     * two operand expressions of the conditional-or expression and whose second
     * operand expression is true.
	 */
	public ConditionalTestExpression getConditionalTestExpression() {
	    if (this.conditionalTestExpression == null) {
	        ConditionalLogicalExpression self = this.getSelf();
	        Expression operand1 = self.getOperand1();

	        boolean isAnd = "&&".equals(self.getOperator());
	        BooleanLiteralExpression literalExpression = 
	            new BooleanLiteralExpression();
	        literalExpression.setImage(isAnd? "false": "true");
	        literalExpression.getImpl().setAssignmentBefore(
	                operand1.getImpl().getAssignmentAfterMap());

	        this.conditionalTestExpression = new ConditionalTestExpression();
	        this.conditionalTestExpression.setOperand1(operand1);
	        if (isAnd) {
	            this.conditionalTestExpression.setOperand2(self.getOperand2());
	            this.conditionalTestExpression.setOperand3(literalExpression);
	        } else {
	            this.conditionalTestExpression.setOperand2(literalExpression);
	            this.conditionalTestExpression.setOperand3(self.getOperand2());
	        }
	        
	    }
        return this.conditionalTestExpression;
	}

} // ConditionalLogicalExpressionImpl
