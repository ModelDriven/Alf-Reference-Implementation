/*******************************************************************************
 * Copyright 2011-2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.AssignedSourceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.LeftHandSideMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.NameLeftHandSide;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.CallBehaviorAction;
import org.modeldriven.alf.uml.StructuredActivityNode;
import org.modeldriven.alf.uml.ActivityNode;

public class NameLeftHandSideMapping extends LeftHandSideMapping {
    
    /**
    * 1. If the left-hand side is a name without an index, then a simple
    * assignment maps to a fork node. The result source element from the
    * mapping of the right-hand side is connected to the fork node by an object
    * flow. The fork node is the result source element for the assignment
    * expression and also the source for the assigned value for the name.
    * 
    * 2. If the left-hand side is a name with an index, then a simple
    * assignment maps to a call behavior action for the library behavior
    * Alf::Library::SequenceFunctions::ReplaceAt. The assigned source for the
    * name from the left-hand side is connected by an object flow to the seq
    * argument input pin of the call behavior action. The result source element
    * from the mapping of the right-hand side is connected to the element
    * argument input pin and the result source element from the mapping of the
    * index expression is connected to the index argument input pin. The seq
    * output pin of the call behavior action is connected by an object flow to
    * a fork node, which is the result source element for the assignment
    * expression and also the source for the assigned value for the name after
    * the expression.
    */
    
    @Override
    public void map() throws MappingError {
        NameLeftHandSide lhs = this.getNameLeftHandSide();
        if (lhs.getImpl().getFeature() != null) {
            super.map();
        } else {            
            Expression index = lhs.getIndex();
            if (index == null) {
                this.resultSource = this.graph.addForkNode(
                        "Fork(" + lhs.getTarget().getPathName() + ")@" + lhs.getId());
                this.assignedValueSource = this.resultSource;
                this.node = this.resultSource;
            } else {
                this.resultSource = this.graph.addForkNode("Fork(LeftHandSide@" + 
                        this.getLeftHandSide().getId() + ")");
                ActivityNode indexSource = this.getIndexSource();
                if (indexSource == null) {
                    FumlMapping mapping = this.fumlMap(index);
                    if (!(mapping instanceof ExpressionMapping)) {
                        this.throwError("Error mapping index expression", mapping);
                    } else {
                        ExpressionMapping indexMapping = (ExpressionMapping)mapping;
                        indexMapping.setIsIndexFrom0(this.isIndexFrom0);
                        indexSource = indexMapping.getResultSource();
                        this.controlTarget = 
                            this.graph.addStructuredActivityNode(
                                    "Index(LeftHandSide@" + lhs.getId() +")", 
                                    indexMapping.getModelElements());
                        
                        // Adjust for indexing from 0, if necessary.
                        if (this.isIndexFrom0) {
                            ActivityGraph subgraph = this.createActivityGraph();
                            indexSource = TupleMapping.mapIncrement(subgraph, indexSource);
                            this.graph.addToStructuredNode(
                                    (StructuredActivityNode)this.controlTarget, 
                                    subgraph.getModelElements());
                        }
                        
                     }
                }
                String name = lhs.getTarget().getUnqualifiedName().getName();
                AssignedSource assignment = lhs.getImpl().getAssignmentBefore(name);
                if (assignment != null) {
                    FumlMapping mapping = this.fumlMap(assignment);
                    if (!(mapping instanceof AssignedSourceMapping)) {
                        this.throwError("Error mapping assigned source", mapping);
                    } else {
                        ActivityNode activityNode = 
                            ((AssignedSourceMapping)mapping).getActivityNode();
                        if (activityNode == null) {
                            this.throwError("Invalid assigned source: " + assignment);
                        } else {
                            CallBehaviorAction callAction = 
                                this.graph.addCallBehaviorAction(getBehavior(
                                        RootNamespace.getRootScope().getSequenceFunctionReplacingAt()));
                            this.graph.addObjectFlow(
                                    activityNode, callAction.getArgument().get(0));
                            this.graph.addObjectFlow(
                                    indexSource, callAction.getArgument().get(1));
                            this.graph.addObjectFlow(
                                    this.resultSource, callAction.getArgument().get(2));
                            
                            if (this.controlTarget == null) {
                                this.controlTarget = callAction;
                            } else {
                                this.graph.addControlFlow(this.controlTarget, callAction);
                            }

                            this.assignedValueSource =
                                this.graph.addForkNode(
                                        "Fork(" + lhs.getTarget().getPathName() + ")");
                            this.graph.addObjectFlow(
                                    callAction.getResult().get(0), this.assignedValueSource);

                            this.node = callAction;
                        }
                    }
                }
            }
            this.assignmentTarget = this.resultSource;
        }
    }

	public NameLeftHandSide getNameLeftHandSide() {
		return (NameLeftHandSide) this.getSource();
	}
	
} // NameLeftHandSideMapping
