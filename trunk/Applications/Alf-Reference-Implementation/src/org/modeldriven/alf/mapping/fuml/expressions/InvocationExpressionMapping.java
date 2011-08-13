
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.ActivityDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.OperationDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.SignalDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.FeatureReference;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.Tuple;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.CallAction;
import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.BasicActions.CallOperationAction;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.InvocationAction;
import fUML.Syntax.Actions.BasicActions.OutputPin;
import fUML.Syntax.Actions.BasicActions.Pin;
import fUML.Syntax.Actions.BasicActions.SendSignalAction;
import fUML.Syntax.Actions.IntermediateActions.ReadLinkAction;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityEdge;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ControlFlow;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Activities.IntermediateActivities.ObjectFlow;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Parameter;
import fUML.Syntax.Classes.Kernel.ParameterDirectionKind;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.Communications.Signal;

public abstract class InvocationExpressionMapping extends ExpressionMapping {

    private Action action = null;
    private List<Element> modelElements = null;
    private ActivityNode resultSource = null;
    
    public void mapTo(Action action) throws MappingError {
        super.mapTo(action);

        InvocationExpression expression = this.getInvocationExpression();

        this.modelElements = new ArrayList<Element>();
        this.mapTargetTo(action);
        
        Tuple tuple = expression.getTuple();
        FumlMapping tupleMapping = this.fumlMap(tuple);
        if (tupleMapping instanceof TupleMapping) {
            ((TupleMapping)tupleMapping).mapTo(action);
            this.modelElements.addAll(tupleMapping.getModelElements());
        } else {
            this.throwError("Error mapping tuple:" + tuple);
        }
        
        this.mapFeature(action);
        
    }
    
    public Action mapAction() throws MappingError {
        InvocationExpression invocationExpression = this.getInvocationExpression();
        if (invocationExpression.getIsOperation()) {
            return new CallOperationAction();
        } else if (invocationExpression.getIsSignal()) {
            return new SendSignalAction();
        } else if (invocationExpression.getIsBehavior()) {
            return new CallBehaviorAction();
        } else if (invocationExpression.getIsAssociationEnd()) {
            this.throwError("Association read mapping not implemented.");
            return null;
        } else {
            this.throwError("Error mapping invocation action: " + invocationExpression.getReferent());
            return null;
        }
    }
    public void mapTargetTo(Action action) throws MappingError {
        InvocationExpression expression = this.getInvocationExpression();
        ElementReference referent = expression.getReferent();
        FumlMapping mapping = this.fumlMap(referent);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        
        if (mapping instanceof OperationDefinitionMapping) {
            Operation operation = 
                ((OperationDefinitionMapping)mapping).getOperation();
            action.setName("CallOperation(" + operation.qualifiedName + ")");
            ((CallOperationAction)action).setOperation(operation);
        } else if (mapping instanceof SignalDefinitionMapping) {
            Signal signal =
                ((SignalDefinitionMapping)mapping).getSignal();
            action.setName("SendSignal(" + signal.qualifiedName + ")");
            ((SendSignalAction)action).setSignal(signal);
        } else if (mapping instanceof ActivityDefinitionMapping) {
            Behavior behavior = 
                ((ActivityDefinitionMapping)mapping).getBehavior();
            action.setName("CallBehavior(" + behavior.qualifiedName + ")");
            ((CallBehaviorAction)action).setBehavior(behavior);
        } else {
            this.throwError("Unknown referent mapping: " + mapping);
        }
        
        for (Parameter parameter: this.getParameters(action)) {
            ParameterDirectionKind direction = parameter.direction;
            if (direction == ParameterDirectionKind.in ||
                    direction == ParameterDirectionKind.inout) {
                this.addPin(action, new InputPin(), parameter);
            }
            if (direction == ParameterDirectionKind.out ||
                    direction == ParameterDirectionKind.inout ||
                    direction == ParameterDirectionKind.return_) {
                OutputPin pin = new OutputPin();
                this.addPin(action, pin, parameter);
                if (direction == ParameterDirectionKind.return_) {
                    this.resultSource = new ForkNode();
                    this.resultSource.setName("Fork(" + pin.name + ")");
                    this.modelElements.add(this.resultSource);
                    
                    ObjectFlow flow = new ObjectFlow();
                    flow.setSource(pin);
                    flow.setTarget(this.resultSource);
                    this.modelElements.add(flow);
                }
            }
        }
    }
    
