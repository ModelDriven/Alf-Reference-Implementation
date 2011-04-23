
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

import java.util.Map;

/**
 * A binary expression with a conditional logical expression, for which the
 * evaluation of the second operand expression is conditioned on the result of
 * evaluating the first operand expression.
 **/

public class ConditionalLogicalExpressionImpl extends BinaryExpressionImpl {

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
	    return RootNamespace.getBooleanType();
	}
	
	/**
	 * A conditional logical expression has a multiplicity lower bound of 0 if
	 * the lower bound if either operand expression is 0 and 1 otherwise.
	 **/
	@Override
	protected Integer deriveLower() {
	    ConditionalLogicalExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    Expression operand2 = self.getOperand2();
	    return operand1 != null && operand1.getLower() == 0 ||
	           operand2 != null && operand2.getLower() == 0? 0: 1;
	}
	
	/**
	 * A conditional logical expression has a multiplicity upper bound of 1.
	 **/
	@Override
	protected Integer deriveUpper() {
	    return 1;
	}
	
	/*
	 * Derivations
	 */
	
	public boolean conditionalLogicalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean conditionalLogicalExpressionLower() {
		return true;
	}

	public boolean conditionalLogicalExpressionUpper() {
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The operands of a conditional logical expression must have type Boolean.
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
	 * same as those after the first operand expression.
	 **/
	public Boolean validateAssignments() {
	    this.getSelf().getAssignmentAfter(); // Force computation of assignments.
		return true;
	} // validateAssignments

	/**
	 * If a name has the same assigned source after the second operand
	 * expression as before it, then that is its assigned source after the
	 * conditional logical expression. If a name is unassigned before the second
	 * operand expression, then it is considered unassigned after the
	 * conditional logical expression, even if it has an assigned source after
	 * the second operand expression. Otherwise its assigned source after the
	 * conditional logical expression is the conditional logical expression
	 * itself.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
        ConditionalLogicalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        Map<String, AssignedSource> assignmentsBefore = self.getImpl().getAssignmentBeforeMap();
        Map<String, AssignedSource> assignmentsAfter = assignmentsBefore;
        if (operand1 != null) {
            operand1.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsAfter = operand1.getImpl().getAssignmentAfterMap();
        }
        if (operand2 != null) {
            operand2.getImpl().setAssignmentBefore(assignmentsAfter);
            for (AssignedSource assignment: operand2.getImpl().getNewAssignments()) {
                String name = assignment.getName();
                if (assignmentsBefore.containsKey(name)) {
                    AssignedSource newAssignment = AssignedSourceImpl.makeAssignment(assignment);
                    newAssignment.setSource(self);
                    assignmentsAfter.put(name, newAssignment);
                }
            }
        }
        return assignmentsAfter;
	} // updateAssignments

} // ConditionalLogicalExpressionImpl
