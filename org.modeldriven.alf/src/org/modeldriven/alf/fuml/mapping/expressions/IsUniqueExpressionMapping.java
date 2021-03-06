/*******************************************************************************
 * Copyright 2011-2016 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.expressions.SequenceExpansionExpressionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.expressions.IsUniqueExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.*;

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
    public Classifier map() throws MappingError {
        Classifier argumentType = super.map();
        this.region.getOutputElement().get(0).setType(argumentType);

        ActivityGraph nestedGraph = this.createActivityGraph();
        ActivityNode forkNode = 
            nestedGraph.addForkNode("Fork(" + this.resultSource.getName() + ")");
        // NOTE: A structured activity node is used here as the source of a
        // control flow to the next expansion region, in order to allow tokens
        // to be offered on both outgoing flows of the fork node before the
        // expansion region fires.
        ActivityNode forkStructureNode = this.graph.addStructuredActivityNode(
                "Node(" + forkNode.getName() + ")", nestedGraph.getModelElements());
        this.graph.addObjectFlow(this.resultSource, forkNode);
        
        // Map the test for count = 1.
        nestedGraph = this.createActivityGraph();
        ForkNode variableSource = nestedGraph.addForkNode("Fork(each)");
        CallBehaviorAction callAction = nestedGraph.addCallBehaviorAction(
                getBehavior(RootNamespace.getRootScope().getSequenceFunctionCount()));
        ValueSpecificationAction valueAction = 
            nestedGraph.addNaturalValueSpecificationAction(1);
        TestIdentityAction testAction = nestedGraph.addTestIdentityAction("=1");
        
        nestedGraph.addObjectFlow(forkNode, callAction.getArgument().get(0));
        nestedGraph.addObjectFlow(variableSource, callAction.getArgument().get(1));
        nestedGraph.addObjectFlow(callAction.getResult().get(0), testAction.getFirst());
        nestedGraph.addObjectFlow(valueAction.getResult(), testAction.getSecond());
        
        ActivityNode node = super.mapNestedGraph(
                "uniqueness", variableSource, nestedGraph, testAction.getResult());
        ActivityNode nestedResult = 
            this.addTermination(nestedGraph, node);
        
        // Create the expansion region for testing the count on each element.
        // NOTE: Object flow from forkNode to the first callAction.getArgument() will
        // result in an input pin at the expansion region boundary.
        IsUniqueExpression expression = this.getIsUniqueExpression();
        ExpansionRegion region = this.graph.addExpansionRegion(
                "Uniqueness(" + expression.getClass().getSimpleName() + 
                            "@" + expression.getId() + ")", 
                "parallel", 
                nestedGraph.getModelElements(), 
                forkNode, variableSource, nestedResult);
        region.getInputElement().get(0).setType(argumentType);
        region.getOutputElement().get(0).setType(argumentType);
        this.graph.addControlFlow(forkStructureNode, region);
        this.resultSource = region.getOutputElement().get(0);
        
        // Add the final check that there are no elements with a count != 1.
        this.addBehaviorCall(RootNamespace.getRootScope().getSequenceFunctionIsEmpty());
        
        return argumentType;
    }
    
	public IsUniqueExpression getIsUniqueExpression() {
		return (IsUniqueExpression) this.getSource();
	}

} // IsUniqueExpressionMapping
