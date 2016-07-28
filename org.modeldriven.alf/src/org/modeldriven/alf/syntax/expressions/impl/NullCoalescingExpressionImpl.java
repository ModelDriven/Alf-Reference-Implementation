/*******************************************************************************
 * Copyright 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;
import org.modeldriven.alf.syntax.expressions.AssignmentExpression;
import org.modeldriven.alf.syntax.expressions.ConditionalTestExpression;
import org.modeldriven.alf.syntax.expressions.EqualityExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.NameExpression;
import org.modeldriven.alf.syntax.expressions.NameLeftHandSide;
import org.modeldriven.alf.syntax.expressions.NullCoalescingExpression;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.expressions.SequenceConstructionExpression;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.impl.ClassifierDefinitionImpl;

public class NullCoalescingExpressionImpl extends ExpressionImpl {

    private Expression operand1 = null;
    private Expression operand2 = null;
    
    private ConditionalTestExpression conditionalTestExpression = null;

    public NullCoalescingExpressionImpl(NullCoalescingExpression self) {
        super(self);
    }
    
    @Override
    public NullCoalescingExpression getSelf() {
        return (NullCoalescingExpression)this.self;
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

    /**
     * If one of the operand expressions of a null-coalescing expression is
     * identically null (untyped with multiplicity 0..0), then the type of the
     * null-coalescing expression is the same as the type of the other operand
     * expressions. Otherwise, the type of a null-coalescing expression is the
     * effective common ancestor of the types of its operands, if one exists,
     * and empty, if it does not.
     */
    @Override
    protected ElementReference deriveType() {
        NullCoalescingExpression self = this.getSelf();
        this.getAssignmentAfterMap(); // Force computation of assignments.
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        if (operand1 == null || operand2 == null) {
            return null;
        } else if (operand1.getImpl().isNull()) {
            return operand2.getType();
        } else if (operand2.getImpl().isNull()) {
            return operand1.getType();
        } else {
            return ClassifierDefinitionImpl.commonAncestor
                        (operand1.getType(), operand2.getType());
        }
    }

    /**
     * The multiplicity lower bound of a null-coalescing expression is the
     * multiplicity lower bound of its first operand expression, if that is
     * greater than 0; otherwise it is 1, if the multiplicity lower bound of its
     * second operand expression is greater than 0; otherwise, it is 0.
     */
    @Override
    protected Integer deriveLower() {
        NullCoalescingExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        int lower1 = operand1 == null? 0: operand1.getLower();
        int lower2 = operand2 == null? 0: operand2.getLower();
        return lower1 > 0? lower1:
               lower2 > 0? 1: 0;
    }

    /**
     * The multiplicity upper bound of a null-coalescing expression is the
     * maximum of the multiplicity upper bounds of its operand expressions.
     */
    @Override
    protected Integer deriveUpper() {
        NullCoalescingExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        int upper1 = operand1 == null? 0: operand1.getUpper();
        int upper2 = operand2 == null? 0: operand2.getUpper();
        return upper1 < 0? upper1:
               upper2 < 0? upper2:
               upper1 > upper2? upper1: upper2;
    }
    
    /*
     * Derivations
     */
    
    public boolean nullCoalescingExpressionTypeDerivation() {
        this.getSelf().getType();
        return true;
    }

    public boolean nullCoalescingExpressionLowerDerivation() {
        this.getSelf().getLower();
        return true;
    }

    public boolean nullCoalescingExpressionUpperDerivation() {
        this.getSelf().getUpper();
        return true;
    }
    
    /*
     * Constraints
     */
    
    /**
     * The assignments before the first operand expression of a null-coalescing
     * expression are the same as those before the null-coalescing expression.
     * The assignments before the second operand expression are the same as
     * those after the first operand expression.
     **/
    public boolean nullCoalescingExpressionAssignmentsBefore() {
        // Note: This is handled by updateAssignments.
        return true;
    }
         
    /*
     * Helper Methods
     */
    
    /**
     * If a null-coalescing expression has one operand expression, then the
     * assignments after the null-coalescing expression are the same as the
     * assignments after that operand expression. If a null-coalescing expression
     * has two operand expressions, then, if a name has the same assigned source
     * after the second operand expression as before it, that is its assigned
     * source after the null-coalescing expression. Otherwise, its assigned
     * source after the null-coalescing expression is the null-coalescing
     * expression. If a name is unassigned before the second operand expression
     * but assigned after it, then it has a multiplicity lower bound of 0 after
     * the null-coalescing expression.
     */
    @Override
    public Map<String, AssignedSource> updateAssignmentMap() {
        NullCoalescingExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        Map<String, AssignedSource> assignmentsBefore = this.getAssignmentBeforeMap();
        Map<String, AssignedSource> assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsBefore);
        
        if (operand1 != null) {
            operand1.getImpl().setAssignmentBefore(assignmentsBefore);
            assignmentsAfter = operand1.getImpl().getAssignmentAfterMap();
        }
        if (operand2 != null) {
            operand2.getImpl().setAssignmentBefore(assignmentsAfter);
            assignmentsAfter = new HashMap<String, AssignedSource>(assignmentsAfter);
            for (AssignedSource assignment: operand2.getImpl().getNewAssignments()) {
                String name = assignment.getName();
                AssignedSource newAssignment = AssignedSourceImpl.makeAssignment(assignment);
                newAssignment.setSource(self);
                if (!assignmentsBefore.containsKey(name)) {
                    newAssignment.setLower(0);
                }
                assignmentsAfter.put(name, newAssignment);
            }
        }
        return assignmentsAfter;
    }
    
    public ConditionalTestExpression getConditionalTestExpression() {
        if (this.conditionalTestExpression == null) {
            NullCoalescingExpression self = this.getSelf();
            Expression operand1 = self.getOperand1();
            Expression operand2 = self.getOperand2();
            
            QualifiedName target = new QualifiedName().getImpl().addName("");
            
            NameLeftHandSide lhs = new NameLeftHandSide();
            lhs.setTarget(target);
            
            AssignmentExpression assignmentExpression = new AssignmentExpression();
            assignmentExpression.setLeftHandSide(lhs);
            assignmentExpression.setRightHandSide(operand1);
            assignmentExpression.setOperator("=");
            assignmentExpression.getImpl().setAssignmentBefore(
                    operand1.getImpl().getAssignmentBeforeMap());
            
            SequenceConstructionExpression nullExpression =
                    SequenceConstructionExpressionImpl.makeNull();
            
            EqualityExpression testExpression = new EqualityExpression();
            testExpression.setOperand1(assignmentExpression);
            testExpression.setOperand2(nullExpression);
            testExpression.setOperator("!=");
            
            NameExpression nameExpression = new NameExpression();
            nameExpression.setName(target);
            nameExpression.getImpl().setAssignmentBefore(
                    assignmentExpression.getImpl().getAssignmentAfterMap());
            
            this.conditionalTestExpression = new ConditionalTestExpression();
            this.conditionalTestExpression.setOperand1(testExpression);
            this.conditionalTestExpression.setOperand2(nameExpression);
            this.conditionalTestExpression.setOperand3(operand2);
        }
        return this.conditionalTestExpression;
    }
    
    @Override
    public void setCurrentScope(NamespaceDefinition currentScope) {
        NullCoalescingExpression self = this.getSelf();
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
        if (base instanceof NullCoalescingExpression) {
            NullCoalescingExpression self = this.getSelf();
            NullCoalescingExpression baseExpression = (NullCoalescingExpression)base;
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
        }
    }
    
}
