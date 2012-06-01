
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.expressions.SequenceExpansionExpressionMapping;

import org.modeldriven.alf.syntax.expressions.IsUniqueExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.IntermediateActions.TestIdentityAction;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;

public class IsUniqueExpressionMapping extends
		SequenceExpansionExpressionMapping {

    /**
     * 1. An isUnique expression is mapped as a collect expression. The
     * expansion output node of the expansion region mapped from the collect
     * expression is connected by an object flow to a fork node which is then
     * connect by object flows to an input expansion node and an input pin of
     * another expansion region. The second expansion region is mapped similarly
     * to a forAll expression, with the condition that the count of each value
     * its sequence is 1. The result source element for the isUnique expression
     * is the result output pin of the isEmpty call on the output of the second
     * expansion region.
     */
    
    @Override
    protected ActivityNode mapNestedGraph(
            String operation,
            ActivityNode variableSource, 
            ActivityGraph nestedGraph,
            ActivityNode resultNode) throws MappingError {
        return resultNode;
    }
    
    @Override
    protected boolean isSelectLike(String operation) {
        return false;
    }
    
    @Override
    public void map() throws MappingError {
        super.map();

        ActivityGraph nestedGraph = new ActivityGraph();
        ActivityNode forkNode = 
            nestedGraph.addForkNode("Fork(" + this.resultSource.name + ")");
        // NOTE: A structured activity node is used here as the source of a
        // control flow to the next expansion region, in order to allow tokens
        // to be offered on both outgoing flows of the fork node before the
        // expansion region fires.
        ActivityNode forkStructureNode = this.graph.addStructuredActivityNode(
                "Node(" + forkNode.name + ")", nestedGraph.getModelElements());
        this.graph.addObjectFlow(this.resultSource, forkNode);
        
        // Map the test for count = 1.
        nestedGraph = new ActivityGraph();
        ForkNode variableSource = nestedGraph.addForkNode("Fork(each)");
        CallBehaviorAction callAction = nestedGraph.addCallBehaviorAction(
                getBehavior(RootNamespace.getSequenceFunctionCount()));
        ValueSpecificationAction valueAction = 
            nestedGraph.addNaturalValueSpecificationAction(1);
        TestIdentityAction testAction = nestedGraph.addTestIdentityAction("=1");
        
        nestedGraph.addObjectFlow(forkNode, callAction.argument.get(0));
        nestedGraph.addObjectFlow(variableSource, callAction.argument.get(1));
        nestedGraph.addObjectFlow(callAction.result.get(0), testAction.first);
        nestedGraph.addObjectFlow(valueAction.result, testAction.second);
        
        ActivityNode node = super.mapNestedGraph(
                "uniqueness", variableSource, nestedGraph, testAction.result);
        ActivityNode nestedResult = 
            this.addTermination(nestedGraph, node);
        
        // Create the expansion region for testing the count on each element.
        // NOTE: Object flow from forkNode to the first callAction.argument will
        // result in an input pin at the expansion region boundary.
        IsUniqueExpression expression = this.getIsUniqueExpression();
        ExpansionRegion region = this.graph.addExpansionRegion(
                "Uniqueness(" + expression.getClass().getSimpleName()+ 
                            "@" + expression.getId() + ")", 
                ExpansionKind.parallel, 
                nestedGraph.getModelElements(), 
                forkNode, variableSource, nestedResult);
        this.graph.addControlFlow(forkStructureNode, region);
        this.resultSource = region.outputElement.get(0);
        
        // Add the final check that there are no elements with a count != 1.
        this.addBehaviorCall(RootNamespace.getSequenceFunctionIsEmpty());
    }
    
	public IsUniqueExpression getIsUniqueExpression() {
		return (IsUniqueExpression) this.getSource();
	}

} // IsUniqueExpressionMapping
