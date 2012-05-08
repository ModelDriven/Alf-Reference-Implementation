/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ForStatement;
import org.modeldriven.alf.syntax.statements.LoopVariableDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.ExecutableNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Element;

public class ForStatementMapping extends LoopStatementMapping {

    /**
     * Iterative for Statements
     * 
     * 1. An iterative for statement is generally mapped to a structured
     * activity node containing the mapping of the loop variable expressions and
     * either a loop node or an expansion region.
     * 
     * 2. An iterative for statement that does not make within it any
     * re-assignments to names defined outside of it is mapped to an expansion
     * region with mode=iterative. The result source element from the mapping of
     * the loop variable expressions are connected by object flows to input
     * expansion nodes on the expansion region.
     * 
     * 3. Otherewise, an iterative for statement is mapped to a loop node . A
     * for statement of the form
     * 
     * for (v1 in expr1, v2 in expr2,...) { stmts } 
     * 
     * is mapped equivalently to 
     * 
     * {
     *  list1 = (T[]) expr1; 
     *  list2 = (T[]) expr2; 
     *  ... 
     *  size = list1->size(); 
     *  i = 1; 
     *  while (i <= size) { 
     *      v1 = list1[i]; 
     *      v2 = list2[i]; 
     *      ... 
     *      stmts 
     *      i++; 
     *  } 
     * }
     * 
     * where list, size and i are arbitrary local names not otherwise used in
     * the enclosing activity and T is the type of expr1.
     * 
     * Parallel for Statements
     * 
     * 4. A parallel for statement is always mapped to an expansion region, but
     * with mode=parallel. Any name listed in the @parallel annotation for the
     * statement is mapped to an output expansion node with that name and its
     * assigned type. This expansion node provides the source element for the
     * assigned value for the name after the for statement.
     */
    
