/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.Statement;

import org.modeldriven.alf.uml.*;

public abstract class LoopStatementMapping extends StatementMapping {
    
    private ActivityFinalNode finalNode = null;
    
    /**
     * Common mapping for while and do statements.
     */
    
    @Override
    public StructuredActivityNode mapNode() {
        return this.create(LoopNode.class);
    }
    
    protected OutputPin addLoopVariable(
            LoopNode loopNode, String name, Classifier classifier,
            int lower, int upper, ActivityNode sourceNode) throws MappingError {
        InputPin inputPin = this.graph.createInputPin(
                loopNode.getName() + ".getLoopVariableInput()(" + name + ")", 
                classifier, lower, upper);
        loopNode.addLoopVariableInput(inputPin);
        if (sourceNode != null) {
            this.graph.addObjectFlow(sourceNode, inputPin);
        }
        
        OutputPin loopVariablePin = this.graph.createOutputPin(
                loopNode.getName() + ".loopVariable(" + name + ")", 
                classifier, lower, upper);
        loopNode.addStructuredNodeOutput(loopVariablePin);
        loopNode.addLoopVariable(loopVariablePin);
        ForkNode forkNode = this.create(ForkNode.class);
        forkNode.setName("Fork(" + loopVariablePin.getName() + ")");
        loopNode.addNode(forkNode);
        loopNode.addEdge(this.graph.createObjectFlow(loopVariablePin, forkNode));
        
        OutputPin outputPin = this.graph.createOutputPin(
                loopNode.getName() + ".result(" + name + ")", 
                classifier, lower, upper);
        loopNode.addResult(outputPin);
        return outputPin;
    }
    
    @Override
    protected OutputPin mapAssignment(
            StructuredActivityNode node, String name, Classifier classifier,
            int lower, int upper) throws MappingError {
        LoopNode loopNode = (LoopNode)node;
        Statement statement = this.getStatement();
        
        ActivityNode sourceNode = null;
        AssignedSource assignment = 
            statement.getImpl().getAssignmentBefore(name);
        if (assignment == null) {
            lower = 0;
        } else {
            FumlMapping mapping = this.fumlMap(assignment.getSource());
            if (!(mapping instanceof SyntaxElementMapping)) {
                this.throwError("Error mapping assigned source for " + name + 
                        ": " + this.getErrorMessage());
            } else {
                sourceNode = 
                    ((SyntaxElementMapping)mapping).getAssignedValueSource(name);
                if (sourceNode == null) {
                    this.throwError("No assigned value source for: " + name);
                }
            }
        }
            
        return this.addLoopVariable(
                loopNode, name, classifier, lower, upper, sourceNode);
    }
    
