
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
import org.modeldriven.alf.mapping.fuml.statements.StatementMapping;

import org.modeldriven.alf.syntax.statements.Block;

import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.Clause;
import fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.Collection;

public abstract class ConditionalStatementMapping extends StatementMapping {
    
    /** 
     * Common mapping for if and switch statements
     */
    
    protected Collection<String> mapConditionalNode(
            ConditionalNode node,
            ActivityGraph graph) throws MappingError {
        return super.mapAssignedValueSources(node, graph, false);
    }
    
    protected OutputPin mapAssignment(
            StructuredActivityNode node, String name, Classifier classifier, 
            int lower, int upper) {
        OutputPin outputPin = ActivityGraph.createOutputPin(
                node.name + 
                ".result(" + name + ")", 
                classifier,
                lower,
                upper);
        ((ConditionalNode)node).addResult(outputPin);
        return outputPin;
    }
    
    protected void mapFinalClause(
            Block block,
            ConditionalNode node,
            Collection<String> assignedNames,
            Collection<Clause> predecessorClauses,
            ActivityGraph graph
            ) throws MappingError {
        // NOTE: Even if the block is empty, a final clause is still needed
        // in order to pass through values of any names that may be assigned
        // in other clauses.
        if (block != null || assignedNames != null) {
            Collection<Element> modelElements = new ArrayList<Element>();
            ActivityGraph subgraph = new ActivityGraph();
            ValueSpecificationAction valueAction = 
                subgraph.addBooleanValueSpecificationAction(true);
            Clause clause = NonFinalClauseMapping.createClause(
                    subgraph.getModelElements(), valueAction.result, 
                    block == null? 
                        new ArrayList<Element>():
                        this.fumlMap(block).getModelElements(), 
                    block == null? 
                        this.getStatement().getImpl().getAssignmentBeforeMap(): 
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
    
} // IfStatementMapping
