
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.statements.Block;
import org.modeldriven.alf.syntax.statements.ConcurrentClauses;
import org.modeldriven.alf.syntax.statements.IfStatement;

import org.modeldriven.alf.uml.Clause;
import org.modeldriven.alf.uml.ConditionalNode;
import org.modeldriven.alf.uml.StructuredActivityNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IfStatementMapping extends ConditionalStatementMapping {
    
    /**
     * Clauses
     * 
     * 1. An if statement maps to a conditional node. Each if clause maps to a
     * clause of the conditional node. For a final if clause, the test part of
     * the clause is a single value specification action for a Boolean literal
     * with value "true".
     * 
     * 2. Each clause specified in a concurrent clause set has as predecessors
     * all clauses specified by the immediately preceding concurrent clause set
     * (if any) in the sequential clause set for the conditional node. A final
     * clause has as its predecessor all clauses specified by the immediately
     * preceding concurrent clause set.
     * 
     * 3. The isAssured and/or isDetermined properties of the conditional node
     * are set according to whether the if statement is assured or determined.
     * 
     * Output Pins
     * 
     * 4. The conditional node has a result output pin corresponding to each
     * local name that is assigned in any of the if clauses. Therefore, each
     * clause of the conditional node also must have a body output pin from
     * within the clause identified for each result pin of the conditional node.
     * If a name is assigned within a clause and the assigned source for that
     * name within the clause is a pin on an action within the body of the
     * clause, then that pin is used as the clause body output pin corresponding
     * to that local name. Otherwise, a structured activity node is added to the
     * mapping of the clause as follows: The structured activity node has one
     * input pin and one output pin, with an object flow from the input pin to
     * the output pin contained within the structured activity node. There is an
     * object flow from the assigned source for the name after the clause (which
     * may be from inside or outside the clause) to the input pin of the
     * structured activity node. The output pin of the structured activity node
     * is then used as the clause body output pin corresponding to the name.
     */
    
    // NOTE: See ConditionalStatementMapping for implementation of mapping
    // common to if and switch statements. See also ConcurrentClausesMapping and 
    // NonFinalClauseMapping.
    
    @Override
    public StructuredActivityNode mapNode() {
        return this.create(ConditionalNode.class);
    }
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        ConditionalNode node = (ConditionalNode)this.getElement();
        IfStatement statement = this.getIfStatement();
        
        Collection<String> assignedNames = 
            this.mapConditionalNode(node, this.graph);
        
        Collection<Clause> predecessorClauses = new ArrayList<Clause>();
        for (ConcurrentClauses nonFinalClauses: statement.getNonFinalClauses()) {
            FumlMapping mapping = this.fumlMap(nonFinalClauses);
            if (!(mapping instanceof ConcurrentClausesMapping)) {
                this.throwError("Error mapping non final clauses " + 
                        nonFinalClauses + ": " + mapping.getErrorMessage());
            } else {
                ConcurrentClausesMapping clausesMapping =
                    (ConcurrentClausesMapping)mapping;
                clausesMapping.setAssignedNames(assignedNames);
                Collection<Clause> clauses = clausesMapping.getClauses();
                for (Clause clause: clauses) {
                    for (Clause predecessorClause: predecessorClauses) {
                        clause.addPredecessorClause(predecessorClause);
                    }
                    node.addClause(clause);
                }
                this.addToNode(clausesMapping.getModelElements());
                predecessorClauses = clauses;
            }
        }
        
        this.mapFinalClause(
                statement.getFinalClause(), node, 
                assignedNames, predecessorClauses, this.graph);
        
        node.setIsAssured(statement.getIsAssured());
        node.setIsDeterminate(statement.getIsDeterminate());
    }
    
	public IfStatement getIfStatement() {
		return (IfStatement) this.getSource();
	}
	
    @Override
    public String toString() {
        ConditionalNode node = (ConditionalNode)this.getElement();
        return super.toString() + 
            (node == null? "": 
                " isDeterminate:" + node.getIsDeterminate() + 
                " isAssured:" + node.getIsAssured());
    }
    
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    IfStatement statement = this.getIfStatement();
	    
	    List<ConcurrentClauses> nonFinalClauses = statement.getNonFinalClauses();
	    if (!nonFinalClauses.isEmpty()) {
            System.out.println(prefix + " nonFinalClauses:");
    	    for (ConcurrentClauses clauses: nonFinalClauses) {
    	        Mapping mapping = clauses.getImpl().getMapping();
    	        if (mapping != null) {
    	            mapping.printChild(prefix);
    	        }
    	    }
	    }
	    
	    Block finalClause = statement.getFinalClause();
	    if (finalClause != null) {
	        System.out.println(prefix + " finalClause:");
	        Mapping mapping = finalClause.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}

} // IfStatementMapping