    public void mapLoop() throws MappingError {
        LoopNode node = (LoopNode)this.getElement();
        node.setIsTestedFirst(this.isTestedFirst());
        
        Collection<String> assignedNames = 
            this.mapAssignedValueSources(node, this.graph, true);
        
        FumlMapping mapping = this.fumlMap(this.getCondition());
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping condition: " + 
                    mapping.getErrorMessage());
        } else {
            ExpressionMapping conditionMapping = (ExpressionMapping)mapping;
            Collection<Element> conditionElements = 
                conditionMapping.getModelElements();
            mapping = this.fumlMap(this.getBody());
            Collection<Element> bodyElements = mapping.getModelElements();
            
            if (node.getIsTestedFirst()) {
                this.addToNode(conditionElements);
                for (Element element: conditionElements) {
                    if (element instanceof ExecutableNode) {
                        node.addTest((ExecutableNode)element);
                    }
                }
            } else {
                ActivityGraph subgraph = this.createActivityGraph();
                ActivityNode bodyNode = subgraph.addStructuredActivityNode(
                        "Body(" + node.getName() + ")", bodyElements);
                ActivityNode conditionNode = subgraph.addStructuredActivityNode(
                        "Condition(" + node.getName() + ")", conditionElements);
                subgraph.addControlFlow(bodyNode, conditionNode);
                bodyElements = subgraph.getModelElements();
                conditionElements = new ArrayList<Element>();
            }
            
            ActivityNode decider = conditionMapping.getResultSource();
            
            if (conditionElements.isEmpty() || !(decider instanceof OutputPin)) {
                StructuredActivityNode passthruNode = 
                    this.graph.createPassthruNode(
                            decider.getName(), getBooleanType(), 1, 1);
                node.addTest(passthruNode);
                node.addNode(passthruNode);
                node.addEdge(this.graph.createObjectFlow(
                        decider, passthruNode.getStructuredNodeInput().get(0)));
                decider = passthruNode.getStructuredNodeOutput().get(0);
            }
            
            node.setDecider((OutputPin)decider);
            
            // NOTE: Call to mapBodyOutputs must come before adding bodyElements
            // to the node, because mapping body outputs may add passthru nodes
            // to bodyElements.
            for (OutputPin bodyOutput: NonFinalClauseMapping.mapBodyOutputs(
                    bodyElements, this.getAssignments(), assignedNames, this)) {
                node.addBodyOutput(bodyOutput);
            }

            this.addToNode(bodyElements);
            for (Element element: bodyElements) {
                if (element instanceof ExecutableNode) {
                    node.addBodyPart((ExecutableNode)element);
                }
            }
        }
    }
    
    @Override
    public void map() throws MappingError {
        super.map();
        this.mapLoop();
    }
    
    @Override
    public void addToNode(Collection<Element> elements) {
        super.addToNode(elements);

        LoopNode node = (LoopNode)this.getElement();
        
        // Redirect flows into the loop node to use loop variables as sources.
        for (Element element: elements) {
            if (element instanceof ObjectFlow) {
                ObjectFlow flow = (ObjectFlow)element;
                if (!ActivityGraph.isContainedIn(flow.getSource(), node) &&
                        ActivityGraph.isContainedIn(flow.getTarget(), node)) {
                    Search:
                    for (int i = 0; i < node.getLoopVariableInput().size(); i++) {
                        InputPin inputPin = node.getLoopVariableInput().get(i);
                        for (ActivityEdge incoming: inputPin.getIncoming()) {
                            if (incoming.getSource().equals(flow.getSource())) {
                                flow.getSource().getOutgoing().remove(flow);
                                flow.setSource(node.getLoopVariable().get(i).
                                        getOutgoing().get(0).getTarget());
                                this.graph.remove(flow);
                                node.addEdge(flow);
                                break Search;
                            }
                        }
                    }
                }
            }
        }    
    }
    
    /**
     * Add a final node to be used as the control target in the mapping of
     * a break statement within the body of the statement for this mapping. 
     * (This method should only be called if that statement is a loop
     * statement.)
     */
    public ActivityFinalNode getFinalNode() throws MappingError {
        if (this.finalNode == null) {
            StructuredActivityNode node = (StructuredActivityNode) this.getNode();
            this.finalNode = this.create(ActivityFinalNode.class);
            this.finalNode.setName("Final(" + node.getName() + ")");
            node.addNode(this.finalNode);
        }
        return this.finalNode;
    }
    
    public abstract boolean isTestedFirst();
    public abstract Expression getCondition();
    public abstract Block getBody();
    public abstract Map<String, AssignedSource> getAssignments();
    
    @Override
    public String toString() {
        return super.toString() + 
                (this.node instanceof LoopNode? 
                        " isTestedFirst:" + ((LoopNode)node).getIsTestedFirst(): "");
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        Expression condition = this.getCondition();
        if (condition != null) {
            System.out.println(prefix + " condition:");
            Mapping mapping = condition.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
        
        Block body = this.getBody();
        if (body != null) {
            System.out.println(prefix + " body:");
            Mapping mapping = body.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
    }

}
