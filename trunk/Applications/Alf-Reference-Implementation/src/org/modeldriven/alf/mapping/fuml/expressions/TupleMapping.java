
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
import java.util.List;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.gen.SyntaxElementMapping;

import org.modeldriven.alf.syntax.expressions.LeftHandSide;
import org.modeldriven.alf.syntax.expressions.NamedExpression;
import org.modeldriven.alf.syntax.expressions.OutputNamedExpression;
import org.modeldriven.alf.syntax.expressions.Tuple;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ControlFlow;
import fUML.Syntax.Activities.IntermediateActivities.ObjectFlow;
import fUML.Syntax.Classes.Kernel.Element;

public abstract class TupleMapping extends SyntaxElementMapping {

    private StructuredActivityNode node = null;
    private List<Element> modelElements = null;

    public void mapTo(Action action) throws MappingError {
        Tuple tuple = this.getTuple();
        Collection<NamedExpression> inputs = tuple.getInput();
        Collection<OutputNamedExpression> outputs = tuple.getOutput();
        
        if (!inputs.isEmpty() || !outputs.isEmpty()) {

            this.node = new StructuredActivityNode();
            this.node.setName("Tuple@" + Integer.toHexString(tuple.hashCode()));

            ControlFlow control = new ControlFlow();
            control.setSource(node);
            control.setTarget(action);
            this.modelElements = new ArrayList<Element>();
            this.modelElements.add(control);

            // Note: Tuple input and output expressions are presumed to be listed
            // in the same order as their corresponding parameters, as implemented
            // in TupleImpl.

            int i = 0;
            for (NamedExpression input: tuple.getInput()) {
                FumlMapping mapping = this.fumlMap(input.getExpression());
                if (!(mapping instanceof ExpressionMapping)) {
                    this.throwError("Error mapping expression: " + mapping);
                } else {
                    addTo(node, mapping.getModelElements(), this.modelElements);

                    InputPin pin = action.input.getValue(i);
                    ActivityNode resultSource = 
                        ((ExpressionMapping)mapping).getResultSource();

                    if (resultSource == null) {
                        this.setErrorMessage("No result source: " + mapping);
                    } else {
                        ObjectFlow flow = new ObjectFlow();        
                        flow.setSource(resultSource);
                        flow.setTarget(pin);
                        this.modelElements.add(flow);
                    }
                }
                i++;
            }

            i = 0;
            for (OutputNamedExpression output: tuple.getOutput()) {
                if (!output.getExpression().getImpl().isNull()) {
                    LeftHandSide lhs = output.getLeftHandSide();
                    FumlMapping lhsMapping = this.fumlMap(lhs);
                    if (!(lhsMapping instanceof LeftHandSideMapping)) {
                        this.throwError("Error mapping output as left hand side: " +
                                lhsMapping);
                    } else {
                        this.modelElements.addAll(lhsMapping.getModelElements());
                        
                        OutputPin pin = action.output.getValue(i);
                        ActivityNode assignmentTarget = 
                            ((LeftHandSideMapping)lhsMapping).getAssignmentTarget();
                        
                        ObjectFlow flow = new ObjectFlow();
                        flow.setSource(pin);
                        flow.setTarget(assignmentTarget);
                        this.modelElements.add(flow);
                    }
                }
                i++;
            }
        }
    }
    
    @Override
    public Element getElement() {
        return this.node;
    }

    @Override
    public List<Element> getModelElements() {
        List<Element> modelElements = new ArrayList<Element>();
        if (this.node != null) {
            modelElements.add(this.node);
        }
        if (this.modelElements != null) {
            modelElements.addAll(this.modelElements);
        }
        return modelElements;
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
