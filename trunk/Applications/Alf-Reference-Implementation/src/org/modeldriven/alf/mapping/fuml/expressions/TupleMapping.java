
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
import java.util.HashMap;
import java.util.Map;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.SyntaxElementMapping;

import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.LeftHandSide;
import org.modeldriven.alf.syntax.expressions.LinkOperationExpression;
import org.modeldriven.alf.syntax.expressions.NamedExpression;
import org.modeldriven.alf.syntax.expressions.OutputNamedExpression;
import org.modeldriven.alf.syntax.expressions.Tuple;
import org.modeldriven.alf.syntax.expressions.UnboundedLiteralExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.InputPinList;
import fUML.Syntax.Actions.BasicActions.InvocationAction;
import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class TupleMapping extends SyntaxElementMapping {

    private StructuredActivityNode node = null;
    private ActivityGraph tupleGraph = new ActivityGraph();
    private ActivityGraph lhsGraph = new ActivityGraph();
    private Map<String, ActivityNode> assignedValueSourceMap = 
        new HashMap<String, ActivityNode>();

    /**
     * 1. An empty tuple (i.e., a positional tuple with no argument expressions)
     * is mapped to nothing. A nonempty tuple is mapped to a structured activity
     * node containing the mapping of each of its argument expressions. There is
     * a control flow from the structured activity node to the invocation action
     * taking input from the tuple mapping.
     */
    /*
     * Actually, output arguments should not be mapped inside the structured
     * activity node.
     */
    /**
     * 
     * 2. For an argument for an in parameter, the argument expression is mapped
     * as usual for an expression. The result source element of such an
     * expression provides the source for setting the value of the associated
     * parameter, unless conversion is required. If collection conversion is
     * required, then the result source element of the argument expression is
     * connect by an object flow to an invocation of the Collection::toSequence
     * operation, and the result of that invocation provides the source for
     * setting the value of the associated parameter, unless bit string
     * conversion is also require. If bit string conversion is required, then
     * either the result source element of the argument expression or the result
     * of the toSequence invocation, if collection conversion was required, is
     * connected by an object flow to an invocation of the
     * BitStringFunctions::toBitString function, and the result of that
     * invocation provides the source for setting the value of the associated
     * parameter.
     * 
     * 3. For an argument for an out parameter, the argument expression is
     * mapped as a left hand side of an assignment: an argument that is a local
     * name is mapped as a fork node while an argument that is a feature
     * reference is mapped as write structural feature value action. The output
     * from the invocation action for the corresponding parameter provides the
     * assigned value.
     * 
     * 4. For an argument for an inout parameter, the argument expression is
     * mapped twice (as given above): once as for an in parameter, to provide
     * the input value for the parameter, and once as for an out parameter, to
     * provide the target for the output value.
     */

    public void mapTo(Action action) throws MappingError {
        Tuple tuple = this.getTuple();
        Collection<NamedExpression> inputs = tuple.getInput();
        Collection<OutputNamedExpression> outputs = tuple.getOutput();
        
        if (!inputs.isEmpty()) {

            // Note: Tuple input and output expressions are presumed to be 
            // in the same order as their corresponding parameters, as 
            // implemented in TupleImpl.
            
            Collection<Element> nestedElements = new ArrayList<Element>();
            InputPinList inputPins = action instanceof InvocationAction? 
                    ((InvocationAction)action).argument: 
                    action.input;
            int i = 0;
            for (NamedExpression input: inputs) {
                FumlMapping mapping = this.fumlMap(input.getExpression());
                if (!(mapping instanceof ExpressionMapping)) {
                    this.throwError("Error mapping expression: " + mapping);
                } else {
                    // TODO: Implement collection and bit string conversion.                    
                    nestedElements.addAll(mapping.getModelElements());
                    ActivityNode resultSource = 
                        ((ExpressionMapping)mapping).getResultSource();
                    if (resultSource == null) {
                        this.setErrorMessage("No result source: " + mapping);
                    } else {
                        InputPin inputPin = inputPins.get(i);
                        this.tupleGraph.addObjectFlow(
                                resultSource, inputPin);
                        Expression index = input.getIndex();
                        if (index == null && 
                                tuple.getInvocation() instanceof LinkOperationExpression &&
                                inputPin.multiplicityElement.isOrdered) {
                            index = new UnboundedLiteralExpression();
                        }
                        if (index != null) {
                            mapping = this.fumlMap(index);
                            if (!(mapping instanceof ExpressionMapping)) {
                                this.throwError("Error mapping index expression" +
                                        mapping.getErrorMessage());
                            } else {
                                nestedElements.addAll(mapping.getModelElements());
                                resultSource =
                                    ((ExpressionMapping)mapping).getResultSource();
                                if (resultSource == null) {
                                    this.throwError("No result Source: " + mapping);
                                } else {
                                    if (index.getType().getImpl().conformsTo(
                                            RootNamespace.getIntegerType())) {
                                        ActivityGraph subgraph = new ActivityGraph();
                                        CallBehaviorAction callAction = 
                                            subgraph.addCallBehaviorAction(
                                            getBehavior(RootNamespace.
                                            getIntegerFunctionToUnlimitedNatural()));
                                        subgraph.addObjectFlow(
                                                resultSource, 
                                                callAction.argument.get(0));
                                        resultSource = callAction.result.get(0);
                                        nestedElements.addAll(
                                                subgraph.getModelElements());
                                    }
                                    // NOTE: This presumes that the "insertAt"
                                    // or "destroyAt" pin comes directly after
                                    // the value pin in the list of input pins.
                                    this.tupleGraph.addObjectFlow(
                                            resultSource, inputPins.get(++i));
                                }
                            }
                        }
                    }
                }
                i++;
            }
            
            if (!nestedElements.isEmpty()) {
                this.node = this.tupleGraph.addStructuredActivityNode(
                        "Tuple@" + tuple.getId(), nestedElements);
                this.tupleGraph.addControlFlow(this.node, action);
            }
        }

        int i = 0;
        OutputPin returnPin = ActivityGraph.getReturnPin(action);
        for (OutputNamedExpression output: outputs) {
            if (!output.getExpression().getImpl().isNull()) {
                LeftHandSide lhs = output.getLeftHandSide();
                FumlMapping mapping = this.fumlMap(lhs);
                if (!(mapping instanceof LeftHandSideMapping)) {
                    this.throwError("Error mapping output as left hand side: " +
                            mapping.getErrorMessage());
                } else {
                    LeftHandSideMapping lhsMapping = 
                        (LeftHandSideMapping)mapping;
                    this.lhsGraph.addAll(lhsMapping.getGraph()); 
                    
                    OutputPin outputPin = action.output.get(i);
                    if (outputPin == returnPin) {
                        i++;
                        outputPin = action.output.get(i);
                    }
                   
                    // NOTE: The object flow is part of the tuple graph, NOT the
                    // LHS graph.
                    this.tupleGraph.addObjectFlow(
                            outputPin, 
                            lhsMapping.getAssignmentTarget());

                    String assignedName = lhs.getImpl().getAssignedName();
                    if (assignedName != null) {
                        this.getAssignedValueSourceMap().put(
                                assignedName, 
                                lhsMapping.getAssignedValueSource());
                    }
                }
            }
            i++;
        }
    }
    
    public Map<String, ActivityNode> getAssignedValueSourceMap() {
        return this.assignedValueSourceMap;
    }
    
    @Override
    public ActivityNode getAssignedValueSource(String name) {
        return this.getAssignedValueSourceMap().get(name);
    }
    
    @Override
    public Element getElement() {
        return this.node;
    }

    public ActivityGraph getTupleGraph() {
        return this.tupleGraph;
    }
    
    public ActivityGraph getLhsGraph() {
        return this.lhsGraph;
    }
    
    @Override
    public Collection<Element> getModelElements() {
        Collection<Element> elements = 
            new ArrayList<Element>(this.getTupleGraph().getModelElements());
        elements.addAll(this.getLhsGraph().getModelElements());
        return elements;
    }
    
    public Tuple getTuple() {
        return (Tuple) this.getSource();
    }
    
    @Override
    public void print(String prefix) {
        super.print(prefix);
        Tuple source = this.getTuple();
        
        Collection<NamedExpression> inputs = source.getInput();
        if (!inputs.isEmpty()) {
            System.out.println(prefix + " input:");
            for (NamedExpression input: inputs) {
                System.out.println(prefix + "  name: " + input.getName());
                Mapping mapping = input.getExpression().getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix + " ");
                }
            }
        }
        
        Collection<OutputNamedExpression> outputs = source.getOutput();
        if (!outputs.isEmpty()) {
            System.out.println(prefix + " output:");
            for (OutputNamedExpression output: outputs) {
                System.out.println(prefix + "  name: " + output.getName());
                Mapping mapping = output.getLeftHandSide().getImpl().getMapping();
                if (mapping != null) {
                    mapping.printChild(prefix + " ");
                }
            }
        }
        
        System.out.println(prefix + " structured node: " + node);
    }

} // TupleMapping
