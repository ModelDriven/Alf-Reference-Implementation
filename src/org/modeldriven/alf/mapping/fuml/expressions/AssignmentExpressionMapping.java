
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

import org.modeldriven.alf.syntax.common.ElementReference;
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
     * invocation of the BitStringFunctions::toBitString function, and the
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
            
            mapping = this.fumlMap(rhs);
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping right hand side: " + 
                        mapping.getErrorMessage());
            } else {    
                ExpressionMapping rhsMapping = (ExpressionMapping)mapping;
                this.graph.addAll(rhsMapping.getGraph());                    
                
                ActivityNode rhsResultSource = rhsMapping.getResultSource();                    
                if (rhsResultSource != null) {
                    // TODO: Implement collection and bit string conversion.

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
                            this.graph.addObjectFlow(
                                    rhsResultSource,
                                    this.callAction.argument.get(1));
                            rhsResultSource = this.callAction.result.get(0);                                
                        }
                    }

                    this.graph.addAll(this.lhsMapping.getGraph());
                    this.graph.addObjectFlow(
                            rhsResultSource,
                            this.lhsMapping.getAssignmentTarget());
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
