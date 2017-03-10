/*******************************************************************************
 * Copyright 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ParsedElement;
import org.modeldriven.alf.syntax.expressions.impl.NullCoalescingExpressionImpl;

public class NullCoalescingExpression extends BinaryExpression {
    
    public NullCoalescingExpression() {
        this.impl = new NullCoalescingExpressionImpl(this);
    }
    
    public NullCoalescingExpression(Parser parser) {
        this();
        Token token = parser.getToken(0);
        if (token.next != null) {
            token = token.next;
        }
        this.setParserInfo(parser.getFileName(), token.beginLine,
                token.beginColumn);        
    }
    
    public NullCoalescingExpression(ParsedElement element) {
        this();
        this.setParserInfo(element.getFileName(), element.getLine(), element
                .getColumn());
    }
    
    @Override
    public NullCoalescingExpressionImpl getImpl() {
        return (NullCoalescingExpressionImpl) this.impl;
    }
    
    public Expression getOperand1() {
        return this.getImpl().getOperand1();
    }

    public void setOperand1(Expression operand1) {
        this.getImpl().setOperand1(operand1);
    }

    public Expression getOperand2() {
        return this.getImpl().getOperand2();
    }

    public void setOperand2(Expression operand2) {
        this.getImpl().setOperand2(operand2);
    }
    
    /**
     * If one of the operand expressions of a null-coalescing expression is
     * identically null (untyped with multiplicity 0..0), then the type of the
     * null-coalescing expression is the same as the type of the other operand
     * expressions. Otherwise, the type of a null-coalescing expression is the
     * effective common ancestor of the types of its operands, if one exists,
     * and empty, if it does not.
     */
    public boolean nullCoalescingExpressionTypeDerivation() {
        return this.getImpl().nullCoalescingExpressionTypeDerivation();
    }

    /**
     * The multiplicity lower bound of a null-coalescing expression is the
     * multiplicity lower bound of its first operand expression, if that is
     * greater than 0; otherwise it is 1, if the multiplicity lower bound of its
     * second operand expression is greater than 0; otherwise, it is 0.
     */
    public boolean nullCoalescingExpressionLowerDerivation() {
        return this.getImpl().nullCoalescingExpressionLowerDerivation();
    }

    /**
     * The multiplicity upper bound of a null-coalescing expression is the
     * maximum of the multiplicity upper bounds of its operands.
     */
    public boolean nullCoalescingExpressionUpperDerivation() {
        return this.getImpl().nullCoalescingExpressionUpperDerivation();
    }
    
   /**
    * The assignments before the first operand expression of a null-coalescing
    * expression are the same as those before the null-coalescing expression.
    * The assignments before the second operand expression are the same as
    * those after the first operand expression.
    **/
    public boolean nullCoalescingExpressionAssignmentsBefore() {
        return this.getImpl().nullCoalescingExpressionAssignmentsBefore();
    }
    
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
    public Collection<AssignedSource> updateAssignments() {
        return this.getImpl().updateAssignments();
    }
    
    /**
     * The minimum lower bound of an operand of a null-coalescing expression
     * is 0.
     */
    @Override
    public Integer minLowerBound() {
        return this.getImpl().minLowerBound();
    }
    
    /**
     * The maximum upper bound of an operand of a null-coalescing expression
     * is * (unbounded).
     */
    @Override
    public Integer maxUpperBound() {
        return this.getImpl().minLowerBound();
    }
    
    @Override
    public void _deriveAll() {
        super._deriveAll();
    }
    
    @Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
        super.checkConstraints(violations);
        if (!this.nullCoalescingExpressionTypeDerivation()) {
            violations.add(new ConstraintViolation(
                    "nullCoalescingExpressionTypeDerivation", this));
        }
        if (!this.nullCoalescingExpressionLowerDerivation()) {
            violations.add(new ConstraintViolation(
                    "nullCoalescingExpressionLowerDerivation", this));
        }
        if (!this.nullCoalescingExpressionUpperDerivation()) {
            violations.add(new ConstraintViolation(
                    "nullCoalescingExpressionUpperDerivation", this));
        }
        if (!this.nullCoalescingExpressionAssignmentsBefore()) {
            violations.add(new ConstraintViolation(
                    "nullCoalescingExpressionAssignmentsBefore", this));
        }
    }
    
}
