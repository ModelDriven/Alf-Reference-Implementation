
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.ArrayList;
import java.util.Collection;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;
import org.modeldriven.alf.mapping.fuml.units.PropertyDefinitionMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.FeatureReference;
import org.modeldriven.alf.syntax.expressions.LeftHandSide;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.IntermediateActions.AddStructuralFeatureValueAction;
import fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction;
import fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Property;

public abstract class LeftHandSideMapping extends SyntaxElementMapping {
    
    protected ActivityGraph graph = new ActivityGraph();
    protected ForkNode resultSource;
    protected ActivityNode node = null;
    protected ActivityNode assignedValueSource = null;
    protected ActivityNode assignmentTarget = null;
    protected ActivityNode controlTarget = null;
    
    protected ActivityNode indexSource = null;
    protected ActivityNode objectSource = null;
    
    private int rhsUpper = -1;

    /**
     * Simple Assignment: Feature Left-Hand Side, without Index
     * 
     * 1. If the left-hand side is a property reference but has no index, then
     * the mapping of a simple assignment depends on the multiplicity upper
     * bound of the right-hand side expression.
     * 
     * 2. If the right-hand side expression has a multiplicity upper bound of 0,
     * then the left-hand side maps to a clear structural feature action for the
     * identified property. There is no result source element for the
     * assignment.
     * 
     * 3. If the right-hand side expression has a multiplicity upper bound of 1,
     * then the left-hand side maps to an add structural feature value action
     * for the identified property with isReplaceAll=true. The result source
     * element from the mapping of the right-hand side expression is connect by
     * an object flow to a fork node that has a further object flow to the value
     * input pin of the add structural feature value action. The fork node is
     * the result source element for the assignment.
     * 
     * 4. Otherwise, the left-hand side maps to a clear structural feature value
     * action for the identified property followed by an expansion region. The
     * result source element from the mapping of the righthand side expression
     * is connected by an object flow to a fork node that has a further object
     * flow to an input expansion node of the expansion region. The expansion
     * region contains an add structural feature value action for the property
     * with isReplaceAll=false and an incoming object flow from the input
     * expansion node to its value input pin. If the property is ordered, then
     * the insertAt input pin has an incoming object flow from a value
     * specification action for the unbounded value *. The fork node is the
     * result source element for the assignment.
     * 
     * Feature Left-Hand Side, with Index
     * 
     * 5. If the left-hand side has an index, then the mapping of the left-hand
     * side includes a structured activity node containing the mapping of the
     * index expression. The further mapping of the left-hand side then depends
     * on the multiplicity upper bound of the right-hand side expression.
     * 
     * 6. If the right-hand side expression has a multiplicity upper bound of 0,
     * then the left-hand side maps to a remove structural feature value action
     * for the identified property with isRemoveDuplicates=false and an incoming
     * object flow into its removeAt input pin from the result source element
     * from the mapping of the index expression. There is no result source
     * element for the assignment.
     * 
     * 7. If the right-hand side expression has a multiplicity upper bound of 1,
     * then the simple assignment maps to a remove structural feature value
     * action for the identified property with isRemoveDuplicates=false followed
     * by an add structural feature value action with isReplaceAll=false. The
     * result source element of the mapping of the index expression is connected
     * by an object flow to a fork node, which then has object flows to the
     * removeAt input pin of the remove structural feature value action and the
     * insertAt input pin of the add structural feature value action. The
     * right-hand side expression is mapped inside a structured activity node,
     * which is connected by a control flow to the structured activity node for
     * the index expression. The result source element of the mapping of the
     * right-hand side expression is connected by an object flow to the value
     * input pin of the add structural feature value action. The fork node is
     * the result source element for the assignment.
     * 
     * Feature Left-Hand Side, Data Value Attribute Update
     * 
     * 8. If the left-hand side (with or without index) is a data value
     * attribute update, then a fork node is added to the mapping for the
     * left-hand side to be used as the source element for the assigned value of
     * the name. The fork node is the target of an object flow whose source is
     * determined as follows: If the mapping includes a remove structural
     * feature action, but no add structural feature action, then the result
     * output pin of the remove structural feature action is used. If the
     * mapping includes an add structural feature action not in an expansion
     * region, then the result output pin of the add structural feature action
     * is used. If the mapping has an add structural feature action in an
     * expansion region, then an output expansion node is added to the expansion
     * region and the result output pin of the add structural feature action is
     * connected to the output expansion node by an object flow. The output
     * expansion node is then connected by an object flow to a mapping of the
     * expression ListGet(x,ListSize(x)), where x represents the object flow
     * from the expansion node.
     */
    
