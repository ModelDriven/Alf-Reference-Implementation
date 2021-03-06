/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.statements;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.SyntaxElementMapping;
import org.modeldriven.alf.fuml.mapping.expressions.AssignmentExpressionMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.fuml.mapping.expressions.SequenceRangeMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.statements.LoopVariableDefinition;

import org.modeldriven.alf.uml.ActivityNode;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;

import java.util.Collection;

public class LoopVariableDefinitionMapping extends SyntaxElementMapping {
    
    private boolean isIndexFrom0 = false;
    private boolean isParallel = false;
    private ActivityGraph graph = null;
    private ActivityNode resultSource1 = null;
    private ActivityNode resultSource2 = null;
    private ActivityNode assignedValueSource = null;
    private Classifier type = null;
    
    public void setIsIndexFrom0(boolean isIndexFrom0) {
        this.isIndexFrom0 = isIndexFrom0;
    }
    
    public void setIsParallel(boolean isParallel) {
        this.isParallel = isParallel;
    }
    
    public void map() throws MappingError {
        super.map(null);
        
        LoopVariableDefinition definition = this.getLoopVariableDefinition();
        Expression expression1 = definition.getExpression1();
        Expression expression2 = definition.getExpression2();
        
        FumlMapping mapping = this.fumlMap(expression1);
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping first expression", mapping);
        } else {
            ExpressionMapping expression1Mapping = (ExpressionMapping)mapping;
            expression1Mapping.setIsIndexFrom0(this.isIndexFrom0);
            this.type = expression1Mapping.getType();
            
            if (expression2 == null) {
                if (!definition.getIsCollectionConversion()) {
                    this.graph = expression1Mapping.getGraph();
                    this.resultSource1 = expression1Mapping.getResultSource();
                } else {
                    this.graph = this.createActivityGraph();
                    this.graph.addAll(expression1Mapping.getGraph());
                    this.resultSource1 = AssignmentExpressionMapping.mapConversions(
                            this, this.graph, 
                            expression1Mapping.getResultSource(), 
                            expression1.getType(), 
                            true, false, false, false);
                }
            } else {
                this.graph = this.createActivityGraph();
                this.graph.addAll(expression1Mapping.getGraph());
                this.resultSource1 = expression1Mapping.getResultSource();
                
                mapping = this.fumlMap(expression2);
                expression1Mapping.setIsIndexFrom0(this.isIndexFrom0);
                if (!(mapping instanceof ExpressionMapping)) {
                    this.throwError("Error mapping second expression", mapping);
                } else {
                    ExpressionMapping expression2Mapping = 
                            (ExpressionMapping)mapping;
                    this.graph.addAll(expression2Mapping.getGraph());
                    if (!this.isParallel && definition.getImpl().getIsFirst()) {
                        this.resultSource2 = expression2Mapping.getResultSource();
                    } else {
                        this.resultSource1 = 
                                SequenceRangeMapping.mapSequenceRangeLoop(
                                        this.graph, 
                                        this.resultSource1, 
                                        expression2Mapping.getResultSource(), 
                                        "SequenceRange(LoopVariable@" + 
                                                definition.getId() + ")");
                    }
                }
            }
        }
    }
    
    public ActivityNode getResultSource1() throws MappingError {
        if (this.resultSource1 == null) {
            this.map();
        }
        return this.resultSource1;
    }
    
    public ActivityNode getResultSource2() throws MappingError {
        this.getResultSource1();
        return this.resultSource2;
    }
    
    public Classifier getType() throws MappingError {
        this.getResultSource1();
        return this.type;
    }
    
    public void setAssignedValueSource(ActivityNode assignedValueSource) {
        this.assignedValueSource = assignedValueSource;
    }

    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        return this.assignedValueSource;
    }

    public ActivityGraph getGraph() throws MappingError {
        this.getResultSource1();
        return this.graph;
    }

    public Collection<Element> getModelElements() throws MappingError {
        return this.getGraph().getModelElements();
    }

	public LoopVariableDefinition getLoopVariableDefinition() {
		return (LoopVariableDefinition) this.getSource();
	}

} // LoopVariableDefinitionMapping
