
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.statements;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.statements.StatementMapping;

import org.modeldriven.alf.syntax.statements.AcceptBlock;
import org.modeldriven.alf.syntax.statements.AcceptStatement;

import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.CompleteActions.AcceptEventAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.CommonBehaviors.Communications.Signal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AcceptStatementMapping extends StatementMapping {
    
    private ActivityNode signalSourceNode = null;

    /**
     * Simple accept Statements
     * 
     * 1. A simple accept statement maps to an accept event action with triggers
     * for each of the named signals. If the accept statement defines a local
     * name, then the result output pin of the accept event action is connected
     * by an object flow to a fork node, which acts as the assigned source for
     * the local name. Otherwise the accept event action has no result output
     * pin.
     * 
     * Compound accept Statements
     * 
     * 2. A compound accept statement maps to a structured activity node
     * containing an accept action with triggers for each of the signals
     * mentioned in any of the clauses of the accept statement. The result
     * output pin of the accept event action is connected by an object flow to a
     * fork node, which is further connected to conditional logic that tests the
     * type of the received signal.
     * 
     * 3. Each block in a compound accept statement is mapped as a block
     * statement, with a control flow from the conditional logic mentioned
     * above, corresponding to the appropriate type(s) of signals for that
     * block. The fork node mentioned above acts as the source for the assigned
     * value of the local name defined in the accept clause for the block, if
     * any.
     * 
     * 4. For any name assigned within one or more blocks of the accept
     * statement, a corresponding output pin is added to the structured activity
     * node mapped from the accept statement. This output pin becomes the source
     * for the assigned value for the name after the accept statement. 
     
       [Actually, the source should be a fork node attached to the output pin.]
      
     * The assigned source from each block assigning the name is connected by an
     * object flow to the corresponding output pin. For each block that does not
     * assign the name, a structured activity node is added to the mapping of
     * the block as follows: The structured activity node has one input pin and
     * one output pin, with an object flow from the input pin to the output pin
     * contained within the structured activity node. There is an object flow
     * from the assigned source of the name to the input pin of the structured
     * activity node. There is an object flow from the output pin of the
     * structured activity node to the corresponding output pin for the name of
     * the enclosing structured activity node for the accept statement.
     */
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        AcceptStatement statement = this.getAcceptStatement();
        Collection<AcceptBlock> acceptBlocks = statement.getAcceptBlock();
        
        ActivityGraph subgraph = new ActivityGraph();
        boolean hasOutput = !statement.getIsSimple() || 
                ((AcceptBlock)acceptBlocks.toArray()[0]).getName() != null;
        Set<Signal> signals = new HashSet<Signal>();

        if (hasOutput) {
            this.signalSourceNode = subgraph.addForkNode("");
        }
        
        if (!statement.getIsSimple()) {
            this.mapAssignedValueSources(this.node, subgraph, false);
        }

        ActivityNode decisionNode = null;
        for (AcceptBlock acceptBlock: acceptBlocks) {
            FumlMapping mapping = this.fumlMap(acceptBlock);
            if (!(mapping instanceof AcceptBlockMapping)) {
                this.throwError("Error mapping " + acceptBlock + ": " + 
                        mapping.getErrorMessage());
            } else {
                AcceptBlockMapping acceptBlockMapping = 
                        (AcceptBlockMapping)mapping;
                acceptBlockMapping.setParentMapping(this);
                acceptBlockMapping.setDecisionNode(decisionNode);
                this.addToNode(acceptBlockMapping.getModelElements());
                signals.addAll(acceptBlockMapping.getSignals());
                decisionNode = acceptBlockMapping.getDecisionNode();
            }
        }
        
        AcceptEventAction acceptAction = 
                subgraph.addAcceptEventAction(signals, hasOutput);
        
        // NOTE: This prevents the accept event action from re-registering itself
        // after it has completed.
        ActivityNode initialNode = subgraph.addInitialNode(
                "Initial(" + acceptAction.name + ")");
        subgraph.addControlFlow(initialNode, acceptAction);

        if (decisionNode != null) {
            subgraph.addControlFlow(acceptAction, decisionNode);
        }

        if (hasOutput) {
            OutputPin outputPin = acceptAction.result.get(0);
            this.signalSourceNode.setName("Fork(" + outputPin.name + ")");
            subgraph.addObjectFlow(outputPin, this.signalSourceNode);
        }
        
        this.addToNode(subgraph.getModelElements());
        
    }
    
    public ActivityNode getSignalSourceNode() {
        return this.signalSourceNode;
    }
    
    public Collection<String> getAssignedNames() {
        return this.assignedValueSourceMap.keySet();
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        if (this.getAcceptStatement().getIsSimple()) {
            this.getNode();
            return this.signalSourceNode;
        } else {
            return super.getAssignedValueSource(name);
        }
    }
    
    public AcceptStatement getAcceptStatement() {
		return (AcceptStatement) this.getSource();
	}
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        AcceptStatement statement = this.getAcceptStatement();
        Collection<AcceptBlock> acceptBlocks = statement.getAcceptBlock();
        if (!acceptBlocks.isEmpty()) {
            System.out.println(prefix + " acceptBlock:");
            for (AcceptBlock acceptBlock: acceptBlocks) {
                Mapping mapping = acceptBlock.getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix);
                }
            }
        }
    }

} // AcceptStatementMapping
