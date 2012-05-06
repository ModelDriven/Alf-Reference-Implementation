
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.AssignedSourceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.LeftHandSideMapping;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.NameLeftHandSide;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

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
                        this.throwError("Error mapping index expression: " + 
                                mapping.getErrorMessage());
                    } else {
                        ExpressionMapping indexMapping = (ExpressionMapping)mapping;
                        this.controlTarget = 
                            this.graph.addStructuredActivityNode(
                                    "Index(LeftHandSide@" + lhs.getId() +")", 
                                    indexMapping.getModelElements());
                        indexSource = indexMapping.getResultSource();
                    }
                }
                String name = lhs.getTarget().getUnqualifiedName().getName();
                AssignedSource assignment = lhs.getImpl().getAssignmentBefore(name);
                if (assignment != null) {
                    FumlMapping mapping = this.fumlMap(assignment);
                    if (!(mapping instanceof AssignedSourceMapping)) {
                        this.throwError("Error mapping assigned source: " + 
                                mapping.getErrorMessage());
                    } else {
                        ActivityNode activityNode = 
                            ((AssignedSourceMapping)mapping).getActivityNode();
                        if (activityNode == null) {
                            this.throwError("Invalid assigned source: " + assignment);
                        } else {
                            CallBehaviorAction callAction = 
                                this.graph.addCallBehaviorAction(getBehavior(
                                        RootNamespace.getSequenceFunctionReplacingAt()));
                            this.graph.addObjectFlow(
                                    activityNode, callAction.argument.get(0));
                            this.graph.addObjectFlow(
                                    indexSource, callAction.argument.get(1));
                            this.graph.addObjectFlow(
                                    this.resultSource, callAction.argument.get(2));

                            this.assignedValueSource =
                                this.graph.addForkNode(
                                        "Fork(" + lhs.getTarget().getPathName() + ")");
                            this.graph.addObjectFlow(
                                    callAction.result.get(0), this.assignedValueSource);

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
