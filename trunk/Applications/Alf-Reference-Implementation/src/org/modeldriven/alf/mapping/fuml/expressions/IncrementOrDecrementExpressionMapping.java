
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.IncrementOrDecrementExpression;
import org.modeldriven.alf.syntax.expressions.LeftHandSide;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public class IncrementOrDecrementExpressionMapping extends ExpressionMapping {
    
    private CallBehaviorAction action = null;
    private ActivityNode resultSource = null;
    private ActivityNode assignedValueSource = null;
    
    private Expression operandExpression = null;
    
    /**
     * An increment or decrement expression is mapped to a call behavior action
     * for the + function (for increment) or the - function (for decrement) from
     * the library package Alf::Library::PrimitiveBehaviors::IntegerFunctions.
     * The second argument input pin of the call behavior action is connected by
     * an object flow to the result output pin of a value specification action
     * for the value 1. The result output pin of the call behavior action is
     * connected by an object flow to a fork node, which acts as the the source
     * element when the expression is an assigned source.
     * 
     * 2. The operand is mapped first considered as an effective argument
     * expression. If the increment or decrement expression is a prefix
     * expression, then the result source element of this mapping is connected
     * by an object flow to the first argument input pin of the call behavior
     * action and the assigned source element is also the result element for the
     * expression. If it is a postfix expression, then the result source element
     * of the operand expression mapping is first connected to a fork node and
     * then that is connected to the argument input pin, and the fork node is
     * the result source element for the expression.
     * 
     * 3. The operand is also mapped as a left-hand side to which is assigned
     * the result of the call behavior action.
     * 
     * 4. If the operand has an index, then the index expression is only mapped
     * once. The result source element of the index expression is connected by
     * an object flow to a fork node, which is used as the source for the index
     * value in the mapping of the operand expression both as an argument and as
     * the left hand side of the assignment.
     */
    
    public void mapAction() throws MappingError {
        IncrementOrDecrementExpression expression = 
            this.getIncrementOrDecrementExpression();
        LeftHandSide operand = expression.getOperand();
        
        Behavior behavior = getBehavior("++".equals(expression.getOperator())?
                RootNamespace.getIntegerFunctionPlus():
                RootNamespace.getIntegerFunctionMinus());
        this.action = this.graph.addCallBehaviorAction(behavior);       
        ValueSpecificationAction valueAction = 
            this.graph.addNaturalValueSpecificationAction(1);
        this.graph.addObjectFlow(
                valueAction.result, 
                this.action.argument.get(1));

        this.operandExpression = operand.getImpl().getExpression();
        FumlMapping mapping = this.fumlMap(operandExpression);
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping operand as expression: " + 
                    mapping.getErrorMessage());
        } else {
            ExpressionMapping operandMapping = (ExpressionMapping)mapping;
            this.graph.addAll(operandMapping.getGraph());

            mapping = this.fumlMap(operand);
            if (!(mapping instanceof LeftHandSideMapping)) {
                this.throwError("Error mapping operand as left-hand side: " + 
                        mapping.getErrorMessage());
            } else {
                LeftHandSideMapping lhsMapping = (LeftHandSideMapping)mapping;
                lhsMapping.setRhsUpper(1);
                lhsMapping.setIndexSource(operandMapping.getIndexSource());
                lhsMapping.setObjectSource(operandMapping.getObjectSource());
                this.graph.addAll(lhsMapping.getGraph());
                this.graph.addObjectFlow(
                        this.action.result.get(0),
                        lhsMapping.getAssignmentTarget());
                
                ActivityNode operandResultSource = operandMapping.getResultSource();
                if (expression.getIsPrefix()) {
                    this.graph.addObjectFlow(
                            operandResultSource, this.action.argument.get(0));
                    this.resultSource = lhsMapping.getResultSource();
                } else {
                    ForkNode forkNode = this.graph.addForkNode(
                            "Fork(" + operandResultSource.name + ")");
                    this.graph.addObjectFlow(operandResultSource, forkNode);
                    this.graph.addObjectFlow(
                            forkNode, this.action.argument.get(0));
                    this.resultSource = forkNode;
                }
                
                this.assignedValueSource = lhsMapping.getAssignedValueSource();
            }
        }        
    }
    
    public CallBehaviorAction getAction() throws MappingError {
        if (this.action == null) {
            this.mapAction();
            this.mapTo(this.action);
        }
        return this.action;
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        this.getAction();
        return this.resultSource;
    }
    
    @Override 
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        this.getAction();
        return this.assignedValueSource;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getAction();
        return super.getGraph();
    }

	public IncrementOrDecrementExpression getIncrementOrDecrementExpression() {
		return (IncrementOrDecrementExpression) this.getSource();
	}
	
	public void print(String prefix) {
	    super.print(prefix);
	    
	    IncrementOrDecrementExpression expression = 
	        this.getIncrementOrDecrementExpression();
	    System.out.println(prefix + " action: " + this.action);
	    
	    LeftHandSide lhs = expression.getOperand();
	    if (lhs != null) {
	        System.out.println(prefix + " operand:");
	        Mapping mapping = lhs.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	    
	    if (this.operandExpression != null) {
	        System.out.println(prefix + " operandExpression:");
	        Mapping mapping = operandExpression.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}

} // IncrementOrDecrementExpressionMapping
