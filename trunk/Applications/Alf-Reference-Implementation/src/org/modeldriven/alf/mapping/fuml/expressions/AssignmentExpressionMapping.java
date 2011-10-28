
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.AssignmentExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.LeftHandSide;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.IntermediateActions.AddStructuralFeatureValueAction;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Property;

public class AssignmentExpressionMapping extends ExpressionMapping {
    
    private LeftHandSideMapping lhsMapping = null;

    private void map() throws MappingError {
        AssignmentExpression assignmentExpression = this.getAssignmentExpression();
        LeftHandSide lhs = assignmentExpression.getLeftHandSide();
        Expression rhs = assignmentExpression.getRightHandSide();
        
        FumlMapping mapping = this.fumlMap(lhs);
        if (!(mapping instanceof LeftHandSideMapping)) {
            this.throwError("Error mapping left hand side: " + 
                    mapping.getErrorMessage());
        } else {
            this.lhsMapping = (LeftHandSideMapping)mapping;
            
            mapping = this.fumlMap(rhs);
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping right hand side: " + 
                        mapping.getErrorMessage());
            } else {    
                ExpressionMapping rhsMapping = (ExpressionMapping)mapping;
                
                if (assignmentExpression.getIsSimple()) {
                    this.graph.addAll(this.lhsMapping.getGraph());
                    this.graph.addAll(rhsMapping.getGraph());
                    
                    ActivityNode rhsResultSource = rhsMapping.getResultSource();                    
                    if (rhsResultSource != null) {
                        // TODO: Implement collection and bit string conversion.
                        this.graph.addObjectFlow(
                                rhsResultSource,
                                this.lhsMapping.getAssignmentTarget());
                    }                    
                }
            }
        }
        
        super.mapTo(this.lhsMapping.getNode());        
    }
    
    private LeftHandSideMapping getLhsMapping() throws MappingError {
        if (this.lhsMapping == null) {
            this.map();
        }
        return this.lhsMapping;        
    }
    
    public ActivityNode getNode() throws MappingError {
        return this.getLhsMapping().getNode();
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        return this.getLhsMapping().getResultSource();
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        return this.getLhsMapping().getAssignedValueSource(name);
    }
    
    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getLhsMapping();
		return super.getGraph();
	}

	public AssignmentExpression getAssignmentExpression() {
		return (AssignmentExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.lhsMapping != null) {
	        System.out.println(prefix + " leftHandSide:");
	        this.lhsMapping.printChild(prefix);
	    }
	    
	    AssignmentExpression assignmentExpression = this.getAssignmentExpression();
	    Expression rhs = assignmentExpression.getRightHandSide();
	    Mapping mapping = rhs.getImpl().getMapping();
	    
	    if (mapping != null) {
    	    System.out.println(prefix + " rightHandSide:");
    	    mapping.printChild(prefix);
	    }
	}
	
	public static ActivityNode mapPropertyAssignment(
	        Property property,
	        ActivityGraph graph,
	        ActivityNode objectSource,
	        ActivityNode valueSource)
	    throws MappingError {
	    ActivityGraph subgraph = new ActivityGraph();
	    
	    // Create write action for the property.  
        AddStructuralFeatureValueAction writeAction = 
            subgraph.addAddStructuralFeatureValueAction(property);

        // Connect the action object pin to the objectSource.
        subgraph.addObjectFlow(objectSource, writeAction.object);
        
        // For an ordered property, add an insertAt pin with a "*" input.
        if (property.multiplicityElement.isOrdered) {
            ValueSpecificationAction valueAction = 
                subgraph.addUnlimitedNaturalValueSpecificationAction(-1);
            
            writeAction.setInsertAt(ActivityGraph.createInputPin(
                    writeAction + ".insertAt", 
                    FumlMapping.getUnlimitedNaturalType(), 1, 1));
            
            subgraph.addObjectFlow(valueAction.result, writeAction.insertAt);
        }
        
        
        if (property.multiplicityElement.lower == 1 && 
                property.multiplicityElement.upper.naturalValue == 1) {
            // If the property multiplicity is 1..1, connect the valueSource to
            // the write action value input pin.
            subgraph.addObjectFlow(valueSource, writeAction.value);        
            writeAction.setIsReplaceAll(true);           
            graph.addAll(subgraph);
            return writeAction.result;
            
        } else {
            // Otherwise, create an expansion region to iteratively add
            // possibly multiple values to the property.
            Classifier featuringClassifier = property.featuringClassifier.get(0);
            ExpansionRegion region = graph.addExpansionRegion(
                    "Iterate(" + writeAction.name + ")", 
                    ExpansionKind.iterative, 
                    subgraph.getModelElements(), 
                    valueSource, writeAction.value, 
                    featuringClassifier instanceof DataType? 
                            writeAction.result: null);
            writeAction.setIsReplaceAll(false);            
            
            // If the property is a feature of a data type, then connect
            // the result output pin of the action to an output expansion
            // node and obtain the correct final data value from that
            // node.
            
            if (!(featuringClassifier instanceof DataType)) {
                return region.outputElement.get(0);                
            } else {
                ForkNode fork = 
                    graph.addForkNode("Fork(" + writeAction.result.name + " list)");
                CallBehaviorAction callSizeAction = 
                    graph.addCallBehaviorAction(
                            getBehavior(RootNamespace.getListFunctionSize()));
                CallBehaviorAction callGetAction = 
                    graph.addCallBehaviorAction(
                            getBehavior(RootNamespace.getListFunctionGet()));
                
                graph.addObjectFlow(region.outputElement.get(0), fork);                
                graph.addObjectFlow(fork, callSizeAction.argument.get(0));
                graph.addObjectFlow(fork, callGetAction.argument.get(0));
                graph.addObjectFlow(
                        callSizeAction.result.get(0), 
                        callGetAction.argument.get(1));
                
                return callGetAction.result.get(0);
            }
        }        
	}
	
} // AssignmentExpressionMapping
