/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import java.util.Map;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.AssignedSourceMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.AssignedSource;
import org.modeldriven.alf.syntax.statements.BreakStatement;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.LoopNode;
import org.modeldriven.alf.uml.OutputPin;
import org.modeldriven.alf.uml.StructuredActivityNode;

public class BreakStatementMapping extends StatementMapping {

    /**
     * A break statement maps to an empty structured activity node with a
     * control flow to an activity final node. The activity final node is placed
     * in the outermost structured activity node mapped from the target
     * statement of the break statement.
     */
    
    @Override
    public void map() throws MappingError {
        super.map();
        
        BreakStatement statement = this.getBreakStatement();
        FumlMapping mapping = this.fumlMap(statement.getImpl().getTarget());
        if (!(mapping instanceof LoopStatementMapping)) {
            this.throwError("Error mapping target statement: " + 
                    mapping.getErrorMessage());
        } else {
            LoopStatementMapping loopMapping = (LoopStatementMapping)mapping;
            Element element = loopMapping.getElement();
            if (element instanceof LoopNode) {
                LoopNode loopNode = (LoopNode)element;
                Map<String, AssignedSource> bodyAssignments = 
                        loopMapping.getAssignments();
                Map<String, AssignedSource> assignmentsBefore = 
                        statement.getImpl().getAssignmentBeforeMap();
                
                // For each body output, ensure that the latest assigned source
                // for the corresponding local name is connected to the output.
                for (OutputPin bodyOutput: loopNode.getBodyOutput()) {
                    String name = bodyOutput.getName();
                    if (name != null) {
                        AssignedSource assignment = assignmentsBefore.get(name);
                        if (assignment != null &&                                 
                                // NOTE: This check ensures that the assigned source
                                // before the break is not already the final
                                // assigned source for the output.
                                assignment != bodyAssignments.get(name)) {
                            
                            StructuredActivityNode passthruNode = 
                                    this.graph.createPassthruNode(
                                            name, bodyOutput.getType(), 1, 1);
                            this.node.addNode(passthruNode);
                            this.graph.addObjectFlow(
                                    passthruNode.getStructuredNodeOutput().get(0), 
                                    bodyOutput);
                            
                            mapping = this.fumlMap(assignment);
                            if (!(mapping instanceof AssignedSourceMapping)) {
                                this.throwError("Error mapping assigned source for " + 
                                        name + ": " + mapping.getErrorMessage());
                            } else {
                                this.graph.addObjectFlow(
                                        ((AssignedSourceMapping)mapping).getActivityNode(),
                                        passthruNode.getStructuredNodeInput().get(0));
                            }
                        }
                    }
                }
            }
            
            this.add(this.graph.createControlFlow(
                    this.node, loopMapping.getFinalNode()));
        }
    }

    public BreakStatement getBreakStatement() {
        return (BreakStatement) this.getSource();
    }

} // BreakStatementMapping