    @Override
    public void mapLoop() throws MappingError {
        ForStatement statement = this.getForStatement();
        boolean isParallel = statement.getIsParallel();
        
        // Map loop variable definitions, bit don't add to loop mapping yet.
        List<LoopVariableDefinitionMapping> variableDefinitionMappings = 
                new ArrayList<LoopVariableDefinitionMapping>();
        ActivityGraph setupSubgraph = new ActivityGraph();
        for (LoopVariableDefinition definition: statement.getVariableDefinition()) {
            FumlMapping mapping = this.fumlMap(definition);
            if (!(mapping instanceof LoopVariableDefinitionMapping)) {
                this.throwError("Error mapping definition of " + 
                        definition.getVariable() + ": " + 
                        mapping.getErrorMessage());
            } else {
                LoopVariableDefinitionMapping definitionMapping =
                        (LoopVariableDefinitionMapping)mapping;
                definitionMapping.setIsParallel(isParallel);
                setupSubgraph.addAll(definitionMapping.getGraph());
                variableDefinitionMappings.add(definitionMapping);
            }
        }
        
        LoopVariableDefinitionMapping firstDefinitionMapping =
                variableDefinitionMappings.get(0);
        ActivityNode resultSource1 = firstDefinitionMapping.getResultSource1();
        ActivityNode resultSource2 = firstDefinitionMapping.getResultSource2();

        if (isParallel || 
                resultSource2 == null && 
                    statement.getImpl().getNewAssignments().isEmpty()) {
            // If this is a parallel for statement, or the first loop variable
            // definition is not a sequence construction shortcut and there
            // are no new assignments in the body, map as an expansion region.
            ExpansionRegion region = new ExpansionRegion();
            region.setName(this.node.name);
            region.setMode(
                    isParallel? ExpansionKind.parallel: ExpansionKind.iterative);
            
            // Replace the default mapping to a loop node with the expansion
            // region.
            this.graph.remove(this.node);
            this.node = region;
            this.graph.add(region);
            
            // For each loop variable definition, create an input expansion
            // node with a corresponding fork node (that becomes the assigned
            // value source for the loop variable within the body).
            for (LoopVariableDefinitionMapping variableDefinitionMapping: 
                variableDefinitionMappings) {
                String name = 
                        variableDefinitionMapping.getLoopVariableDefinition().
                            getVariable();
                ExpansionNode inputNode = 
                        this.graph.addInputExpansionNode(name, region);
                ActivityNode forkNode = new ForkNode();
                forkNode.setName("Fork(" + name + ")");
                region.addNode(forkNode);
                
                this.graph.addObjectFlow(
                        variableDefinitionMapping.getResultSource1(), inputNode);
                region.addEdge(ActivityGraph.createObjectFlow(inputNode, forkNode));
                
                variableDefinitionMapping.setAssignedValueSource(forkNode);
            }
            
            // For each @parallel name, create an output expansion node with
            // a corresponding fork node (that becomes the assigned value
            // source for the name after the for statement).
            // (Note: This must be done before mapping the body, so the output
            // expansion nodes are also available for mapping @parallel names
            // used as targets of add calls within the body.)
            for (String name: statement.getImpl().getParallelNames()) {
                ExpansionNode outputNode =
                        this.graph.addOutputExpansionNode(name, region);
                ActivityNode forkNode = this.graph.addForkNode("Fork(" + name +")");
                this.graph.addObjectFlow(outputNode, forkNode);
                this.assignedValueSourceMap.put(name, forkNode);
            }
            
            // Map the for statement body within the expansion region.
            FumlMapping mapping = this.fumlMap(this.getBody());
            this.graph.addToExpansionRegion(region, mapping.getModelElements());
        } else {
            // If not mapped as an expansion region, map as a loop node.
            LoopNode node = (LoopNode)this.node;
            node.setIsTestedFirst(true);
            
            // Create output pins for all names assigned after the for
            // statement.
            // NOTE: Output pins are created even for names that are not
            // assigned within the for statement, so they can be made available
            // as loop variables for access within the body of the for 
            // statement.
            Collection<String> assignedNames = 
                this.mapAssignedValueSources(node, this.graph, true);
            
            // Create a loop variable pin for corresponding to each loop
            // variable definition.
            int n = node.loopVariable.size();            
            for (LoopVariableDefinitionMapping variableDefinitionMapping: 
                variableDefinitionMappings) {
                this.addLoopVariable(
                        node, 
                        variableDefinitionMapping.getLoopVariableDefinition().getVariable(), 
                        variableDefinitionMapping.getType(), 
                        0, -1, 
                        variableDefinitionMapping.getResultSource1());
            }
            
            // Redirect the flow into the first loop variable input pin though a
            // fork node.
            ActivityNode forkNode = 
                    setupSubgraph.addForkNode("Fork(" + resultSource1.name + ")");
            InputPin inputPin = node.loopVariableInput.get(n);
            ActivityEdge flow = inputPin.incoming.get(0);
            flow.target.incoming.remove(flow);
            flow.setTarget(forkNode);
            setupSubgraph.addObjectFlow(forkNode, inputPin);

            CallBehaviorAction callAction;
            if (resultSource2 == null) {
                // Unless the first loop variable definition is a sequence
                // construction shortcut, the number of iterations of the
                // loop is determined by the list size of the first loop
                // variable input.
                callAction = setupSubgraph.addCallBehaviorAction(
                        getBehavior(RootNamespace.getListFunctionSize()));
                setupSubgraph.addObjectFlow(forkNode, callAction.argument.get(0));                
            } else {
                // If the first loop variable definition is a sequence
                // construction shortcut of the form m..n, then the number of
                // iterations of the loop is n-m+1.
                inputPin.multiplicityElement.setLower(1);
                inputPin.multiplicityElement.setUpper(1);
                CallBehaviorAction minusAction = setupSubgraph.addCallBehaviorAction(
                        getBehavior(RootNamespace.getIntegerFunctionMinus()));
                ValueSpecificationAction valueAction = 
                        setupSubgraph.addNaturalValueSpecificationAction(1);
                callAction = setupSubgraph.addCallBehaviorAction(
                        getBehavior(RootNamespace.getIntegerFunctionPlus()));
                setupSubgraph.addObjectFlow(resultSource2, minusAction.argument.get(0));
                setupSubgraph.addObjectFlow(forkNode, minusAction.argument.get(1));
                setupSubgraph.addObjectFlow(minusAction.result.get(0), callAction.argument.get(0));
                setupSubgraph.addObjectFlow(valueAction.result, callAction.argument.get(1));
            }
            
            // Set up loop variables for counting loop iterations and 
            // for the iteration count upper bound.
            int i = node.loopVariable.size();
            ValueSpecificationAction valueAction = 
                    setupSubgraph.addNaturalValueSpecificationAction(1);
            this.addLoopVariable(
                    node, "counter", getIntegerType(), 1, 1, valueAction.result);
            this.addLoopVariable(
                    node, "upper", getIntegerType(), 1, 1, callAction.result.get(0));
            ActivityNode counterFork = node.loopVariable.get(i).outgoing.get(0).target;
            ActivityNode upperFork = node.loopVariable.get(i+1).outgoing.get(0).target;
            
            // Map the test count <= upper.
            ActivityGraph subgraph = new ActivityGraph();
            CallBehaviorAction testCall = 
                subgraph.addCallBehaviorAction(getBehavior(
                        RootNamespace.getIntegerFunctionLessThanOrEqual()));
            subgraph.addObjectFlow(
                    counterFork, testCall.argument.get(0));
            subgraph.addObjectFlow(
                    upperFork, testCall.argument.get(1));
            
            Collection<Element> testElements = subgraph.getModelElements();
            this.addToNode(testElements);
            for (Element element: testElements) {
                if (element instanceof ExecutableNode) {
                    node.addTest((ExecutableNode)element);
                }
            }
            
            node.setDecider(testCall.result.get(0));
            
            // Determine the assigned value source for each loop variable
            // within the body of the for statement.
            i = n;
            subgraph = new ActivityGraph();
            for (LoopVariableDefinitionMapping variableDefinitionMapping: 
                variableDefinitionMappings) {
                OutputPin loopVariable = node.loopVariable.get(i);
                forkNode = 
                        subgraph.addForkNode("Fork(" + 
                                variableDefinitionMapping.
                                getLoopVariableDefinition().getVariable() + ")");
                if (i == n && 
                        variableDefinitionMapping.getResultSource2() != null) {
                    // If this is the first loop variable, and its definition
                    // is a sequence construction shorthand, then its assigned
                    // value is just the value of the loop variable pin
                    subgraph.addObjectFlow(loopVariable, forkNode);
                } else {
                    // Otherwise, the assigned value of the loop variable is
                    // the value at the position in the loop variable input list
                    // given by the current iteration count.
                    ActivityNode variableFork = 
                            subgraph.addForkNode("Fork(" + loopVariable.name + ")");
                    callAction = subgraph.addCallBehaviorAction(
                            getBehavior(RootNamespace.getListFunctionGet()));
                    subgraph.addObjectFlow(loopVariable, variableFork);
                    subgraph.addObjectFlow(variableFork, callAction.argument.get(0));
                    subgraph.addObjectFlow(counterFork, callAction.argument.get(1));
                    subgraph.addObjectFlow(callAction.result.get(0), forkNode);
                }
                variableDefinitionMapping.setAssignedValueSource(forkNode);
                i++;
            }
            
            ActivityGraph bodyGraph = new ActivityGraph();
            String name = this.node.name;
            StructuredActivityNode valuesNode = 
                    bodyGraph.addStructuredActivityNode(
                            "Values(" + name + ")", subgraph.getModelElements());

            // Map the body of the for statement.
            Block body = this.getBody();
            FumlMapping mapping = this.fumlMap(body);
            Collection<Element>  bodyElements = mapping.getModelElements();
            
            // NOTE: Call to mapBodyOutputs must come before adding bodyElements
            // to the node, because mapping body outputs may add passthru nodes
            // to bodyElements.
            for (OutputPin bodyOutput: NonFinalClauseMapping.mapBodyOutputs(
                    bodyElements, this.getAssignments(), assignedNames, this)) {
                node.addBodyOutput(bodyOutput);
            }
            
            // NOTE: The loop variable value model elements and the body model
            // elements are mapped into separate structured activity nodes,
            // with the former having a control flow to the latter, to ensure
            // all the loop variables have the proper values before the main
            // body executes.
            StructuredActivityNode bodyNode = 
                    bodyGraph.addStructuredActivityNode(
                            "Body(" + name + ")", bodyElements);
            bodyGraph.addControlFlow(valuesNode, bodyNode);
            
            // Identify the body output corresponding to each loop variable.
            i = n;
            for (LoopVariableDefinitionMapping variableDefinitionMapping:
                variableDefinitionMappings) {
                OutputPin loopVariable = node.loopVariable.get(i);
                forkNode = loopVariable.outgoing.get(0).target;
                if (i == n && 
                        variableDefinitionMapping.getResultSource2() != null) {
                    // If the this is the first loop variable, and its
                    // definition is a sequence construction shorthand, then
                    // its value must be incremented for the next iteration.
                    callAction = bodyGraph.addCallBehaviorAction(
                            getBehavior(RootNamespace.getIntegerFunctionPlus()));
                    valueAction = bodyGraph.addNaturalValueSpecificationAction(1);
                    bodyGraph.addObjectFlow(forkNode, callAction.argument.get(0));
                    bodyGraph.addObjectFlow(valueAction.result, callAction.argument.get(1));
                    node.addBodyOutput(callAction.result.get(0));
                } else {
                    // Otherwise, the loop variable input list must simple be
                    // passed through to the next iteration unchanged.
                    StructuredActivityNode passthruNode = 
                            ActivityGraph.createPassthruNode(
                                    "Passthru(" + loopVariable.name + ")", 
                                    loopVariable.typedElement.type, 0, -1);
                    bodyGraph.add(passthruNode);
                    bodyGraph.addObjectFlow(
                            forkNode, passthruNode.structuredNodeInput.get(0));
                    node.addBodyOutput(passthruNode.structuredNodeOutput.get(0));
                }
                i++;
            }
            
            // Map the incrementing of the loop iteration count.
            callAction = bodyGraph.addCallBehaviorAction(
                    getBehavior(RootNamespace.getIntegerFunctionPlus()));
            valueAction = bodyGraph.addNaturalValueSpecificationAction(1);
            bodyGraph.addObjectFlow(counterFork, callAction.argument.get(0));
            bodyGraph.addObjectFlow(valueAction.result, callAction.argument.get(1));
            node.addBodyOutput(callAction.result.get(0));
            
            // Map the passing through of the upper bound value.
            StructuredActivityNode passthruNode = 
                    ActivityGraph.createPassthruNode(
                            "Passthru(upper)", getIntegerType(), 1, 1);
            bodyGraph.add(passthruNode);
            bodyGraph.addObjectFlow(
                    upperFork, passthruNode.structuredNodeInput.get(0));
            node.addBodyOutput(passthruNode.structuredNodeOutput.get(0));


            this.addToNode(bodyGraph.getModelElements());
            for (Element element: bodyGraph.getModelElements()) {
                if (element instanceof ExecutableNode) {
                    node.addBodyPart((ExecutableNode)element);
                }
            }        
        }
        
        if (!setupSubgraph.isEmpty()) {
            // Unless there are no setup model elements, map the setup elements
            // into their own structured activity node that must complete
            // before execution of the loop node/expansion region.
            ActivityGraph subgraph = new ActivityGraph();
            String name = this.node.name;
            this.node.setName("Loop(" + name + ")");
            subgraph.addAll(this.graph);
            StructuredActivityNode variablesNode =
                    subgraph.addStructuredActivityNode(
                            "LoopVariables(" + name + ")", 
                            setupSubgraph.getModelElements());
            subgraph.addControlFlow(variablesNode, this.node);
            this.graph = new ActivityGraph();
            this.node = this.graph.addStructuredActivityNode(
                    name, subgraph.getModelElements());        
        }
    }

    public ForStatement getForStatement() {
        return (ForStatement) this.getSource();
    }

    @Override
    public boolean isTestedFirst() {
        return true;
    }

    @Override
    public Expression getCondition() {
        return null;
    }

    @Override
    public Block getBody() {
        return this.getForStatement().getBody();
    }

    @Override
    public Map<String, AssignedSource> getAssignments() {
        return this.getBody().getImpl().getAssignmentAfterMap();
    }

} // ForStatementMapping
