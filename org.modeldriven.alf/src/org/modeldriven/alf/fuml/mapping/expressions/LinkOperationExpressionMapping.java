
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import java.util.List;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.InvocationExpressionMapping;
import org.modeldriven.alf.fuml.mapping.units.AssociationDefinitionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.LinkOperationExpression;

import org.modeldriven.alf.uml.*;

public class LinkOperationExpressionMapping extends InvocationExpressionMapping {

    /**
     * 1. A link operation expression for the operation createLink maps to a
     * create link action for the named association with isReplaceAll=false for
     * all ends. The value input pin of the end creation data for each end of
     * the association is the target of an object flow from the result source
     * element of the mapping of the corresponding argument expression. If an
     * association end is ordered, then the insertAt input pin for that end is
     * the target of an object flow from the result source element of the
     * mapping of the corresponding index expression (which defaults to * if not
     * given explicitly).
     * 
     * 2. A link operation expression for the operation destroyLink maps to a
     * destroy link action for the named association. The value input pin of the
     * end creation data for each end of the association is the target of an
     * object flow from the result source element of the mapping of the
     * corresponding argument expression. If an association end is unordered,
     * then isDestroyDuplicates=true. If an association end is ordered, then
     * isDestroyDuplicates=false and the insertAt input pin for that end is the
     * target of an object flow from the result source element of the mapping of
     * the corresponding index expression (which defaults to * if not given
     * explicitly).
     * 
     * 3. A link operation expression for the link operation clearAssoc maps to
     * a clear association action for the named association. The object input
     * pin of clear association action is the target of an object flow from the
     * result source element of the the mapping of the argument expression.
     */

    @Override
    public Action mapTarget() throws MappingError {
        LinkOperationExpression expression = this.getLinkOperationExpression();
        ElementReference referent = expression.getReferent();
        
        Action action = null;
        Association association = (Association)referent.getImpl().getUml();
        if (association == null) {
            FumlMapping mapping = this.fumlMap(expression.getReferent());
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof AssociationDefinitionMapping)) {
                this.throwError("Error mapping association: " + mapping.getErrorMessage());
            } else {
                association = (Association)
                    ((AssociationDefinitionMapping)mapping).getClassifier();
            }
        }
        action = 
            expression.getIsClear()?
                this.graph.addClearAssociationAction(association):
            expression.getIsCreation()?
                this.graph.addCreateLinkAction(association):
                this.graph.addDestroyLinkAction(association);
        if (expression.getIsCreation()) {
            List<LinkEndCreationData> endDataList = 
                    ((CreateLinkAction)action).getEndData();
            if (association.getMemberEnd().size() == 2) {
                // For a binary association, setting isReplaceAll=true on 
                // the opposite end of an end with multiplicity upper bound of 1
                // ensures that the upper bound is maintained.
                LinkEndCreationData endData1 = endDataList.get(0);
                LinkEndCreationData endData2 = endDataList.get(1);
                if (endData1.getEnd().getUpper() == 1) {
                    endData2.setIsReplaceAll(true);
                }
                if (endData2.getEnd().getUpper() == 1) {
                    endData1.setIsReplaceAll(true);
                }
            } else {
                // For a non-binary association, specific links need to be
                // found and destroyed to maintain any upper bound
                // multiplicities of 1.
                boolean hasUpperBound1 = false;
                for (LinkEndCreationData endData: endDataList) {
                    if (endData.getEnd().getUpper() == 1) {
                        hasUpperBound1 = true;
                        break;
                    }
                }
                if (hasUpperBound1) {
                    StructuredActivityNode node = 
                            this.graph.addStructuredActivityNode(
                                    "CreateLink(" + association.getName() + ")", null);
                    this.graph.remove(action);
                    ActivityGraph subgraph = this.createActivityGraph();
                    subgraph.add(action);
                    for (LinkEndCreationData endData: endDataList) {
                        Property end = endData.getEnd();
                        InputPin inputPin = this.graph.createInputPin(
                                node.getName() + ".input(" + end.getName() + ")", 
                                end.getType(), 
                                end.getLower(), 
                                end.getUpper());
                        inputPin.setIsOrdered(end.getIsOrdered());
                        node.addStructuredNodeInput(inputPin);
                        ActivityNode forkNode = 
                                subgraph.addForkNode("Fork" + end.getName() + ")");
                        subgraph.addObjectFlow(inputPin, forkNode);
                        subgraph.addObjectFlow(forkNode, endData.getValue());
                        if (end.getIsOrdered()) {
                            inputPin = this.graph.createInputPin(
                                    "Index(" + end.getName() + ")", 
                                    getUnlimitedNaturalType(), 1, 1);
                            node.addStructuredNodeInput(inputPin);
                            subgraph.addObjectFlow(inputPin, endData.getInsertAt());
                        }
                    }
                    ActivityGraph destroyGraph = this.createActivityGraph();
                    for (int i = 0; i < endDataList.size(); i++) {
                        Property end = endDataList.get(i).getEnd();
                        if (end.getUpper() == 1) {
                            ReadLinkAction readAction = 
                                    destroyGraph.addReadLinkAction(end);
                            DestroyLinkAction destroyAction = 
                                    destroyGraph.addDestroyLinkAction(association);
                            for (int j = 0, k = 0; j < endDataList.size(); j++, k++) {
                                if (j == i) {
                                    destroyGraph.addObjectFlow(
                                            readAction.getResult(), 
                                            destroyAction.getInputValue().get(j));
                                } else {
                                    InputPin inputPin = 
                                            node.getStructuredNodeInput().get(k);
                                    ActivityNode forkNode = 
                                            inputPin.getOutgoing().get(0).getTarget();
                                    destroyGraph.addObjectFlow(
                                            forkNode, 
                                            readAction.getInputValue().get(j < i? j: j-1));
                                    destroyGraph.addObjectFlow(
                                            forkNode, 
                                            destroyAction.getInputValue().get(j));
                                }
                                if (endDataList.get(j).getEnd().getIsOrdered()) {
                                    k++;
                                }
                            }
                        }
                    }
                    ActivityNode destroyNode = 
                            subgraph.addStructuredActivityNode(
                                    "DestroyLinks", 
                                    destroyGraph.getModelElements());
                    subgraph.addControlFlow(destroyNode, action);
                    this.graph.addToStructuredNode(
                            node, subgraph.getModelElements());
                    action = node;
                }
            }
        }
        return action;
    }

    public LinkOperationExpression getLinkOperationExpression() {
		return (LinkOperationExpression) this.getSource();
	}

} // LinkOperationExpressionMapping
