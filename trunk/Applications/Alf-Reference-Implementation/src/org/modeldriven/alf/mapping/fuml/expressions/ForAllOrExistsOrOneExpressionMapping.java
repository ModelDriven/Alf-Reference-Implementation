
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.expressions.SequenceExpansionExpressionMapping;

import org.modeldriven.alf.syntax.expressions.ForAllOrExistsOrOneExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.IntermediateActions.TestIdentityAction;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class ForAllOrExistsOrOneExpressionMapping extends
		SequenceExpansionExpressionMapping {

    /**
     * 1. A forall expression is mapped the same as a reject expression, except
     * that the output expansion node of the expansion region is connected by an
     * object flow to a call behavior action for the library isEmpty function.
     * The result output pin of the call behavior action is the result source
     * element for the forAll expression.
     * 
     * 2. An exists expression is mapped the same as a select expression, with
     * the addition that, inside the expansion region, the decision node is not
     * directly connected to the output expansion node but, rather, is connected
     * to a fork node that is connected both to the output expansion node and to
     * an activity final node. Further, the output expansion node of the
     * expansion region is connected by an object flow to a call behavior action
     * for the library notEmpty function. The result output pin of the call
     * behavior action is the result source element for the exists expression.
     * 
     * NOTE. The inclusion of an activity final node within the expansion region
     * is intended to terminate the region as soon as an element is found for
     * which the Boolean expression is true. (Despite its name, an activity
     * final node within a structured node such as an expansion region only
     * terminates that structured node.)
     * 
     * 3. A one expression is mapped the same as a select expression, except
     * that the output expansion node of the expansion region is connected by an
     * object flow to a call behavior action for the library size function. The
     * result output pin of the call behavior action is then connected by an
     * object flow to the input pin of a test identity action whose other input
     * pin is connected to a value specification action for the value 1. The
     * result output pin of the test identity action is the result source
     * element of the one expression.
     */
    
    @Override
    protected ActivityNode mapNestedGraph(
            String operation,
            ActivityNode variableSource, 
            ActivityGraph nestedGraph,
            ActivityNode resultNode) throws MappingError {
        ActivityNode resultSource = super.mapNestedGraph(
                operation, variableSource, nestedGraph, resultNode);
        
        if (this.isTerminated(operation)) {
            resultSource = this.addTermination(nestedGraph, resultSource);
        }
        return resultSource;
    }
    
    @Override
    protected boolean isSelectLike(String operation) {
        return "exists".equals(operation) || "one".equals(operation);
    }
    
    protected boolean isTerminated(String operation) {
        // NOTE: Including a terminating activity final node in the case of
        // a forAll expression is an optimization not currently in the spec.
        return "forAll".equals(operation) || "exists".equals(operation);
    }
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        String operation = this.getForAllOrExistsOrOneExpression().getOperation();
        
        if ("forAll".equals(operation)) {
            this.addBehaviorCall(RootNamespace.getSequenceFunctionIsEmpty());
        } else if ("exists".equals(operation)) {
            this.addBehaviorCall(RootNamespace.getSequenceFunctionNotEmpty());
        } else if ("one".equals(operation)) {
            this.addBehaviorCall(RootNamespace.getListFunctionSize());
            ValueSpecificationAction valueAction = 
                this.graph.addNaturalValueSpecificationAction(1);
            TestIdentityAction testAction = 
                this.graph.addTestIdentityAction("Test(=1)");
            this.graph.addObjectFlow(this.resultSource, testAction.first);
            this.graph.addObjectFlow(valueAction.result, testAction.second);
            this.resultSource = testAction.result;
        }
    }
    
	public ForAllOrExistsOrOneExpression getForAllOrExistsOrOneExpression() {
		return (ForAllOrExistsOrOneExpression) this.getSource();
	}

} // ForAllOrExistsOrOneExpressionMapping
