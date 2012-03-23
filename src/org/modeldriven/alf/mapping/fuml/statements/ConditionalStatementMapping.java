
/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.statements.StatementMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.Statement;

import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.Clause;
import fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class ConditionalStatementMapping extends StatementMapping {
    
    private Map<String, ActivityNode> assignedValueSourceMap = 
        new HashMap<String, ActivityNode>();

    /** 
     * Common mapping for if and switch statements
     */
    
    protected Collection<String> mapConditionalNode(
            ConditionalNode node,
            ActivityGraph graph) throws MappingError {
        Statement statement = this.getStatement();
        
        Collection<String> assignedNames = new ArrayList<String>();
        for (AssignedSource assignment: statement.getAssignmentAfter()) {
            if (assignment.getSource() == statement) {
                String name = assignment.getName();
                ElementReference type = assignment.getType();
                assignedNames.add(name);
                
                Classifier classifier = null;
                if (type != null) {
                    FumlMapping mapping = this.fumlMap(type);
                    if (mapping instanceof ElementReferenceMapping) {
                        mapping = ((ElementReferenceMapping)mapping).getMapping();
                    }
                    if (!(mapping instanceof ClassifierDefinitionMapping)) {
                        this.throwError("Error mapping type " + type + ": " + 
                                mapping.getErrorMessage());
                    }
                    classifier = 
                        ((ClassifierDefinitionMapping)mapping).getClassifier();
                }
                
                OutputPin outputPin = ActivityGraph.createOutputPin(
                        ((StructuredActivityNode)this.getNode()).name + 
                        ".result(" + name + ")", 
                        classifier,
                        assignment.getLower(),
                        assignment.getUpper());
                node.addResult(outputPin);

                ForkNode forkNode = new ForkNode();
                forkNode.setName("Fork(" + name + ")");
                this.assignedValueSourceMap.put(name, forkNode);
                graph.add(forkNode);
                graph.add(ActivityGraph.createObjectFlow(outputPin, forkNode));                
            }
        }
        return assignedNames;
    }
    
    protected void mapFinalClause(
            Block block,
            ConditionalNode node,
            Collection<String> assignedNames,
            Collection<Clause> predecessorClauses,
            ActivityGraph graph
            ) throws MappingError {
        if (block != null) {
            Collection<Element> modelElements = new ArrayList<Element>();
            FumlMapping mapping = this.fumlMap(block);
            ActivityGraph subgraph = new ActivityGraph();
            ValueSpecificationAction valueAction = 
                subgraph.addBooleanValueSpecificationAction(true);
            Clause clause = NonFinalClauseMapping.createClause(
                    subgraph.getModelElements(), valueAction.result, 
                    mapping.getModelElements(), 
                    block.getImpl().getAssignmentAfterMap(),
                    assignedNames, 
                    modelElements, this);
            for (Clause predecessorClause: predecessorClauses) {
                clause.addPredecessorClause(predecessorClause);
            }
            node.addClause(clause);
            graph.addToStructuredNode(node, modelElements);
        }
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) {
        return this.assignedValueSourceMap.get(name);
    }
    
} // IfStatementMapping
