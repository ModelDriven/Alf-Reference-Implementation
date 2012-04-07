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
            ExpansionRegion region = new ExpansionRegion();
            region.setName(this.node.name);
            region.setMode(
                    isParallel? ExpansionKind.parallel: ExpansionKind.iterative);
            
            this.graph.remove(this.node);
            this.node = region;
            this.graph.add(region);
            
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
            
            for (String name: statement.getImpl().getParallelNames()) {
                ExpansionNode outputNode =
                        this.graph.addOutputExpansionNode(name, region);
                ActivityNode forkNode = this.graph.addForkNode("Fork(" + name +")");
                this.graph.addObjectFlow(outputNode, forkNode);
                this.assignedValueSourceMap.put(name, forkNode);
            }
            
            FumlMapping mapping = this.fumlMap(this.getBody());
            this.graph.addToExpansionRegion(region, mapping.getModelElements());
        } else {
            LoopNode node = (LoopNode)this.node;
            node.setIsTestedFirst(true);
            
            Collection<String> assignedNames = 
                this.mapAssignedValueSources(node, this.graph, true);
            
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
            
            ActivityNode forkNode = 
                    setupSubgraph.addForkNode("Fork(" + resultSource1.name + ")");
            InputPin inputPin = node.loopVariableInput.get(n);
            ActivityEdge flow = inputPin.incoming.get(0);
            flow.target.incoming.remove(flow);
            flow.setTarget(forkNode);
            setupSubgraph.addObjectFlow(forkNode, inputPin);            

            CallBehaviorAction callAction;
            if (resultSource2 == null) {
                callAction = setupSubgraph.addCallBehaviorAction(
                        getBehavior(RootNamespace.getListFunctionSize()));
                setupSubgraph.addObjectFlow(forkNode, callAction.argument.get(0));                
            } else {
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
            
            int i = node.loopVariable.size();
            ValueSpecificationAction valueAction = 
                    setupSubgraph.addNaturalValueSpecificationAction(1);
            this.addLoopVariable(
                    node, "counter", getIntegerType(), 1, 1, valueAction.result);
            this.addLoopVariable(
                    node, "upper", getIntegerType(), 1, 1, callAction.result.get(0));
            ActivityNode counterFork = node.loopVariable.get(i).outgoing.get(0).target;
            ActivityNode upperFork = node.loopVariable.get(i+1).outgoing.get(0).target;
            
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
                    subgraph.addObjectFlow(loopVariable, forkNode);
                } else {
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
            
            StructuredActivityNode bodyNode = 
                    bodyGraph.addStructuredActivityNode(
                            "Body(" + name + ")", bodyElements);
            bodyGraph.addControlFlow(valuesNode, bodyNode);
            
            i = n;
            for (LoopVariableDefinitionMapping variableDefinitionMapping:
                variableDefinitionMappings) {
                OutputPin loopVariable = node.loopVariable.get(i);
                forkNode = loopVariable.outgoing.get(0).target;
                if (i == n && 
                        variableDefinitionMapping.getResultSource2() != null) {
                    callAction = bodyGraph.addCallBehaviorAction(
                            getBehavior(RootNamespace.getIntegerFunctionPlus()));
                    valueAction = bodyGraph.addNaturalValueSpecificationAction(1);
                    bodyGraph.addObjectFlow(forkNode, callAction.argument.get(0));
                    bodyGraph.addObjectFlow(valueAction.result, callAction.argument.get(1));
                    node.addBodyOutput(callAction.result.get(0));
                } else {
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
            
            callAction = bodyGraph.addCallBehaviorAction(
                    getBehavior(RootNamespace.getIntegerFunctionPlus()));
            valueAction = bodyGraph.addNaturalValueSpecificationAction(1);
            bodyGraph.addObjectFlow(counterFork, callAction.argument.get(0));
            bodyGraph.addObjectFlow(valueAction.result, callAction.argument.get(1));
            node.addBodyOutput(callAction.result.get(0));
            
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
