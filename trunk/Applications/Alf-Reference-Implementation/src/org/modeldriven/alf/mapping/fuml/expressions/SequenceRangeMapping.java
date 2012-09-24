
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.ArrayList;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.SequenceElementsMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.SequenceRange;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.*;

public class SequenceRangeMapping extends SequenceElementsMapping {

    /**
     * A sequence range mapping includes the mapping of the range upper and
     * lower expressions. The result source elements of the upper and lower
     * expressions are connected by object flows to input pins of a loop node.
     * The loop node also has a third input pin that has a multiplicity lower
     * bound of 0. The output pin corresponding to this third input pin is the
     * result source element for the sequence range.
     * 
     * The loop node is iterative, continually incrementing the value in its
     * first loop variable until it reaches the value of its second loop
     * variable. On each iteration, it appends the value of its first loop
     * variable to the list in its third loop variable, which, at the end of the
     * iteration, thus contains the desired sequence.
     */

    @Override
    protected void map() throws MappingError {
        SequenceRange sequenceRange = this.getSequenceRange();
        FumlMapping mapping = this.fumlMap(sequenceRange.getRangeLower());
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping range lower expression: " + 
                    mapping.getErrorMessage());
        } else {
            ExpressionMapping rangeLowerMapping = (ExpressionMapping)mapping;
            this.graph.addAll(rangeLowerMapping.getGraph());
            mapping = this.fumlMap(sequenceRange.getRangeUpper());
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping range upper expression: " + 
                        mapping.getErrorMessage());
            } else {
                ExpressionMapping rangeUpperMapping = (ExpressionMapping)mapping;
                this.graph.addAll(rangeUpperMapping.getGraph());
                
                ActivityNode resultSource = mapSequenceRangeLoop(
                        this.graph, 
                        rangeLowerMapping.getResultSource(), 
                        rangeUpperMapping.getResultSource(), 
                        "SequenceRange@" + sequenceRange.getId());
                
                this.resultSources.add(resultSource);
            }
        }
    }

	public SequenceRange getSequenceRange() {
		return (SequenceRange) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    SequenceRange sequenceRange = this.getSequenceRange();
	    Expression rangeLower = sequenceRange.getRangeLower();
	    Expression rangeUpper = sequenceRange.getRangeUpper();
	    
	    if (rangeLower != null) {
	        System.out.println(prefix + " rangeLower:");
	        Mapping mapping = rangeLower.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	    
	    if (rangeUpper != null) {
	        System.out.println(prefix + " rangeUpper:");
	        Mapping mapping = rangeUpper.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}
	
	// Static helper methods
	
	public static ActivityNode mapSequenceRangeLoop(
	        ActivityGraph graph,
	        ActivityNode resultSource1,
	        ActivityNode resultSource2,
	        String label) throws MappingError {
        InputPin rangeLowerInputPin = graph.createInputPin(
                "rangeLower", getIntegerType(), 1, 1);
        InputPin rangeUpperInputPin = graph.createInputPin(
                "rangeUpper", getIntegerType(), 1, 1);
        InputPin accumulatorInputPin = graph.createInputPin(
                "range", getIntegerType(), 0, -1);
        
        graph.addObjectFlow(
                resultSource1, 
                rangeLowerInputPin);
        graph.addObjectFlow(
                resultSource2, 
                rangeUpperInputPin);

        LoopNode loopNode = graph.addLoopNode(
                label, true, 
                rangeLowerInputPin, rangeUpperInputPin, accumulatorInputPin);
        
        // Test if counter is still less than the range upper limit.
        ActivityGraph loopGraph = new ActivityGraph(graph.getElementFactory());
        ForkNode fork0 = loopGraph.addForkNode(
                "Fork(" + loopNode.getLoopVariable().get(0).getName() + ")");
        ForkNode fork1 = loopGraph.addForkNode(
                "Fork(" + loopNode.getLoopVariable().get(1).getName() + ")");
        ForkNode fork2 = loopGraph.addForkNode(
                "Fork(" + loopNode.getLoopVariable().get(2).getName() + ")");
        loopGraph.addObjectFlow(loopNode.getLoopVariable().get(0), fork0);
        loopGraph.addObjectFlow(loopNode.getLoopVariable().get(1), fork1);
        loopGraph.addObjectFlow(loopNode.getLoopVariable().get(2), fork2);
        
        CallBehaviorAction testCall = 
            loopGraph.addCallBehaviorAction(getBehavior(
                    RootNamespace.getIntegerFunctionLessThanOrEqual()));
        loopGraph.addObjectFlow(
                fork0, testCall.getArgument().get(0));
        loopGraph.addObjectFlow(
                fork1, testCall.getArgument().get(1));
        
        graph.addLoopTest(
                loopNode, 
                loopGraph.getModelElements(), 
                testCall.getResult().get(0));
        
        loopGraph = new ActivityGraph(graph.getElementFactory());
        
        // Increment the counter.
        ValueSpecificationAction valueOne =
            loopGraph.addNaturalValueSpecificationAction(1);
        CallBehaviorAction incrementCall =
            loopGraph.addCallBehaviorAction(getBehavior(
                    RootNamespace.getIntegerFunctionPlus()));
        loopGraph.addObjectFlow(
                fork0, incrementCall.getArgument().get(0));
        loopGraph.addObjectFlow(
                valueOne.getResult(), incrementCall.getArgument().get(1));
        
        // Preserve the range upper bound.
        StructuredActivityNode node =
            loopGraph.addStructuredActivityNode(
                    "Node(" + loopNode.getLoopVariable().get(1).getName(), 
                    new ArrayList<Element>());
        node.addStructuredNodeInput(graph.createInputPin(
                node.getName() + ".input", getIntegerType(), 1, 1));
        node.addStructuredNodeOutput(graph.createOutputPin(
                node.getName() + ".output", getIntegerType(), 1, 1));
        node.addEdge(graph.createObjectFlow(
                node.getStructuredNodeInput().get(0), 
                node.getStructuredNodeOutput().get(0)));
        loopGraph.addObjectFlow(fork1, node.getStructuredNodeInput().get(0));
        
        // Append the counter to the list.
        CallBehaviorAction appendCall =
            loopGraph.addCallBehaviorAction(getBehavior(
                    RootNamespace.getSequenceFunctionIncluding()));
        loopGraph.addObjectFlow(
                loopNode.getLoopVariable().get(2), appendCall.getArgument().get(0));
        loopGraph.addObjectFlow(
                fork0, appendCall.getArgument().get(1));
        
        graph.addLoopBodyPart(
                loopNode, 
                loopGraph.getModelElements(), 
                incrementCall.getResult().get(0), 
                node.getStructuredNodeOutput().get(0), 
                appendCall.getResult().get(0));
        
        return loopNode.getResult().get(2);
	}

} // SequenceRangeMapping
