
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.Collection;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.ExtentOrExpression;
import org.modeldriven.alf.syntax.expressions.SequenceExpansionExpression;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Activities.IntermediateActivities.MergeNode;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class SequenceExpansionExpressionMapping extends
		ExpressionMapping {
    
    protected ExpansionRegion region = null;
    protected ForkNode variableSource = null;
    protected ActivityNode resultSource = null;

    /**
     * 1. A sequence expansion expression maps to an expansion region with a
     * single input expansion node. Except for the iterate operation, the
     * expansion region has mode=parallel. For the iterate operation, the
     * expansion region has mode=iterative.
     * 
     * 2. The input expansion node has the same type as the primary expression.
     * It is the target of an object flow from the result source element of the
     * mapping of the primary expression.
     * 
     * 3. The argument expression is mapped inside the expansion region. The
     * input expansion node is connected by an object flow to a fork node within
     * the expansion region that acts as the assigned source for references to
     * the expansion variable within the mapping of the argument expression.
     * 
     * 4. The specific mapping for each kind of sequence expansion operation is
     * determined by the corresponding subclasses of this class.
     */
    
    @Override
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        if (this.getSequenceExpansionExpression().getVariable().equals(name)) {
            return this.variableSource;
        } else {
            return null;
        }
    }
    
    // NOTE: This default behavior is used for select, reject, forAll, exists
    // (with modification) and one expressions.
    protected ActivityNode mapNestedGraph(
            String operation,
            ActivityNode variableSource, 
            ActivityGraph nestedGraph,
            ActivityNode resultNode) throws MappingError {
        String label = operation + "@" + this.getExpression().getId();
        boolean isSelect = this.isSelectLike(operation);
        
        // NOTE: A merge node is used here to provide a result source node for
        // the test.
        MergeNode mergeNode = nestedGraph.addMergeNode("Merge("+ label + ")");
        
        if (resultNode != null) {
            nestedGraph.addObjectDecisionNode(
                    label, variableSource, resultNode, 
                    isSelect? mergeNode: null, isSelect? null: mergeNode);
        }
        
        return mergeNode;
    }
    
    protected boolean isSelectLike(String operation) {
        return true;
    }
    
    protected void mapRegion(
            String operation,
            Expression expression, 
            ActivityGraph nestedGraph,
            ActivityNode argumentSource,
            ActivityNode primarySource,
            ActivityNode variableSource) throws MappingError {
        
        ActivityNode nestedResultSource = this.mapNestedGraph(
                operation,
                variableSource, 
                nestedGraph, 
                argumentSource);
        
        this.region = this.graph.addExpansionRegion(
                expression.getClass().getSimpleName()+ 
                    "@" + expression.getId(), 
                ExpansionKind.parallel, 
                nestedGraph.getModelElements(), 
                primarySource, 
                variableSource, 
                nestedResultSource);
        
        this.resultSource = this.region.outputElement.get(0);
        
        this.mapTo(this.region);
    }
            
    public void map() throws MappingError {
        SequenceExpansionExpression expression = 
            this.getSequenceExpansionExpression();
        
        ExtentOrExpression primary = expression.getPrimary();
        FumlMapping mapping = this.fumlMap(
                primary == null? null: primary.getExpression());
        if (!(mapping instanceof ExpressionMapping)) {
            this.throwError("Error mapping primary expression: " + 
                    mapping.getErrorMessage());
        } else {
            ExpressionMapping primaryMapping = (ExpressionMapping)mapping;
            this.graph.addAll(primaryMapping.getGraph());
            ActivityGraph nestedGraph = new ActivityGraph();
            this.variableSource = nestedGraph.addForkNode(
                    "Fork(" + expression.getVariable() + ")");
            
            mapping = this.fumlMap(expression.getArgument());
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping argument: " + 
                        mapping.getErrorMessage());
            } else {
                ExpressionMapping argumentMapping = (ExpressionMapping)mapping;                
                nestedGraph.addAll(argumentMapping.getGraph());
                
                this.mapRegion(
                        expression.getOperation(),
                        expression, nestedGraph, 
                        argumentMapping.getResultSource(), 
                        primaryMapping.getResultSource(), 
                        this.variableSource);
                this.region.inputElement.get(0).setType(primaryMapping.getType());
                this.region.outputElement.get(0).setType(primaryMapping.getType());
            }
        }
    }
        
    //Used by subclasses
    protected ActivityNode addTermination(
            ActivityGraph nestedGraph, ActivityNode resultSource) {
        ForkNode forkNode = 
            nestedGraph.addForkNode("Fork(" + resultSource.name + ")");
        nestedGraph.addObjectFlow(resultSource, forkNode);
        
        // NOTE: The use of an enclosing structured activity node here is to
        // allow the value produced by the computation be offered to the
        // output expansion node before the termination of the expansion region
        // by the activity final node.
        Collection<Element> elements = nestedGraph.getModelElements();
        nestedGraph.clear();
        ActivityNode structuredNode = nestedGraph.addStructuredActivityNode(
                "Compute(" + resultSource.name + ")", elements);
        ActivityNode joinNode = nestedGraph.addJoinNode(
                "Join(" + resultSource.name + ")");
        ActivityNode finalNode = 
            nestedGraph.addMergeNode(
                    "ActivityFinal(" + resultSource.name + ")");
        nestedGraph.addObjectFlow(forkNode, joinNode);
        nestedGraph.addControlFlow(structuredNode, joinNode);
        nestedGraph.addObjectFlow(joinNode, finalNode);
        
        return forkNode;
    }
    
    // Used by subclasses
    protected void addBehaviorCall(ElementReference behaviorReference) 
        throws MappingError {
        CallBehaviorAction callAction = 
            this.graph.addCallBehaviorAction(getBehavior(behaviorReference));
        this.graph.addObjectFlow(this.resultSource, callAction.argument.get(0));
        this.resultSource = callAction.result.get(0);
    }
    
    public ExpansionRegion getRegion() throws MappingError {
        this.getResultSource();
        return this.region;
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        if (this.resultSource == null) {
            this.map();
            this.mapTo(this.region);
        }
        return this.resultSource;
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getResultSource();
        return super.getGraph();
    }

    public SequenceExpansionExpression getSequenceExpansionExpression() {
		return (SequenceExpansionExpression) this.getSource();
	}
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        
        if (this.region != null) {
            System.out.println(prefix + " region: " + this.region);
        }
        
        if (this.resultSource != null) {
            System.out.println(prefix + " resultSource: " + this.resultSource);
        }
        
        this.printChildren(prefix);
    }
    
    protected void printChildren(String prefix) {
        SequenceExpansionExpression expression = 
            this.getSequenceExpansionExpression();
        
        ExtentOrExpression primary = expression.getPrimary();
        Expression primaryExpression = 
            primary == null? null: primary.getExpression();
        if (primaryExpression != null) {
            System.out.println(prefix + " primary:");
            Mapping mapping = primaryExpression.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }
        
        Expression argument = expression.getArgument();
        if (argument != null) {
            System.out.println(prefix + " argument:");
            Mapping mapping = argument.getImpl().getMapping();
            if (mapping != null) {
                mapping.printChild(prefix);
            }
        }        
    }

} // SequenceExpansionExpressionMapping
