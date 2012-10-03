
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.BinaryExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.CallBehaviorAction;
import org.modeldriven.alf.uml.ActivityNode;

public abstract class BinaryExpressionMapping extends ExpressionMapping {
    
    protected Action action = null;
    protected ActivityNode resultSource = null;

    /**
     * Arithmetic Expressions
     * 
     * 1. An arithmetic expression is mapped as a behavior invocation for the
     * corresponding primitive behavior from the
     * Alf::Library::PrimitiveBehaviors package.
     * 
     * Shift Expressions
     * 
     * 2. A shift expression is mapped as a behavior invocation for the
     * corresponding primitive behavior from the
     * Alf::Library::PrimitiveBehaviors package. Note that this includes the
     * possibility of performing bit string conversion on the first operand.
     * 
     * Relational Expressions
     * 
     * 3. A relational expression is mapped as a behavior invocation for the
     * corresponding primitive behavior from the
     * Alf::Library::PrimitiveBehaviors package.
     * 
     * Logical Expressions
     * 
     * 4. A logical expression is mapped as a behavior invocation for the
     * corresponding primitive behavior from the
     * Alf::Library::PrimitiveBehaviors package. Note that this includes the
     * possibility of applying bit string conversion to one or both operands, if
     * the operator is bit-wise.
     */
    
    // Classification, equality and conditional logical expressions have
    // specialized mappings.
    
    // ClassificationExpressionMapping is a subclass of UnaryExpressionMapping.
    // ConditionalLogicalExpressionMapping is a subclass of ConditionalTestMapping.
    
    protected ActivityNode mapOperand(Expression operand) throws MappingError {
        ExpressionMapping operandMapping = null;
        FumlMapping mapping = this.fumlMap(operand);
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping operand expression: " + 
                    mapping.getErrorMessage());
        } else {
            operandMapping = (ExpressionMapping)mapping;
            this.graph.addAll(operandMapping.getGraph());
        }
        
        ActivityNode resultSource = operandMapping.getResultSource();
        if (resultSource == null) {
            this.throwError("Operand mapping has no result source: " + operand);
        }
        
        return resultSource;
    }
    
    // NOTE: This is overridden in LogicalExpressionMapping and
    // ShiftExpressionMapping to implement bit string conversion.
    protected void mapOperator(
            String operator,
            ActivityNode operand1Result, 
            ActivityNode operand2Result) throws MappingError {
        ElementReference operatorFunction = this.getOperatorFunction(operator);
        if (operatorFunction == null) {
            this.resultSource = operand1Result;
        } else {
            CallBehaviorAction callAction = 
                this.graph.addCallBehaviorAction(getBehavior(operatorFunction));
            this.graph.addObjectFlow(
                    operand1Result, 
                    callAction.getArgument().get(0));
            this.graph.addObjectFlow(
                    operand2Result, 
                    callAction.getArgument().get(1));
            this.action = callAction;
            this.resultSource = callAction.getResult().get(0);
        }
    }
    
    // Used by subclasses.
    protected ActivityNode addBitStringConversion(ActivityNode operandResult) 
        throws MappingError {
        CallBehaviorAction callAction = this.graph.addCallBehaviorAction(
                getBehavior(RootNamespace.getBitStringFunctionToBitString()));
        this.graph.addObjectFlow(operandResult, callAction.getArgument().get(0));
        return callAction.getResult().get(0);
    }

    protected ElementReference getOperatorFunction(String operator) {
        return null;
    }

    protected void map() throws MappingError {
        BinaryExpression expression = this.getBinaryExpression();
        ActivityNode operand1Result = this.mapOperand(expression.getOperand1());
        ActivityNode operand2Result = this.mapOperand(expression.getOperand2());
        this.mapOperator(expression.getOperator(), operand1Result, operand2Result);
    }
    
    public Action getAction() throws MappingError {
        this.getResultSource();
        return this.action;
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        if (this.resultSource == null) {
            this.map();            
            this.mapTo(this.action);
        }
        return this.resultSource;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getAction();
        return super.getGraph();
    }

	public BinaryExpression getBinaryExpression() {
		return (BinaryExpression) this.getSource();
	}

    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        if (this.action != null) {
            System.out.println(prefix + " action:" + this.action);
            if (this.action instanceof CallBehaviorAction) {
                System.out.println(prefix + " behavior:" + 
                        ((CallBehaviorAction)this.action).getBehavior());
            }
        }
        
        if (this.resultSource != null) {
            System.out.println(prefix + " resultSource: " + this.resultSource);
        }
        
        BinaryExpression expression = this.getBinaryExpression();
        
        Expression operand1 = expression.getOperand1();
        if (operand1 != null) {
            System.out.println(prefix + " operand1:");
            Mapping mapping = operand1.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
        
        Expression operand2 = expression.getOperand2();
        if (operand2 != null) {
            System.out.println(prefix + " operand2:");
            Mapping mapping = operand2.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
    }

} // BinaryExpressionMapping
