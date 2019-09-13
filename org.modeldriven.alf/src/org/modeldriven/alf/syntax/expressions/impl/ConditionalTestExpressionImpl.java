/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
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
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        this.getAssignmentAfterMap(); // Force computation of assignments.
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
        this.getAssignmentAfterMap(); // Force computation of assignments.
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
        this.getAssignmentAfterMap(); // Force computation of assignments.
        Expression operand2 = self.getOperand2();
        Expression operand3 = self.getOperand3();
        int upper2 = operand2 == null? 0: operand2.getUpper();
        int upper3 = operand3 == null? 0: operand3.getUpper();
        return upper2 == -1 || upper3 != -1 && upper2 > upper3? upper2: upper3;
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
     * a type that conforms to type Boolean and have a multiplicity lower and upper 
     * bounds of 1.
	 **/
	public boolean conditionalTestExpressionCondition() {
        ConditionalTestExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        ElementReference type = operand1 == null? null: operand1.getType();
		return type == null || type.getImpl().isBoolean() &&
		            operand1.getLower() == 1 && operand1.getUpper() == 1;
	}

	/**
	 * The assignments before the first operand expression of a conditional-test
	 * expression are the same as those before the conditional-test expression.
	 * The assignments before the second and third operand expressions are the
	 * same as those after the first operand expression, adjusted for known
	 * null and non-null names from the first operand expression being true,
	 * for the second operand expression, or false, for the third operand
	 * expression.
	 **/
	public boolean conditionalTestExpressionAssignmentsBefore() {
	    // Note: This is handled by updateAssignments.
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
	 * source. If such a local name is defined in one operand expression but not
	 * the other, then it is adjusted to have multiplicity lower bound of 0 after
	 * the conditional test expression. If a local name has a new assignment after
	 * each of the second and third expressions, then, after the conditional-test
	 * expression, it has a type that is the effective common ancestor of its type
	 * after the second and third operand expressions, a multiplicity lower bound 
	 * that is the minimum of the lower bounds after the second and third operand
	 * expressions and a multiplicity upper bound that is the maximum of the upper
	 * bounds after the second and third expressions.
	 **/
	@Override
	public Map<String, AssignedSource> updateAssignmentMap() {
        ConditionalTestExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        Expression operand3 = self.getOperand3();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        
        if (operand1 != null) {
            operand1.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsBefore = operand1.getImpl().getAssignmentAfterMap();
        }
                
        Collection<AssignedSource> newAssignments2 = new ArrayList<AssignedSource>();
        Collection<AssignedSource> newAssignments3 = new ArrayList<AssignedSource>();
        
        if (operand2 != null) {
            Map<String, AssignedSource> assignmentsBefore2 = 
                operand1.getImpl().adjustAssignments(
                    new HashMap<String, AssignedSource>(assignmentsBefore), true);
            operand2.getImpl().setAssignmentBefore(assignmentsBefore2);
            newAssignments2 = operand2.getImpl().getNewAssignments();
        }
        
        if (operand3 != null) {
            Map<String, AssignedSource> assignmentsBefore3 = 
                operand1.getImpl().adjustAssignments(
                    new HashMap<String, AssignedSource>(assignmentsBefore), false);
            operand3.getImpl().setAssignmentBefore(assignmentsBefore3);
            newAssignments3 = operand3.getImpl().getNewAssignments();
        }
        
        return this.mergeAssignments(assignmentsBefore, newAssignments2, newAssignments3);
	} // updateAssignments
	
	private Map<String, AssignedSource> mergeAssignments(
	        Map<String, AssignedSource> assignmentsBefore, 
	        Collection<AssignedSource> newAssignments2,
	        Collection<AssignedSource> newAssignments3) {
	    ConditionalTestExpression self = this.getSelf();
	    
        Map<String, AssignedSource> newAssignmentsMap2 = 
                this.makeAssignmentMap(newAssignments2);
        Map<String, AssignedSource> newAssignmentsMap3 = 
                this.makeAssignmentMap(newAssignments3);
        Map<String, AssignedSource> assignmentsAfter =
                new HashMap<String, AssignedSource>(assignmentsBefore);
        
        Set<String> names = new HashSet<String>(newAssignmentsMap2.keySet());
        names.addAll(newAssignmentsMap3.keySet());
	    for (String name: names) {
	        AssignedSource assignmentBefore = assignmentsBefore.get(name);
            AssignedSource assignment2 = newAssignmentsMap2.get(name);
            AssignedSource assignment3 = newAssignmentsMap3.get(name);
            if (assignment2 == null) {
                assignment2 = assignmentBefore != null? assignmentBefore:
                    AssignedSourceImpl.makeAssignment(name, self, assignment3.getType(), 0, 0);
            }
            if (assignment3 == null) {
                assignment3 = assignmentBefore != null? assignmentBefore:
                    AssignedSourceImpl.makeAssignment(name, self, assignment2.getType(), 0, 0);
            }
            ElementReference type2 = assignment2.getType();
            ElementReference type3 = assignment3.getType();
            if (type2 != null && !type2.getImpl().equals(type3)) {
                assignment2.setType(ClassifierDefinitionImpl.commonAncestor(type2, type3));
            }
            int lower2 = assignment2.getLower();
            int upper2 = assignment2.getUpper();
            int lower3 = assignment3.getLower();
            int upper3 = assignment3.getUpper();
            if (lower3 < lower2) {
                assignment2.setLower(lower3);
            }
            if (upper3 == -1 || upper2 != -1 && upper2 < upper3) {
                assignment2.setUpper(upper3);
            }
	        assignmentsAfter.put(name, assignment2);
	    }
	    
	    return assignmentsAfter;
	}
	
	private Map<String, AssignedSource> makeAssignmentMap(Collection<AssignedSource> assignments) {
	    ConditionalTestExpression self = this.getSelf();
	    Map<String, AssignedSource> assignmentMap = new HashMap<String, AssignedSource>();
	    for (AssignedSource assignment: assignments) {
            AssignedSource newAssignment = AssignedSourceImpl.makeAssignment(assignment);
            newAssignment.setSource(self);
            assignmentMap.put(newAssignment.getName(), newAssignment);
	    }
	    return assignmentMap;
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
