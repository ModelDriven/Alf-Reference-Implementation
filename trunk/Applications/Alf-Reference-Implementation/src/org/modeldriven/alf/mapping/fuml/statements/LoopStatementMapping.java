/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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

import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Activities.IntermediateActivities.ObjectFlow;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class LoopStatementMapping extends StatementMapping {
    
    /**
     * Common mapping for while and do statements.
     */
    
    @Override
    public StructuredActivityNode mapNode() {
        return new LoopNode();
    }
    
    @Override
    protected OutputPin mapAssignment(
            StructuredActivityNode node, String name, Classifier classifier,
            int lower, int upper) throws MappingError {
        LoopNode loopNode = (LoopNode)node;
        Statement statement = this.getStatement();
        
        InputPin inputPin = ActivityGraph.createInputPin(
                node.name + ".input(" + name + ")", classifier, lower, upper);
        loopNode.addLoopVariableInput(inputPin);
        AssignedSource assignment = 
            statement.getImpl().getAssignmentBefore(name);
        if (assignment != null) {
            FumlMapping mapping = this.fumlMap(assignment.getSource());
            if (!(mapping instanceof SyntaxElementMapping)) {
                this.throwError("Error mapping assigned source for " + name + 
                        ": " + this.getErrorMessage());
            } else {
                ActivityNode sourceNode = 
                    ((SyntaxElementMapping)mapping).getAssignedValueSource(name);
                if (sourceNode == null) {
                    this.throwError("No assigned value source for: " + name);
                } else {
                    this.graph.addObjectFlow(sourceNode, inputPin);
                }
            }
        }
        
        OutputPin loopVariablePin = ActivityGraph.createOutputPin(
                node.name + ".variable(" + name + ")", classifier, lower, upper);
        loopNode.addLoopVariable(loopVariablePin);
        ForkNode forkNode = new ForkNode();
        forkNode.setName("Fork(" + loopVariablePin.name + ")");
        node.addNode(forkNode);
        node.addEdge(ActivityGraph.createObjectFlow(loopVariablePin, forkNode));
        
        OutputPin outputPin = ActivityGraph.createOutputPin(
                node.name + ".result(" + name + ")", classifier, lower, upper);
        loopNode.addResult(outputPin);
        return outputPin;
    }
    
    @Override
    public void map() throws MappingError {
        super.map();
        
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
            
            if (node.isTestedFirst) {
                this.addToNode(conditionElements);
                for (Element element: conditionElements) {
                    if (element instanceof ExecutableNode) {
                        node.addTest((ExecutableNode)element);
                    }
                }
            } else {
                ActivityGraph subgraph = new ActivityGraph();
                ActivityNode bodyNode = subgraph.addStructuredActivityNode(
                        "Body(" + node.name + ")", bodyElements);
                ActivityNode conditionNode = subgraph.addStructuredActivityNode(
                        "Condition(" + node.name + ")", conditionElements);
                subgraph.addControlFlow(bodyNode, conditionNode);
                bodyElements = subgraph.getModelElements();
                conditionElements = new ArrayList<Element>();
            }
            
            ActivityNode decider = conditionMapping.getResultSource();
            
            if (conditionElements.isEmpty() || !(decider instanceof OutputPin)) {
                StructuredActivityNode passthruNode = 
                    ActivityGraph.createPassthruNode(
                            decider.name, getBooleanType(), 1, 1);
                node.addTest(passthruNode);
                node.addNode(passthruNode);
                node.addEdge(ActivityGraph.createObjectFlow(
                        decider, passthruNode.structuredNodeInput.get(0)));
                decider = passthruNode.structuredNodeOutput.get(0);
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
    public void addToNode(Collection<Element> elements) {
        super.addToNode(elements);

        LoopNode node = (LoopNode)this.getElement();
        
        // Redirect flows into the loop node to use loop variables as sources.
        for (Element element: elements) {
            if (element instanceof ObjectFlow) {
                ObjectFlow flow = (ObjectFlow)element;
                if (!ActivityGraph.isContainedIn(flow.source, node) &&
                        ActivityGraph.isContainedIn(flow.target, node)) {
                    Search:
                    for (int i = 0; i < node.loopVariableInput.size(); i++) {
                        InputPin inputPin = node.loopVariableInput.get(i);
                        for (ActivityEdge incoming: inputPin.incoming) {
                            if (incoming.source == flow.source) {
                                flow.source.outgoing.remove(flow);
                                flow.setSource(node.loopVariable.get(i).
                                        outgoing.get(0).target);
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
    
    public abstract boolean isTestedFirst();
    public abstract Expression getCondition();
    public abstract Block getBody();
    public abstract Map<String, AssignedSource> getAssignments();
    
    @Override
    public String toString() {
        LoopNode node = (LoopNode)this.getElement();
        return super.toString() + " isTestedFirst:" + node.isTestedFirst;
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
