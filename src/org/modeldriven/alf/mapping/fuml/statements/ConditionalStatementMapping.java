
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.statements.StatementMapping;

import org.modeldriven.alf.syntax.statements.Block;

import org.modeldriven.alf.uml.*;

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
        OutputPin outputPin = this.graph.createOutputPin(
                node.getName() + 
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
            ActivityGraph subgraph = this.createActivityGraph();
            ValueSpecificationAction valueAction = 
                subgraph.addBooleanValueSpecificationAction(true);
            Clause clause = NonFinalClauseMapping.createClause(
                    subgraph.getModelElements(), valueAction.getResult(), 
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