    private void addPin(Action action, Pin pin, Parameter parameter) 
        throws MappingError {
        pin.setLower(parameter.multiplicityElement.lower);
        pin.setUpper(parameter.multiplicityElement.upper.naturalValue);

        pin.setType(parameter.type);

        if (pin instanceof InputPin) {
            pin.setName(action.name + ".argument(" + parameter.name + ")");
            if (action instanceof InvocationAction) {
                ((InvocationAction)action).addArgument((InputPin)pin);
            } else if (action instanceof ReadLinkAction) {
                this.throwError("InvocationExpressionMapping for ReadLinkAction not yet implemented.");
            } else {
                this.throwError("Error adding input pin to action: " + action);
            }
        } else if (action instanceof CallAction) {
            pin.setName(action.name + ".result(" + parameter.name + ")");
            ((CallAction)action).addResult((OutputPin)pin);
        } else {
            this.throwError("Error adding output pin to action: " + action);
        }
    }

    public void mapFeature(Action action) throws MappingError {
        InvocationExpression expression = this.getInvocationExpression();
        FeatureReference feature = expression.getFeature();        
        if (feature != null) {
            InputPin targetPin = new InputPin();
            targetPin.setName(action.name + ".target");
            targetPin.setLower(1);
            targetPin.setUpper(1);
            if (action instanceof CallOperationAction) {
                ((CallOperationAction)action).setTarget(targetPin);
            } else {
                ((SendSignalAction)action).setTarget(targetPin);
            }
    
            Expression primary = feature == null? null: feature.getExpression();
            FumlMapping mapping = this.fumlMap(primary);
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping expression:" + primary);
            } else {
                ActivityNode featureResult = 
                    ((ExpressionMapping)mapping).getResultSource();
                if (expression.getImpl().isSequenceFeatureInvocation()) {
                    List<Element> elements = this.modelElements;
                    this.modelElements = new ArrayList<Element>();
                    
                    ExpansionRegion region = new ExpansionRegion();
                    region.setName("Collect(" + action.name + ")");
                    
                    // Add action and tuple mapping to expansion region.
                    region.addNode(action);
                    for (Element element: elements) {
                        if (element instanceof ActivityNode) {
                            region.addNode((ActivityNode)element);
                        } else if (element instanceof ControlFlow) {
                            region.addEdge((ActivityEdge)element);
                        }
                    }
                    for (Element element: elements) {
                        if (element instanceof ObjectFlow) {
                            ActivityEdge edge = (ActivityEdge)element;
                            if (edge.source.inStructuredNode == region) {
                                region.addEdge(edge);
                            } else {
                                InputPin pin = new InputPin();
                                pin.setName(region.name + ".input(" + edge.source.name + ")");
                                region.input.add(pin);
                                ObjectFlow flow = new ObjectFlow();
                                flow.setSource(edge.source);
                                flow.setTarget(pin);
                                this.modelElements.add(flow);
                                flow = new ObjectFlow();
                                flow.setSource(pin);
                                flow.setTarget(edge.target);
                                region.addEdge(flow);                                
                            }
                        }
                    }
                    
                    // Connect feature mapping result source to region input node.
                    ExpansionNode inputNode = new ExpansionNode();
                    inputNode.setName(region.name + ".inputElement");
                    region.addInputElement(inputNode);
                    this.modelElements.add(inputNode);                
                    ObjectFlow flow = new ObjectFlow();
                    flow.setSource(featureResult);
                    flow.setTarget(inputNode);
                    this.modelElements.add(flow);
                    
                    // Connect region input node internally to action target pin.
                    flow = new ObjectFlow();
                    flow.setSource(inputNode);
                    flow.setTarget(targetPin);
                    region.addEdge(flow);
                    
                    // Connect action result pin (if any) internally to region
                    // output node and connect that to a fork node as the invocation
                    // expression result source.
                    if (this.resultSource != null) {
                        ExpansionNode outputNode = new ExpansionNode();
                        outputNode.setName(region.name + ".outputElement");
                        region.addOutputElement(outputNode);
                        this.modelElements.add(outputNode);
                        
                        ActivityEdge oldFlow = this.resultSource.incoming.get(0);
                        
                        flow = new ObjectFlow();
                        flow.setSource(oldFlow.source);
                        flow.setTarget(outputNode);
                        region.addEdge(flow);
                        
                        flow = new ObjectFlow();
                        flow.setSource(outputNode);
                        flow.setTarget(this.resultSource);
                        this.modelElements.add(flow);
                        
                        oldFlow.source.outgoing.remove(oldFlow);
                        resultSource.incoming.remove(oldFlow);
                        this.modelElements.remove(oldFlow);                        
                    }
                    
                    // Make the expansion region the new primary action for the
                    // mapping.
                    this.action = region;
                } else if (featureResult != null) {
                    // Connection feature mapping result source directly to action
                    // target pin
                    ObjectFlow flow = new ObjectFlow();
                    flow.setSource(featureResult);
                    flow.setTarget(targetPin);
                    this.modelElements.add(flow);
                }
                this.modelElements.addAll(mapping.getModelElements());
            }
        }
    }
    
    public List<Parameter> getParameters(Action action) {
        List<Parameter> parameters = new ArrayList<Parameter>();
        if (action instanceof CallOperationAction) {
            parameters.addAll(((CallOperationAction)action).operation.ownedParameter);
        } else if (action instanceof SendSignalAction) {
            for (Property attribute: ((SendSignalAction)action).signal.attribute) {
                Parameter parameter = new Parameter();
                parameter.setName(attribute.name);
                parameter.setDirection(ParameterDirectionKind.in);
                parameter.setType(attribute.typedElement.type);
                parameter.setLower(attribute.multiplicityElement.lower);
                parameter.setUpper(attribute.multiplicityElement.upper.naturalValue);
                parameters.add(parameter);
            }
        } else if (action instanceof CallBehaviorAction) {
            parameters.addAll( 
                ((CallBehaviorAction)action).behavior.ownedParameter
                );
        }
        return parameters;
    }
    
    public Action getAction() throws MappingError {
        if (this.action == null) {
            this.action = this.mapAction();
            this.mapTo(this.action);
        }
        return this.action;
    }
    
    @Override
    public ActivityNode getResultSource() throws MappingError {
        this.getAction();
        return this.resultSource;
    }
    
    @Override
    public Element getElement() {
        return this.action;
    }
    
    @Override
    public List<Element> getModelElements() throws MappingError {
        List<Element> elements = new ArrayList<Element>();

        Action action = this.getAction();
        if (action != null) {
            elements.add(action);
        }

        if (this.modelElements != null) {
            elements.addAll(this.modelElements);
        }

        return elements;
    }
    
	public InvocationExpression getInvocationExpression() {
		return (InvocationExpression) this.getSource();
	}
	
	@Override
	public void print(String prefix) {
	    super.print(prefix);
	    
	    Tuple tuple = this.getInvocationExpression().getTuple();
	    Mapping mapping = tuple.getImpl().getMapping();
	    if (mapping != null) {
	        System.out.println(prefix + " tuple:");
	        mapping.printChild(prefix);
	    }
	    
        FeatureReference feature = this.getInvocationExpression().getFeature();
        Expression primary = feature == null? null: feature.getExpression();
        if (primary != null) {
            mapping = primary.getImpl().getMapping();
            if (mapping != null) {
                System.out.println(prefix + " primary:");
                mapping.printChild(prefix);
            }
        }
        
        if (this.action != null) {
            System.out.println(prefix + " action: " + action);
            Element invocationAction = this.action;
            if (action instanceof ExpansionRegion) {
                ExpansionNode inputNode = ((ExpansionRegion)action).inputElement.get(0);
                ActivityEdge flow = inputNode.outgoing.get(0);
                invocationAction = ((InputPin)flow.target).owner;
            }
            if (invocationAction instanceof CallOperationAction) {
                System.out.println(prefix + " operation: " + 
                        ((CallOperationAction)invocationAction).operation);
            } else if (invocationAction instanceof SendSignalAction) {
                System.out.println(prefix + " signal: " + 
                        ((SendSignalAction)action).signal);
            } else if (invocationAction instanceof CallBehaviorAction) {
                System.out.println(prefix + " behavior: " + 
                        ((CallBehaviorAction)action).behavior);
            }
        }        
	}

} // InvocationExpressionMapping
