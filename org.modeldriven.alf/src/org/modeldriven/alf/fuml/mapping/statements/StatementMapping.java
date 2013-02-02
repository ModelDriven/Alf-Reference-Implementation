
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.DocumentedElementMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.statements.Statement;

import org.modeldriven.alf.uml.*;

public abstract class StatementMapping extends DocumentedElementMapping {

    protected StructuredActivityNode node = null;
    protected ActivityGraph graph = this.createActivityGraph();
    
    protected Map<String, ActivityNode> assignedValueSourceMap = 
        new HashMap<String, ActivityNode>();
    
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
        return this.create(StructuredActivityNode.class);
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
    
    /**
     * Create an output pin for each assignment for which this statement
     * will be the assigned value source. Return the collection of names
     * from those assignments. (If mapAll is true, then map all assignments after
     * the statement, not just those with the statement as the source.)
     */
    protected List<String> mapAssignedValueSources(
            StructuredActivityNode node,
            ActivityGraph graph, 
            boolean mapAll) throws MappingError {
        Statement statement = this.getStatement();
        
        List<String> assignedNames = new ArrayList<String>();
        for (AssignedSource assignment: statement.getAssignmentAfter()) {
            boolean statementIsSource = assignment.getSource() == statement;
            if (mapAll || statementIsSource) {
                String name = assignment.getName();
                ElementReference type = assignment.getType();
                assignedNames.add(name);
                
                Classifier classifier = null;
                if (type != null) {
                    classifier = (Classifier)type.getImpl().getUml();
                    if (classifier == null) {
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
                }
                
                OutputPin outputPin = this.mapAssignment(
                        node, name, classifier, 
                        assignment.getLower(), assignment.getUpper());
                
                if (statementIsSource) {
                    ForkNode forkNode = create(ForkNode.class);
                    forkNode.setName("Fork(" + name + ")");
                    this.assignedValueSourceMap.put(name, forkNode);
                    graph.add(forkNode);
                    graph.add(this.graph.createObjectFlow(outputPin, forkNode));
                }
            }
        }
        return assignedNames;
    }

    protected OutputPin mapAssignment(
            StructuredActivityNode node, String name, Classifier classifier, 
            int lower, int upper) throws MappingError {
        OutputPin outputPin = this.graph.createOutputPin(
                node.getName() + 
                ".output(" + name + ")", 
                classifier,
                lower,
                upper);
        node.addStructuredNodeOutput(outputPin);
        return outputPin;
    }

    public void add(ActivityEdge edge) {
        this.graph.add(edge);
    }
    
    public void add(ActivityNode node) {
        this.graph.add(node);
    }
    
    public void addToNode(Collection<Element> elements) {
        this.graph.addToStructuredNode(this.node, elements);
        
        // NOTE: Adding input pins to structured activity nodes is removed.
        // This is not a good idea, because it is not possible in general to set
        // the input pin multiplicities properly, and setting them to 0..*
        // allows the structured activity node to fire even when an input that
        // is really required is not yet available.
        /*
        if (!(this.node instanceof ConditionalNode ||
                this.node instanceof LoopNode ||
                this.node instanceof ExpansionRegion)) {
            for (Element element: elements) {
                if (element instanceof ObjectFlow) {
                    ObjectFlow flow = (ObjectFlow)element;
                    if (!ActivityGraph.isContainedIn(flow.source, this.node) &&
                            ActivityGraph.isContainedIn(flow.target, this.node)) {
                        
                        System.out.println("[addToNode] flow.source=" + flow.source.name +
                                " flow.target=" + flow.target.name);
                        
                        // Check if there already is an input pin corresponding
                        // to the source of the flow.
                        InputPin pin = null;
                        search: 
                        for (InputPin input: this.node.structuredNodeInput) {
                            System.out.println("[addToNode] input=" + input.name);
                            for (ActivityEdge incoming: input.incoming) {
                                System.out.println("[addToNode] incoming.source=" + 
                                        incoming.source.name);
                                if (incoming.source.equals(flow.source)) {
                                    pin = input;
                                    break search;
                                }
                            }
                        }
                        
                        ActivityNode target = flow.target;
                        
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
                            target.incoming.remove(flow);
                            flow.setTarget(pin);
                        }
                        
                        ActivityEdge internalFlow = 
                            ActivityGraph.createObjectFlow(
                                pin.outgoing.get(0).target, target);
                        this.node.addEdge(internalFlow);
                    }
                }
            }
        }
        */
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
	
    @Override
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        this.getNode();
        if (this.assignedValueSourceMap == null) {
            return super.getAssignedValueSource(name);
        } else {
            return this.assignedValueSourceMap.get(name);
        }
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
