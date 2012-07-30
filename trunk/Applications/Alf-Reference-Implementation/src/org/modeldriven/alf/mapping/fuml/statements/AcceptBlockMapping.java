
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
import org.modeldriven.alf.mapping.fuml.common.AssignedSourceMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.SignalDefinitionMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.statements.AcceptBlock;
import org.modeldriven.alf.syntax.statements.Block;

import fUML.Syntax.Actions.CompleteActions.ReadIsClassifiedObjectAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.CommonBehaviors.Communications.Signal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class AcceptBlockMapping extends SyntaxElementMapping {

    private ActivityGraph graph = null;
    private Collection<Signal> signals = null;
    private StructuredActivityNode blockNode = null;
    
    private AcceptStatementMapping parentMapping = null;
    private ActivityNode decisionNode = null;
    
    public void setParentMapping(AcceptStatementMapping parentMapping) {
        this.parentMapping = parentMapping;
    }
    
    public void setDecisionNode(ActivityNode decisionNode) {
        this.decisionNode = decisionNode;
    }
    
    public ActivityNode getDecisionNode() {
        return this.decisionNode;
    }
    
    private void map() throws MappingError {
        this.graph = new ActivityGraph();
        
        AcceptBlock acceptBlock = this.getAcceptBlock();
        Collection<ElementReference> signalReferences = acceptBlock.getSignal();
        Block block = acceptBlock.getBlock();
        
        this.signals = new ArrayList<Signal>();
        for (ElementReference signalReference: signalReferences) {
            FumlMapping mapping = this.fumlMap(signalReference);
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof SignalDefinitionMapping)) {
                this.throwError("Error mapping signal " + signalReference + ": " + 
                        mapping.getErrorMessage());
            } else {
                this.signals.add((Signal)((SignalDefinitionMapping)mapping).
                            getClassifier());
            }
        }
        
        if (block != null) {
            FumlMapping mapping = this.fumlMap(block);
            this.blockNode = graph.addStructuredActivityNode(
                    "", mapping.getModelElements());
            
            ActivityNode signalSourceNode = parentMapping.getSignalSourceNode();
            StringBuilder signalNames = new StringBuilder();
            for (Signal signal: this.signals) {
                if (signalNames.length() > 0) {
                    signalNames.append(",");
                }
                signalNames.append(signal.name);

                ReadIsClassifiedObjectAction testAction = 
                        graph.addReadIsClassifiedObjectAction(
                                signal, false);
                graph.addObjectFlow(signalSourceNode, testAction.object);

                this.decisionNode = 
                        graph.addControlDecisionNode(
                                "Test(" + signal.name + ")", 
                                null, testAction.result, 
                                this.blockNode, this.decisionNode);
            }
            
            this.blockNode.setName("AcceptBlock(" + signalNames + ")");
            
            Map<String, AssignedSource> assignments = 
                    block.getImpl().getAssignmentAfterMap();
            for (String name: parentMapping.getAssignedNames()) {
                AssignedSource assignment = assignments.get(name);
                if (assignment != null) {
                    mapping = this.fumlMap(assignment);
                    if (!(mapping instanceof AssignedSourceMapping)) {
                        this.throwError("Error mapping assigned source for " + 
                                name + ": " + mapping.getErrorMessage());
                    } else {
                        ActivityNode source = 
                                ((AssignedSourceMapping)mapping).getActivityNode();
                        if (!(ActivityGraph.isContainedIn(source, this.blockNode))) {
                            mapping = this.fumlMap(assignment.getType());
                            if (!(mapping instanceof ClassifierDefinitionMapping)) {
                                this.throwError("Error mapping type for " + 
                                        name + ": " + mapping.getErrorMessage());
                            } else {
                                Classifier type = 
                                        ((ClassifierDefinitionMapping)mapping).
                                            getClassifier();
                                StructuredActivityNode passthruNode =
                                        ActivityGraph.createPassthruNode(
                                                name, type,
                                                assignment.getLower(), 
                                                assignment.getUpper());
                                blockNode.addNode(passthruNode);
                                blockNode.addEdge(ActivityGraph.createObjectFlow(
                                        source, 
                                        passthruNode.structuredNodeInput.get(0)));
                                source = passthruNode.structuredNodeOutput.get(0);
                            }
                        }
                        graph.addObjectFlow(
                                source, 
                                parentMapping.getAssignedValueSource(name).
                                    incoming.get(0).source);
                    }
                }
            }
        }

        super.map(this.blockNode);
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) {
        return this.parentMapping.getSignalSourceNode();
    }
    
    public Collection<Signal> getSignals() throws MappingError {
        this.getModelElements();
        return this.signals;
    }
    
    @Override
    public Collection<Element> getModelElements() throws MappingError {
		if (this.graph == null) {
		    this.map();
		}
		return this.graph.getModelElements();
	}

	public AcceptBlock getAcceptBlock() {
		return (AcceptBlock) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    AcceptBlock acceptBlock = this.getAcceptBlock();
	    
	    if (this.signals != null) {
	        System.out.println(prefix + " signal:");
	        for (Signal signal: this.signals) {
	            System.out.println(prefix + "  " + signal);
	        }
	    }
	    
	    Block block = acceptBlock.getBlock();
	    if (block != null) {
	        System.out.println(prefix + " block:");
	        Mapping mapping = block.getImpl().getMapping();
	        if (mapping != null) {
	            mapping.printChild(prefix);
	        }
	    }
	}

} // AcceptBlockMapping
