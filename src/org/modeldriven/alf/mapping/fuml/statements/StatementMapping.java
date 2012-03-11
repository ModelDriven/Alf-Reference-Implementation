
/*
 * Copyright 2011-2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.statements;

import java.util.Collection;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.common.DocumentedElementMapping;

import org.modeldriven.alf.syntax.statements.Statement;

import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Activities.CompleteStructuredActivities.ConditionalNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Activities.IntermediateActivities.ObjectFlow;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class StatementMapping extends DocumentedElementMapping {

    private StructuredActivityNode node = null;
    private ActivityGraph graph = new ActivityGraph();
    
    /**
     * 1. Every statement is mapped to a single activity node (which may be a
     * structured activity node with nested structure).
     * 
     * 2. If the static analysis of assignment indicates that a name with an
     * assigned source has a different assigned source after a statement than
     * before the statement, and the statement maps to a structured activity
     * node (but not a conditional node or a loop node), then an input pin
     * corresponding to that name is added to the structured activity node. This
     * input pin is the target of an incoming object flow from the assigned
     * source of the name before the statement. The input pin is also connected
     * by an outgoing object flow to a fork node contained in the structured
     * activity node. This fork node acts as the assigned source for the name
     * within the mapping of the statement.
     * 
     * 3. A statement for which @isolated is allowed is always mapped to
     * structured activity node, and annotating it @isolated results in the
     * isIsolated property on this node being true.
     */
    
    // NOTE: Block mapping is handled in the BlockMapping class.
    
    public StructuredActivityNode mapNode() {
        return new StructuredActivityNode();
    }
    
    public void map() throws MappingError {
        super.mapTo(this.node);
        
        Statement statement = this.getStatement();
        String s = statement.getClass().getName();
        this.node.setName(
                s.substring(s.lastIndexOf(".")+1) + "@" + statement.getId());        
        this.node.setMustIsolate(statement.getIsIsolated());
        
        this.graph.add(this.node);
    }
    
    public void add(ActivityEdge edge) {
        this.graph.add(edge);
    }
    
    public void add(ActivityNode node) {
        this.graph.add(node);
    }
    
    public void addToNode(Collection<Element> elements) {
        this.graph.addToStructuredNode(this.node, elements);

        if (!(this.node instanceof ConditionalNode ||
                this.node instanceof LoopNode ||
                this.node instanceof ExpansionRegion)) {
            for (Element element: elements) {
                if (element instanceof ObjectFlow) {
                    ObjectFlow flow = (ObjectFlow)element;
                    if (!ActivityGraph.isContainedIn(flow.source, this.node) &&
                            ActivityGraph.isContainedIn(flow.target, this.node)) {
                        
                        // Check if there already is an input pin corresponding
                        // to the source of the flow.
                        InputPin pin = null;
                        search: 
                        for (InputPin input: node.structuredNodeInput) {
                            for (ActivityEdge incoming: input.incoming) {
                                if (incoming.source == flow.source) {
                                    pin = input;
                                    break search;
                                }
                            }
                        }
                        
                        // If not, create an input pin for the new source.
                        if (pin == null) {
                            pin = ActivityGraph.createInputPin(
                                    this.node.name + ".input(" + 
                                        flow.source.name + ")", 
                                    null, 0, -1);
                            this.node.addStructuredNodeInput(pin);
                            ForkNode fork = new ForkNode();
                            fork.setName("Fork(" + flow.source.name + ")");
                            this.node.addNode(fork);
                            this.node.addEdge(
                                    ActivityGraph.createObjectFlow(pin, fork));
                        }
                        
                        // Redirect the flow through the input pin.
                        ObjectFlow internalFlow = 
                            ActivityGraph.createObjectFlow(
                                    pin.outgoing.get(0).target, flow.target);
                        this.node.addEdge(internalFlow);
                        flow.target.incoming.remove(flow);
                        flow.setTarget(pin);
                    }
                }
            }
        }
    }
    
    public ActivityNode getNode() throws MappingError {
        if (this.node == null) {
            this.node = this.mapNode();
            this.map();
          }
          return this.node;
    }
    
	public Statement getStatement() {
		return (Statement) this.getSource();
	}
	
	public Element getElement() {
	    return this.node;
	}
	
	public ActivityGraph getGraph() throws MappingError {
	    this.getNode();
	    return this.graph;
	}
	
	@Override
	public Collection<Element> getModelElements() throws MappingError {
	    return this.getGraph().getModelElements();
	}
	
	@Override
	public String toString() {
	    return super.toString() + " node:" + this.node;
	}

} // StatementMapping