    // NOTE: The mapping for feature left-hand sides is handled in this class,
    // since a NameLeftHandSide with a target that disambiguates to a feature
    // must also be mapped that way. The mapping for a name left-hand side with
    // a target that is not a feature is handled in the NameLeftHandSide class.
    
    // This operation must be called with the RHS upper bound multiplicity
    // BEFORE the LHS mapping is carried out.
    public void setRhsUpper(int rhsUpper) {
        this.rhsUpper = rhsUpper;
    }

    public void map() throws MappingError {
        LeftHandSide lhs = this.getLeftHandSide();
        FeatureReference feature = lhs.getImpl().getFeature();
        if (feature != null) {
            FumlMapping mapping = this.fumlMap(lhs.getImpl().getReferent());
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping)mapping).getMapping();
            }
            if (!(mapping instanceof PropertyDefinitionMapping)) {
                this.throwError("Error mapping feature: " + 
                        mapping.getErrorMessage());
            } else {
                Property property =
                    ((PropertyDefinitionMapping)mapping).getProperty();
                ActivityNode objectSource = this.getObjectSource();
                if (objectSource == null) {
                    mapping = this.fumlMap(feature.getExpression());
                    if (!(mapping instanceof ExpressionMapping)) {
                        this.throwError("Error mapping feature expression: " + 
                                mapping.getErrorMessage());
                    } else {
                        ExpressionMapping expressionMapping = (ExpressionMapping)mapping;
                        this.controlTarget = this.graph.addStructuredActivityNode(
                                "Expression(LeftHandSide@" + lhs.getId() +")", 
                                expressionMapping.getModelElements());
                        objectSource = expressionMapping.getResultSource();
                    }
                }
                Expression index = lhs.getIndex();
                ActivityNode resultNode;
                if (index == null) {
                    if (rhsUpper == 0) {
                        ClearStructuralFeatureAction clearAction =
                            this.graph.addClearStructuralFeatureAction(property);
                        this.node = clearAction;
                        this.graph.addObjectFlow(
                                objectSource, 
                                clearAction.object);
                        if (this.controlTarget == null) {
                            this.controlTarget = clearAction;
                        }
                        resultNode = clearAction.result;
                    } else if (rhsUpper == 1) {
                        AddStructuralFeatureValueAction writeAction =
                            this.graph.addAddStructuralFeatureValueAction(
                                    property, true);
                        this.node = writeAction;
                        this.resultSource = this.graph.addForkNode(
                                "Fork(LeftHandSide@" + lhs.getId() + ")");
                        this.assignmentTarget = this.resultSource;
                        this.graph.addObjectFlow(
                                objectSource, 
                                writeAction.object);
                        this.graph.addObjectFlow(
                                this.resultSource, writeAction.value);
                        resultNode = writeAction.result;
                    } else {
                        ClearStructuralFeatureAction clearAction =
                            this.graph.addClearStructuralFeatureAction(property);
                        this.graph.addObjectFlow(
                                objectSource, 
                                clearAction.object);
                        this.resultSource = this.graph.addForkNode(
                                "Fork(LeftHandSide@" + lhs.getId() + ")");
                        this.assignmentTarget = this.resultSource;

                        // Place property assignment mapping in a
                        // structured activity node to insure the isEmpty
                        // test within it does start executing too soon.
                        this.node =
                            this.graph.addStructuredActivityNode(
                                    "WriteAll(" + property.qualifiedName +")", 
                                    new ArrayList<Element>());

                        InputPin valuePin = ActivityGraph.createInputPin(
                                this.node.name + 
                                ".input(" + property.qualifiedName + ")", 
                                property.typedElement.type, 
                                property.multiplicityElement.lower, 
                                property.multiplicityElement.upper.naturalValue);
                        ((StructuredActivityNode)this.node).
                        addStructuredNodeInput(valuePin);
                        this.graph.addObjectFlow(this.resultSource, valuePin);

                        ActivityGraph subgraph = new ActivityGraph();
                        resultNode = 
                            AssignmentExpressionMapping.mapPropertyAssignment(
                                    property, subgraph, 
                                    clearAction.result, valuePin);

                        graph.addToStructuredNode(
                                (StructuredActivityNode)this.node, 
                                subgraph.getModelElements());
                    }
                } else {
                    ActivityNode indexSource = this.getIndexSource();
                    if (indexSource == null) {
                        mapping = this.fumlMap(index);
                        if (!(mapping instanceof ExpressionMapping)) {
                            this.throwError("Error mapping index expression: " + 
                                    mapping.getErrorMessage());
                        } else {
                            ExpressionMapping indexMapping = 
                                (ExpressionMapping)mapping;
                            StructuredActivityNode indexNode = 
                                this.graph.addStructuredActivityNode(
                                        "Index(LeftHandSide@" + lhs.getId() +")", 
                                        indexMapping.getModelElements());
                            if (this.controlTarget == null) {
                                this.controlTarget = indexNode;
                            } else {
                                this.graph.addControlFlow(
                                        this.controlTarget, indexNode);
                            }
                            indexSource = indexMapping.getResultSource();
                        }
                    }
                    
                    CallBehaviorAction indexConversionAction = 
                        this.graph.addCallBehaviorAction(getBehavior(
                                RootNamespace.getIntegerFunctionToUnlimitedNatural()));
                    this.graph.addObjectFlow(
                            indexSource, indexConversionAction.argument.get(0));
                    
                    if (rhsUpper == 0) {
                        RemoveStructuralFeatureValueAction removeAction =
                            this.graph.addRemoveStructuralFeatureValueAction(
                                    property, false);
                        this.node = removeAction;
                        this.graph.addObjectFlow(
                                objectSource, 
                                removeAction.object);
                        this.graph.addObjectFlow(
                                indexConversionAction.result.get(0),
                                removeAction.removeAt);
                        resultNode = removeAction.result;                                
                    } else {
                        ForkNode indexFork = this.graph.addForkNode(
                                "Fork(" + indexSource.name + ")");
                        this.graph.addObjectFlow(
                                indexConversionAction.result.get(0), indexFork);
                        
                        RemoveStructuralFeatureValueAction removeAction =
                            this.graph.addRemoveStructuralFeatureValueAction(
                                    property, false);
                        this.graph.addObjectFlow(
                                objectSource, removeAction.object);
                        this.graph.addObjectFlow(
                                indexFork, removeAction.removeAt);
                        
                        AddStructuralFeatureValueAction writeAction =
                            this.graph.addAddStructuralFeatureValueAction(
                                    property, false);
                        this.node = writeAction;
                        this.resultSource = this.graph.addForkNode(
                                "Fork(LeftHandSide@" + lhs.getId() + ")");
                        this.assignmentTarget = this.resultSource;
                        this.graph.addObjectFlow(
                                removeAction.result, 
                                writeAction.object);
                        this.graph.addObjectFlow(
                                indexFork, writeAction.insertAt);
                        this.graph.addObjectFlow(
                                this.resultSource, writeAction.value);
                        resultNode = writeAction.result;
                    }
                }
                if (lhs.getImpl().isDataValueUpdate()) {
                    this.assignedValueSource = this.graph.addForkNode(
                            "Fork(" + resultNode.name + ")");
                    this.graph.addObjectFlow(
                            resultNode, this.assignedValueSource);
                }
            }
        }
    }
    
    public ActivityNode getNode() throws MappingError {
        if (this.node == null) {
            this.map();
            this.mapTo(this.node);
        }
        return this.node;
    }
    
    public ForkNode getResultSource() throws MappingError {
        this.getNode();
        return this.resultSource;
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        return this.getAssignedValueSource();
    }
    
    /**
     * The assigned value source is the activity node that is the source for
     * the value of a local name whose assigned source is the result of an
     * assignment to this left hand side. This will be different from the
     * result source for a feature left hand side or an indexed left hand side.
     */
    public ActivityNode getAssignedValueSource() throws MappingError {
        this.getNode();
        return this.assignedValueSource;
    }
    
    /**
     * The assignment target is the activity node which should receive the value
     * to be assigned.
     */
    public ActivityNode getAssignmentTarget() throws MappingError {
        this.getNode();
        return this.assignmentTarget;
    }
    
    /**
     * The control target is the activity node (if any) which should be the
     * target of a control flow from the right-hand side.
     */
    public ActivityNode getControlTarget() throws MappingError {
        this.getNode();
        return this.controlTarget;
    }
    
    /**
     * Set the source to be used for an index expression in this left-hand
     * side that has been mapped previously as for an inout parameter,
     * increment/decrement expression or compound assignment.
     */
    public void setIndexSource(ActivityNode indexSource) {
        this.indexSource = indexSource;        
    }
    
    public ActivityNode getIndexSource() {
        return this.indexSource;
    }

    /**
     * Set the source to be used for a feature object expression in this
     * left-hand side that has been mapped previously as for an inout parameter,
     * increment/decrement expression or compound assignment.
     */
    public void setObjectSource(ActivityNode indexSource) {
        this.objectSource = indexSource;        
    }
    
    public ActivityNode getObjectSource() {
        return this.objectSource;
    }

    @Override
    public Element getElement() {
        return this.node;
    }
    
    @Override
    public Collection<Element> getModelElements() throws MappingError {
        return this.getGraph().getModelElements();
    }

    public ActivityGraph getGraph() throws MappingError {
        this.getNode();
        return this.graph;
    }
	
	public LeftHandSide getLeftHandSide() {
		return (LeftHandSide) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    if (this.node != null) {
	        System.out.println(prefix + " node: " + this.node);
	    }
	    
	    if (this.resultSource != null) {
	        System.out.println(prefix + " resultSource: " + this.resultSource);
	    }
	    
	    if (this.assignedValueSource != null) {
	        System.out.println(prefix + " assignedValueSource: " + 
	                this.assignedValueSource);
	    }
	    
	    if (this.assignmentTarget != null) {
	        System.out.println(prefix + " assignmentTarget: " + 
	                this.assignmentTarget);
	    }
	    
        LeftHandSide source = this.getLeftHandSide();
	    FeatureReference feature = source.getImpl().getFeature();
	    if (feature != null) {
	        Expression expression = feature.getExpression();
	        if (expression != null) {
	            System.out.println(prefix + " expression:");
	            Mapping mapping = expression.getImpl().getMapping();
	            if (mapping != null) {
	                mapping.printChild(prefix);
	            }
	        }
	    }
	    
	    if (this.indexSource != null) {
	        System.out.println(prefix + " indexSource: " + this.indexSource);
	    } else {
    	    Expression index = source.getIndex();
    	    if (index != null) {
    	        System.out.println(prefix + " index:");
    	        Mapping indexMapping = index.getImpl().getMapping();
    	        if (indexMapping != null) {
    	            indexMapping.printChild(prefix);
    	        }
    	    }
	    }
	}

} // LeftHandSideMapping
