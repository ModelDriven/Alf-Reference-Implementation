
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.UnaryExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.Action;
import org.modeldriven.alf.uml.CallBehaviorAction;
import org.modeldriven.alf.uml.ActivityNode;

public abstract class UnaryExpressionMapping extends ExpressionMapping {

    protected Action action = null;
    protected ActivityNode resultSource = null;

    /**
     * Boolean Unary Expressions
     * 
     * 1. A Boolean unary expression with a Boolean negation operator is mapped
     * as the equivalent behavior invocation for the function Alf::Library::
     * PrimitiveBehaviors::BooleanFunctions::'!' on the operand expression.
     * 
     * BitString Unary Expressions
     * 
     * 2. A BitString unary expression with a BitString negation operator is
     * mapped as the equivalent behavior invocation for the function
     * Alf::Library::PrimitiveBehaviors::BitStringFunctions::'~' on the
     * operand expression. Note that this includes the possibility of bit string
     * conversion on the operand expression.
     * 
     * Numeric Unary Expressions
     * 
     * 3. A numeric unary expression with a plus operator is mapped as its
     * operand expression. A numeric unary expression with a minus operator is
     * mapped as the equivalent behavior invocation for the function
     * Alf::Library::PrimitiveBehaviors::IntegerFunctions::Neg on the operand
     * expression.
     * 
     */
    
    // Cast and isolation expressions are handled in separate classes.
    // ClassificationExpressionMapping is also a subclass of UnaryExpressionMapping.
    
    // NOTE: This is overridden in BitStringUnaryExpressionMapping to implement
    // bit string conversion.
    protected ActivityNode mapOperand() throws MappingError {
        UnaryExpression expression = this.getUnaryExpression();
        FumlMapping mapping = this.fumlMap(expression.getOperand());
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping operand expression: " + 
                    mapping.getErrorMessage());
        } 
        ExpressionMapping operandMapping = (ExpressionMapping)mapping;
        this.graph.addAll(operandMapping.getGraph());
        return operandMapping.getResultSource();
    }
    
    protected void map() throws MappingError {
        UnaryExpression expression = this.getUnaryExpression();
        ActivityNode operandResultSource = this.mapOperand();
        ElementReference operatorFunction =
            this.getOperatorFunction(expression.getOperator());
        if (operatorFunction == null) {
            this.resultSource = operandResultSource;
        } else {
            CallBehaviorAction callAction = 
                this.graph.addCallBehaviorAction(getBehavior(operatorFunction));
            this.graph.addObjectFlow(
                    operandResultSource, callAction.getArgument().get(0));
            this.action = callAction;
            this.resultSource = callAction.getResult().get(0);
        }
    }
    
    private ElementReference getOperatorFunction(String operator) {
        switch (operator.charAt(0)) {
            case '!': return RootNamespace.getBooleanFunctionNot();
            case '~': return RootNamespace.getBitStringFunctionComplement();
            case '-': return RootNamespace.getIntegerFunctionNeg();
            default: return null;
        }
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

	public UnaryExpression getUnaryExpression() {
		return (UnaryExpression) this.getSource();
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
	    
	    UnaryExpression expression = this.getUnaryExpression();
	    Expression operand = expression.getOperand();
	    if (operand != null) {
	        System.out.println(prefix + " operand:");
	        Mapping mapping = operand.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}

} // UnaryExpressionMapping
