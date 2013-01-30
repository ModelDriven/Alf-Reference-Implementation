
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.SwitchClause;
import org.modeldriven.alf.syntax.statements.SwitchStatement;

import org.modeldriven.alf.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SwitchStatementMapping extends ConditionalStatementMapping {
    
    /**
     * Clauses 
     * 
     * 1. A switch statement maps to a structured activity node that
     * contains a conditional node and the mapping for the switch expression.
     * The switch clauses map to concurrent clauses of the conditional node.
     * Each clause tests whether the result of the switch expression equals the
     * result of one of the case expressions.
     * 
     * 2. A switch default clause is mapped to a conditional node clause with a
     * condition of true and with all other clauses as predecessor clauses.
     * 
     * 3. The isAssured and/or isDetermined properties of the conditional node
     * are set according to whether the switch statement is assured or
     * determined..
     * 
     * Output Pins
     * 
     * 4. The result and clause body output pins of the conditional node are
     * mapped as for an if statement.
     */
    
    // NOTE: See ConditionalStatementMapping for implementation of mapping
    // common to if and switch statements. See also SwitchClausesMapping.
    
    @Override
    public void map()throws MappingError {
        super.map();
        
        SwitchStatement statement = this.getSwitchStatement();

        ActivityGraph graph = this.createActivityGraph();
        ConditionalNode node = this.create(ConditionalNode.class);
        node.setName("Conditional(SwitchStatement@" + statement.getId() + ")");
        graph.add(node);
        
        List<String> assignedNames = this.mapConditionalNode(node, graph);
        
        Collection<Clause> clauses = new ArrayList<Clause>();        
        FumlMapping mapping = this.fumlMap(statement.getExpression());
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping switch expression: " + 
                    mapping.getErrorMessage());
        } else {
            ExpressionMapping expressionMapping = (ExpressionMapping)mapping;
            ActivityNode resultSource = expressionMapping.getResultSource();
            graph.addAll(expressionMapping.getGraph());
            ActivityNode forkNode = 
                graph.addForkNode("Fork(" + resultSource.getName() + ")");
            graph.addObjectFlow(resultSource, forkNode);
            
            for (SwitchClause switchClause: statement.getNonDefaultClause()) {
                mapping = this.fumlMap(switchClause);
                if (!(mapping instanceof SwitchClauseMapping)) {
                    this.throwError("Error mapping switch clause " + 
                            switchClause + ": " + mapping.getErrorMessage());
                } else {
                    SwitchClauseMapping clauseMapping = 
                        (SwitchClauseMapping)mapping;
                    clauseMapping.setSwitchSource(resultSource);
                    clauseMapping.setAssignedNames(assignedNames);
                    graph.addToStructuredNode(
                            node, clauseMapping.getModelElements());
                    Clause clause = clauseMapping.getClause();
                    node.addClause(clause);
                    clauses.add(clause);
                }
            }
        }
        
        this.mapFinalClause(
                statement.getDefaultClause(), node, 
                assignedNames, clauses, graph);
        this.addToNode(graph.getModelElements());
        
        node.setIsAssured(statement.getIsAssured());
        node.setIsDeterminate(statement.getIsDeterminate());
    }

	public SwitchStatement getSwitchStatement() {
		return (SwitchStatement) this.getSource();
	}

    @Override
    public String toString() {
        StructuredActivityNode structuredNode = 
            (StructuredActivityNode)this.getElement();
        ConditionalNode conditionalNode = null;
        for (ActivityNode node: structuredNode.getNode()) {
            if (node instanceof ConditionalNode) {
                conditionalNode = (ConditionalNode)node;
                break;
            }
        }
        return super.toString() + 
            (conditionalNode == null? "": 
                " isDeterminate:" + conditionalNode.getIsDeterminate() + 
                " isAssured:" + conditionalNode.getIsAssured());
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        SwitchStatement statement = this.getSwitchStatement();
        
        Collection<SwitchClause> nonDefaultClauses = 
            statement.getNonDefaultClause();
        if (!nonDefaultClauses.isEmpty()) {
            System.out.println(prefix + " nonDefaultClause:");
            for (SwitchClause clause: nonDefaultClauses) {
                Mapping mapping = clause.getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix);
                }
            }
        }
        
        Block defaultClause = statement.getDefaultClause();
        if (defaultClause != null) {
            System.out.println(prefix + " defaultClause:");
            Mapping mapping = defaultClause.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
    }

} // SwitchStatementMapping
