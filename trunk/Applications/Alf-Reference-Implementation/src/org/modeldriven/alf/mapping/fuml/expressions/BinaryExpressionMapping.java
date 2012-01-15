
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.BinaryExpression;
import org.modeldriven.alf.syntax.expressions.Expression;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

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
                    callAction.argument.get(0));
            this.graph.addObjectFlow(
                    operand2Result, 
                    callAction.argument.get(1));
            this.action = callAction;
            this.resultSource = callAction.result.get(0);
        }
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
                        ((CallBehaviorAction)this.action).behavior);
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
