
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
import org.modeldriven.alf.syntax.statements.ConcurrentClauses;
import org.modeldriven.alf.syntax.statements.IfStatement;

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

public class IfStatementMapping extends StatementMapping {
    
    Map<String, ActivityNode> assignedValueSourceMap = 
        new HashMap<String, ActivityNode>();

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
    
    // NOTE: See also ConcurrentClausesMapping and NonFinalClauseMapping.
    
    @Override
    public StructuredActivityNode mapNode() {
        return new ConditionalNode();
    }
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        ConditionalNode node = (ConditionalNode)this.getElement();
        IfStatement statement = this.getIfStatement();
        
        Collection<AssignedSource> assignments = new ArrayList<AssignedSource>();
        for (AssignedSource assignment: statement.getAssignmentAfter()) {
            if (assignment.getSource() == statement) {
                assignments.add(assignment);
                String name = assignment.getName();
                ElementReference type = assignment.getType();
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
                this.add(forkNode);
                this.add(ActivityGraph.createObjectFlow(outputPin, forkNode));                
            }
        }

        Collection<Clause> predecessorClauses = new ArrayList<Clause>();
        for (ConcurrentClauses nonFinalClauses: statement.getNonFinalClauses()) {
            FumlMapping mapping = this.fumlMap(nonFinalClauses);
            if (!(mapping instanceof ConcurrentClausesMapping)) {
                this.throwError("Error mapping non final clauses " + 
                        nonFinalClauses + ": " + mapping.getErrorMessage());
            } else {
                ConcurrentClausesMapping clausesMapping =
                    (ConcurrentClausesMapping)mapping;
                clausesMapping.setAssignments(assignments);
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
        
        Block finalClause = statement.getFinalClause();
        if (finalClause != null) {
            Collection<Element> modelElements = new ArrayList<Element>();
            FumlMapping mapping = this.fumlMap(finalClause);
            this.addToNode(mapping.getModelElements());
            ActivityGraph subgraph = new ActivityGraph();
            ValueSpecificationAction valueAction = 
                subgraph.addBooleanValueSpecificationAction(true);
            Clause clause = NonFinalClauseMapping.createClause(
                    subgraph.getModelElements(), valueAction.result, 
                    mapping.getModelElements(), 
                    assignments, 
                    modelElements, this);
            this.addToNode(modelElements);
            for (Clause predecessorClause: predecessorClauses) {
                clause.addPredecessorClause(predecessorClause);
            }
            node.addClause(clause);
        }
        
        node.setIsAssured(statement.getIsAssured());
        node.setIsDeterminate(statement.getIsDetermined());
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) {
        return this.assignedValueSourceMap.get(name);
    }
    
	public IfStatement getIfStatement() {
		return (IfStatement) this.getSource();
	}

} // IfStatementMapping
