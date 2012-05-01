
/*
 * Copyright 2011-2012 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.OperationDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.AssignmentExpression;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.LeftHandSide;
import org.modeldriven.alf.syntax.expressions.SequenceConstructionExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.BasicActions.CallOperationAction;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.IntermediateActions.AddStructuralFeatureValueAction;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.LoopNode;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public class AssignmentExpressionMapping extends ExpressionMapping {
    
    private LeftHandSideMapping lhsMapping = null;
    private ExpressionMapping lhsExpressionMapping = null;
    private CallBehaviorAction callAction = null;

    /**
     * 1. The mapping of an assignment expression depends on whether it is a
     * simple or compound assignment and, if it is a simple assignment whether
     * it has a name or feature left-hand side and whether or not it has an
     * index.
     * 
     * 2. As an assigned source, an assignment expression maps to the result
     * source of the expression.
     * 
     * 3. If no conversion is required then the result source element of the
     * right-hand side of an assignment expression, as referenced below, should
     * be considered to be the result source element of the mapping of the
     * right-hand side expression. If collection conversion is required, then
     * the result source element of the right-hand side expression is connect by
     * an object flow to an invocation of the Collection::toSequence operation,
     * and the result of that invocation acts as the result source element for
     * the right-hand side, unless bit string conversion is also require. If bit
     * string conversion is required, then either the result source element of
     * the argument expression or the result of the toSequence invocation, if
     * collection conversion was required, is connected by an object flow to an
     * invocation of the BitStringFunctions::ToBitString function, and the
     * result of that invocation acts as the result source element for the
     * right-hand side.
     * 
     * Simple Assignment: Name Left-Hand Side, without Index
     * 
     * 4. If the left-hand side is a name without an index, then a simple
     * assignment maps to a fork node. The result source element from the
     * mapping of the right-hand side is connected to the fork node by an object
     * flow. The fork node is the result source element for the assignment
     * expression and also the source for the assigned value for the name.
     * 
     * Simple Assignment: Name Left-Hand Side, with Index
     * 
     * 5. If the left-hand side is a name with an index, then a simple
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
     * 
     * Simple Assignment: Feature Left-Hand Side, without Index
     * 
     * 6. If the left-hand side is a property reference but has no index, then
     * the mapping of a simple assignment depends on the multiplicity upper
     * bound of the right-hand side expression.
     * 
     * 7. If the right-hand side expression has a multiplicity upper bound of 0,
     * then the simple assignment maps to a clear structural feature action for
     * the identified property. If the right-hand side expression is a sequence
     * construction expression for an empty set, then it is not mapped at all.
     * Otherwise, the right-hand side expression is mapped inside a structured
     * activity node, with a control flow from the structured activity node to
     * the clear structural feature action. There is no result source element
     * for the assignment.
     * 
     * 8. If the right-hand side expression has a multiplicity upper bound of 1,
     * then the simple assignment maps to an add structural feature value action
     * for the identified property with isReplaceAll=true. The result source
     * element from the mapping of the right-hand side expression is connect by
     * an object flow to a fork node that has a further object flow to the value
     * input pin of the add structural feature value action. The fork node is
     * the result source element for the assignment.
     * 
     * 9. Otherwise, the simple assignment maps to a clear structural feature
     * value action for the identified property followed by an expansion region.
     * The result source element from the mapping of the righthand side
     * expression is connected by an object flow to a fork node that has a
     * further object flow to an input expansion node of the expansion region.
     * The expansion region contains an add structural feature value action for
     * the property with isReplaceAll=false and an incoming object flow from the
     * input expansion node to its value input pin. If the property is ordered,
     * then the insertAt input pin has an incoming object flow from a value
     * specification action for the unbounded value *. The fork node is the
     * result source element for the assignment.
     * 
     * Simple Assignment: Feature Left-Hand Side, with Index
     * 
     * 10. If the left-hand side has an index, then the mapping of a simple
     * assignment includes a structured activity node containing the mapping of
     * the index expression. The further mapping of the assignment expression
     * then depends on the multiplicity upper bound of the right-hand side
     * expression.
     * 
     * 11. If the right-hand side expression has a multiplicity upper bound of
     * 0, then the simple assignment maps to a remove structural feature value
     * action for the identified property with isRemoveDuplicates=false and an
     * incoming object flow into its removeAt input pin from the result source
     * element from the mapping of the index expression. If the right-hand side
     * expression is a sequence construction expression for an empty collection,
     * then it is not mapped at all. Otherwise, the right-hand side expression
     * is mapped inside a structured activity node, with a control flow from
     * that structured activity node to the structured activity node containing
     * the mapping of the index expression. There is no result source element
     * for the assignment.
     * 
     * 12. If the right-hand side expression has a multiplicity upper bound of
     * 1, then the simple assignment maps to a remove structural feature value
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
     * 13. If the left-hand side is a data value attribute update, then a fork
     * node is added to the mapping for the assignment expression to be used as
     * the source element for the assigned value of the name. The fork node is
     * the target of an object flow whose source is determined as follows: If
     * the mapping includes a remove structural feature action, but no add
     * structural feature action, then the result output pin of the remove
     * structural feature action is used. If the mapping includes an add
     * structural feature action not in an expansion region, then the result
     * output pin of the add structural feature action is used. If the mapping
     * has an add structural feature action in an expansion region, then an
     * output expansion node is added to the expansion region and the result
     * output pin of the add structural feature action is connected to the
     * output expansion node by an object flow. The output expansion node is
     * then connected by an object flow to a mapping of the expression
     * ListGet(x,ListSize(x)), where x represents the object flow from the
     * expansion node.
     * 
     * Compound Assignment
     * 
     * 14. A compound assignment is mapped like a simple assignment expression
     * for which the assigned value is the result of a call behavior action for
     * the primitive behavior corresponding to the compound assignment operator.
     * The arguments to the call behavior action come from the result source
     * elements of the mapping of the effective expression for the left-hand
     * side and the right-hand side expression. However, if the left-hand side
     * is a property reference, then the primary expression for the reference
     * and any index expression are only mapped once with their values used both
     * in the mapping of the effective expression for the left-hand side and the
     * updating of the left-hand side as a structural feature.
     */
    
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
            this.lhsMapping.setRhsUpper(rhs.getUpper());

            mapping = this.fumlMap(rhs);
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping right hand side: " + 
                        mapping.getErrorMessage());
            } else {
                ExpressionMapping rhsMapping = (ExpressionMapping)mapping;
                ActivityGraph rhsSubgraph = new ActivityGraph(rhsMapping.getGraph());
                ActivityNode rhsResultSource = rhsMapping.getResultSource();
                if (rhsResultSource != null) {
                    if (!assignmentExpression.getIsSimple()) {
                        Expression expression = lhs.getImpl().getExpression();
                        mapping = this.fumlMap(expression);
                        if (!(mapping instanceof ExpressionMapping)) {
                            this.throwError("Error mapping left hand side as an expression: " +
                                    mapping.getErrorMessage());
                        } else {
                            this.lhsExpressionMapping =
                                (ExpressionMapping)mapping;
                            this.graph.addAll(this.lhsExpressionMapping.getGraph());
                            this.lhsMapping.setIndexSource(
                                    this.lhsExpressionMapping.getIndexSource());
                            this.lhsMapping.setObjectSource(
                                    this.lhsExpressionMapping.getObjectSource());

                            this.callAction = 
                                this.graph.addCallBehaviorAction(
                                        this.getCompoundExpressionBehavior());
                            this.graph.addObjectFlow(
                                    this.lhsExpressionMapping.getResultSource(), 
                                    this.callAction.argument.get(0));

                            // Apply bit string conversion to the right-hand
                            // side, if necessary.
                            ElementReference rhsType = rhs.getType();
                            rhsResultSource = mapConversions(
                                    this, this.graph, 
                                    rhsResultSource, 
                                    null, false, 
                                    rhsType != null && rhsType.getImpl().isInteger() && 
                                    this.callAction.argument.get(1).
                                        typedElement.type == getBitStringType());
                            
                            this.graph.addObjectFlow(
                                    rhsResultSource,
                                    this.callAction.argument.get(1));
                            
                            rhsResultSource = this.callAction.result.get(0);                                
                        }
                    }
                    
                    rhsResultSource = mapConversions(
                            this, rhsSubgraph, rhsResultSource, 
                            assignmentExpression.getType(), 
                            assignmentExpression.getIsCollectionConversion(), 
                            assignmentExpression.getIsBitStringConversion());

                    this.graph.addAll(this.lhsMapping.getGraph());                    
                    ActivityNode assignmentTarget = 
                        this.lhsMapping.getAssignmentTarget();
                    ActivityNode controlTarget =
                        this.lhsMapping.getControlTarget();

                    StructuredActivityNode rhsNode = 
                        assignmentTarget == null && 
                        rhs instanceof SequenceConstructionExpression ? null: 
                            this.graph.addStructuredActivityNode(
                                    "RightHandSide@" + rhs.getId(), 
                                    rhsSubgraph.getModelElements());

                    if (assignmentTarget != null) {
                        this.graph.addObjectFlow(
                                rhsResultSource,
                                this.lhsMapping.getAssignmentTarget());
                    }

                    if (rhsNode != null && controlTarget != null) {
                        this.graph.addControlFlow(rhsNode, controlTarget);
                    }
                }
            }
        }
        
        super.mapTo(this.lhsMapping.getNode());        
    }
    
    private Behavior getCompoundExpressionBehavior() throws MappingError {
        AssignmentExpression assignmentExpression = this.getAssignmentExpression();
        ElementReference type = assignmentExpression.getType();
        String operator = assignmentExpression.getOperator();
        operator = operator.substring(0, operator.length()-1);
        return getBehavior(
            type.getImpl().isInteger()?
                    RootNamespace.getIntegerFunction(operator):
            type.getImpl().isBitString()?
                    RootNamespace.getBitStringFunction(operator):
            type.getImpl().isBoolean()?
                    RootNamespace.getBooleanFunction(operator):
            type.getImpl().isString()?
                    RootNamespace.getStringFunction(operator):
                    null
        );
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
	    
	    if (this.lhsExpressionMapping != null) {
	        System.out.println(prefix + " lhsExpression: ");
	        this.lhsExpressionMapping.printChild(prefix);
	    }
	    
	    if (this.callAction != null) {
	        System.out.println(prefix + " behavior: " + callAction.behavior);
	    }
	    
	    AssignmentExpression assignmentExpression = this.getAssignmentExpression();
	    Expression rhs = assignmentExpression.getRightHandSide();
	    Mapping mapping = rhs.getImpl().getMapping();
	    
	    if (mapping != null) {
    	    System.out.println(prefix + " rightHandSide:");
    	    mapping.printChild(prefix);
	    }
	}
	
	// Static helper methods
	
	public static ActivityNode mapConversions(
	        FumlMapping outerMapping,
	        ActivityGraph subgraph,
	        ActivityNode rhsResultSource, 
	        ElementReference rhsType,
	        boolean isCollectionConversion, 
	        boolean isBitStringConversion) throws MappingError {
        if (rhsResultSource != null) {
            if (isCollectionConversion) {
                ElementReference toSequenceOperation = rhsType == null? null: 
                    rhsType.getImpl().getToSequenceOperation();
                if (toSequenceOperation == null) {
                    outerMapping.throwError("No toSequence operation: " + rhsType);
                } else {
                    FumlMapping mapping = outerMapping.fumlMap(toSequenceOperation);
                    if (mapping instanceof ElementReferenceMapping) {
                        mapping = ((ElementReferenceMapping)mapping).getMapping();
                    }
                    if (!(mapping instanceof OperationDefinitionMapping)) {
                        outerMapping.throwError("Error mapping toSequence operation: " + 
                                mapping.getErrorMessage());
                    } else {
                        CallOperationAction callAction = 
                            subgraph.addCallOperationAction(
                                    ((OperationDefinitionMapping)mapping).
                                        getOperation());
                        subgraph.addObjectFlow(
                                rhsResultSource, callAction.argument.get(0));
                        rhsResultSource = callAction.result.get(0);
                    }
                }
            }
            if (isBitStringConversion) {
                CallBehaviorAction callAction = subgraph.addCallBehaviorAction(
                        getBehavior(RootNamespace.getBitStringFunctionToBitString()));
                subgraph.addObjectFlow(
                        rhsResultSource, callAction.argument.get(0));
                rhsResultSource = callAction.result.get(0);
            }
        }
        return rhsResultSource;
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
            subgraph.addAddStructuralFeatureValueAction(property, false);

        // For an ordered property, provide insertAt pin with a "*" input.
        if (property.multiplicityElement.isOrdered) {
            ValueSpecificationAction valueAction = 
                subgraph.addUnlimitedNaturalValueSpecificationAction(-1);
            subgraph.addObjectFlow(valueAction.result, writeAction.insertAt);
        }
        
        
        if (property.multiplicityElement.lower == 1 && 
                property.multiplicityElement.upper.naturalValue == 1) {
            // If the property multiplicity is 1..1, connect the valueSource to
            // the write action value input pin.
            subgraph.addObjectFlow(valueSource, writeAction.value);        
            writeAction.setIsReplaceAll(true);   
            
            // Connect the action object pin to the objectSource.
            subgraph.addObjectFlow(objectSource, writeAction.object);
            
            graph.addAll(subgraph);
            return writeAction.result;
            
        } else {
            // Otherwise, create a node to iteratively add possibly 
            // multiple values to the property.
            Classifier featuringClassifier = property.featuringClassifier.get(0);
            writeAction.setIsReplaceAll(false);
            if (!(featuringClassifier instanceof DataType)) {
                // If the property is a feature of a class, use an iterative
                // expansion region.
                ExpansionRegion region = graph.addExpansionRegion(
                        "Iterate(" + writeAction.name + ")", 
                        ExpansionKind.iterative, 
                        subgraph.getModelElements(), 
                        valueSource, writeAction.value, 
                        null);

                InputPin objectInputPin = ActivityGraph.createInputPin(
                        region.name + ".input(" + objectSource.name + ")", 
                        featuringClassifier, 1, 1);
                region.addStructuredNodeInput(objectInputPin);
                region.addEdge(ActivityGraph.createObjectFlow(
                        objectInputPin, writeAction.object));

                graph.addObjectFlow(objectSource, objectInputPin);
                return null;
            } else {
                // If the property is a feature of a data type, then use
                // a loop node to iteratively update the data value, rather than
                // an expansion region.
                InputPin objectInputPin = ActivityGraph.createInputPin(
                        objectSource.name, featuringClassifier, 1, 1);
                InputPin valueInputPin = ActivityGraph.createInputPin(
                        "value", property.typedElement.type, 0, -1);
                LoopNode loopNode = graph.addLoopNode(
                        "Iterate(" + writeAction.name + ")", true, 
                        objectInputPin, valueInputPin);
                graph.addObjectFlow(objectSource, loopNode.loopVariableInput.get(0));
                graph.addObjectFlow(valueSource, loopNode.loopVariableInput.get(1));
                
                ActivityNode valueFork = subgraph.addForkNode("Fork(value)");
                ValueSpecificationAction value1Action = 
                        subgraph.addNaturalValueSpecificationAction(1);
                ActivityNode value1Fork = subgraph.addForkNode(
                        "Fork(" + value1Action.result.name + ")");
                CallBehaviorAction getAction = subgraph.addCallBehaviorAction(
                        getBehavior(RootNamespace.getListFunctionGet()));
                CallBehaviorAction removeAction = subgraph.addCallBehaviorAction(
                        getBehavior(RootNamespace.getSequenceFunctionExcludeAt()));
                subgraph.addObjectFlow(loopNode.loopVariable.get(0), writeAction.object);
                subgraph.addObjectFlow(loopNode.loopVariable.get(1), valueFork);
                subgraph.addObjectFlow(value1Action.result, value1Fork);
                subgraph.addObjectFlow(valueFork, getAction.argument.get(0));
                subgraph.addObjectFlow(value1Fork, getAction.argument.get(1));
                subgraph.addObjectFlow(getAction.result.get(0), writeAction.value);
                subgraph.addObjectFlow(valueFork, removeAction.argument.get(0));
                subgraph.addObjectFlow(value1Fork, removeAction.argument.get(1));
                
                graph.addLoopBodyPart(
                        loopNode, subgraph.getModelElements(), 
                        writeAction.result, removeAction.result.get(0));
                
                subgraph = new ActivityGraph();
                CallBehaviorAction testAction = subgraph.addCallBehaviorAction(
                        getBehavior(RootNamespace.getSequenceFunctionNotEmpty()));
                subgraph.addObjectFlow(valueFork, testAction.argument.get(0));
                
                graph.addLoopTest(
                        loopNode, subgraph.getModelElements(), 
                        testAction.result.get(0));

                return loopNode.result.get(0);
            }
        }        
	}
	
} // AssignmentExpressionMapping
