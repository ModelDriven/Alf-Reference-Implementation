
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
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
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.ActivityDefinitionMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.ExtentOrExpression;
import org.modeldriven.alf.syntax.expressions.SequenceReductionExpression;

import fUML.Syntax.Actions.CompleteActions.ReduceAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class SequenceReductionExpressionMapping extends ExpressionMapping {
    
    private ReduceAction action = null;

	/**
     * A sequence reduction expression is mapped to a reduce action with the
     * named behavior as the reducer. The collection input pin is the target of
     * an object flow from the result source element of the mapping of the input
     * expression. The result output pin of the reduce action is the result
     * source element for the reduction expression.
     */
    
    public void mapAction() throws MappingError {
        SequenceReductionExpression expression = 
            this.getSequenceReductionExpression();
        FumlMapping mapping = this.fumlMap(expression.getReferent());
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        if (!(mapping instanceof ActivityDefinitionMapping)) {
            this.throwError("Error mapping behavior " + 
                    expression.getBehaviorName() + ": " + 
                    mapping.getErrorMessage());
        } else {
            this.action = this.graph.addReduceAction(
                    ((ActivityDefinitionMapping)mapping).getBehavior(),
                    this.getType(),
                    expression.getIsOrdered());
            ExtentOrExpression primary = expression.getPrimary();
            mapping = this.fumlMap(primary == null? null: primary.getExpression());
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping primary expression: " + 
                        mapping.getErrorMessage());
            } else {
                ExpressionMapping expressionMapping = (ExpressionMapping)mapping;
                this.graph.addAll(expressionMapping.getGraph());
                this.graph.addObjectFlow(
                        expressionMapping.getResultSource(), 
                        this.action.collection);
            }
        }
    }
    
    public ReduceAction getAction() throws MappingError {
        if (this.action == null) {
            this.mapAction();
            this.mapTo(this.action);
        }
        return this.action;
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        return this.getAction().result;
    }

    @Override
	public ActivityGraph getGraph() throws MappingError {
	    this.getAction();
	    return super.getGraph();
	}

	public SequenceReductionExpression getSequenceReductionExpression() {
		return (SequenceReductionExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.action != null) {
	        System.out.println(prefix + " action: " + this.action);
	        System.out.println(prefix + " isOrdered: " + this.action.isOrdered);
	        System.out.println(prefix + " reducer:" + this.action.reducer);
	    }
	    
	    SequenceReductionExpression expression = 
	        this.getSequenceReductionExpression();	    
	    ExtentOrExpression primary = expression.getPrimary();
	    if (primary != null) {
	        Expression primaryExpression = primary.getExpression();
	        if (primaryExpression != null) {
	            System.out.println(prefix + " primary:");
	            Mapping mapping = primaryExpression.getImpl().getMapping();
	            if (mapping != null) {
	                mapping.printChild(prefix);
	            }
	        }
	    }
	}

} // SequenceReductionExpressionMapping
