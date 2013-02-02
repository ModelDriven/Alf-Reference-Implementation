
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
import org.modeldriven.alf.fuml.mapping.common.AssignedSourceMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.SignalDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.statements.AcceptBlock;
import org.modeldriven.alf.syntax.statements.Block;

import org.modeldriven.alf.uml.*;

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
        this.graph = this.createActivityGraph();
        
        AcceptBlock acceptBlock = this.getAcceptBlock();
        Collection<ElementReference> signalReferences = acceptBlock.getSignal();
        Block block = acceptBlock.getBlock();
        
        this.signals = new ArrayList<Signal>();
        for (ElementReference signalReference: signalReferences) {
            Signal signal = (Signal)signalReference.getImpl().getUml();
            if (signal == null) {
                FumlMapping mapping = this.fumlMap(signalReference);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (!(mapping instanceof SignalDefinitionMapping)) {
                    this.throwError("Error mapping signal " + signalReference + ": " + 
                            mapping.getErrorMessage());
                } else {
                    signal = (Signal)((SignalDefinitionMapping)mapping).getClassifier();
                }
            }
            this.signals.add(signal);
        }
        
        if (block != null) {
            FumlMapping mapping = this.fumlMap(block);
            this.blockNode = this.graph.addStructuredActivityNode(
                    "", mapping.getModelElements());
            
            ActivityNode signalSourceNode = parentMapping.getSignalSourceNode();
            StringBuilder signalNames = new StringBuilder();
            for (Signal signal: this.signals) {
                if (signalNames.length() > 0) {
                    signalNames.append(",");
                }
                signalNames.append(signal.getName());

                ReadIsClassifiedObjectAction testAction = 
                        this.graph.addReadIsClassifiedObjectAction(
                                signal, false);
                this.graph.addObjectFlow(signalSourceNode, testAction.getObject());

                this.decisionNode = 
                        this.graph.addControlDecisionNode(
                                "Test(" + signal.getName() + ")", 
                                null, testAction.getResult(), 
                                this.blockNode, this.decisionNode);
            }
            
            this.blockNode.setName("AcceptBlock(" + signalNames + ")");
            
            Map<String, AssignedSource> assignments = 
                    block.getImpl().getAssignmentAfterMap();
            for (String name: this.parentMapping.getAssignedNames()) {
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
                            ElementReference type = assignment.getType();
                            Classifier classifier = null;
                            if (type != null) {
                                classifier = (Classifier)type.getImpl().getUml();
                                if (classifier == null) {
                                    mapping = this.fumlMap(assignment.getType());
                                    if (mapping instanceof ElementReferenceMapping) {
                                        mapping = ((ElementReferenceMapping)mapping).getMapping();
                                    }
                                    if (!(mapping instanceof ClassifierDefinitionMapping)) {
                                        this.throwError("Error mapping type for " + 
                                                name + ": " + mapping.getErrorMessage());
                                    } else {
                                        classifier = 
                                                ((ClassifierDefinitionMapping)mapping).
                                                    getClassifier();
                                    }
                                }
                            }
                            StructuredActivityNode passthruNode =
                                    this.graph.createPassthruNode(
                                            name, classifier,
                                            assignment.getLower(), 
                                            assignment.getUpper());
                            blockNode.addNode(passthruNode);
                            this.graph.addObjectFlow(
                                    source, 
                                    passthruNode.getStructuredNodeInput().get(0));
                            source = passthruNode.getStructuredNodeOutput().get(0);
                        }
                        this.graph.addObjectFlow(
                                source, 
                                this.parentMapping.getAssignedValueSource(name).
                                    getIncoming().get(0).getSource());
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
