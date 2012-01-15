/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import java.util.Collection;
import java.util.Map;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.ActivityDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.OperationDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.PropertyDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.SignalDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.FeatureReference;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.Tuple;

import fUML.Syntax.Actions.BasicActions.Action;
import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.BasicActions.CallOperationAction;
import fUML.Syntax.Actions.BasicActions.InputPin;
import fUML.Syntax.Actions.BasicActions.SendSignalAction;
import fUML.Syntax.Actions.IntermediateActions.DestroyObjectAction;
import fUML.Syntax.Actions.IntermediateActions.ReadSelfAction;
import fUML.Syntax.Actions.IntermediateActions.TestIdentityAction;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionKind;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionNode;
import fUML.Syntax.Activities.ExtraStructuredActivities.ExpansionRegion;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.ForkNode;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.Communications.Signal;

public abstract class InvocationExpressionMapping extends ExpressionMapping {

    protected Action action = null;
    protected ActivityNode resultSource = null;
    private ActivityGraph lhsGraph = new ActivityGraph();
    private Map<String, ActivityNode> assignedValueSourceMap = null;

    /**
     * 1. An invocation expression is mapped as a behavior invocation (which
     * also include a functional notation for reading associations) or a feature
     * invocation. Note that, after static semantic analysis, a super invocation
     * is mapped as a behavior invocation.
     * 
     * 2. If the invocation expression is the assigned source for a local name,
     * then it must map to a call action with result output pins. The actual
     * source for the value of the local name is the fork node connected to the
     * result output pin with that name. (NOTE: This is actually handled by the
     * assignment mapping.)
     */

    public Action mapAction() throws MappingError {
        Action action = this.mapTarget();
        
        InvocationExpression expression = this.getInvocationExpression();
        Tuple tuple = expression.getTuple();
        FumlMapping mapping = this.fumlMap(tuple);
        if (mapping instanceof TupleMapping) {
            TupleMapping tupleMapping = (TupleMapping)mapping;
            tupleMapping.mapTo(action);
            this.graph.addAll(tupleMapping.getTupleGraph());
            this.lhsGraph = tupleMapping.getLhsGraph();
            this.assignedValueSourceMap = tupleMapping.getAssignedValueSourceMap();
        } else {
            this.throwError("Error mapping tuple:" + tuple);
        }

        action = this.mapFeature(action);
        
        this.graph.addAll(this.lhsGraph);
        return action;
    }

    /*
     * NOTE: The mapping for both feature and behavior invocation actions is
     * handled here, to easily handle the case of qualified names disambiguated
     * to feature references.
     */

    /**
     * BEHAVIOR INVOCATION MAPPING
     * 
     * 1. A behavior invocation expression whose qualified name disambiguates to
     * a feature reference is mapped as if it were a feature invocation
     * expression. Otherwise, a behavior invocation expression is mapped as
     * either a behavior call or an association read.
     * 
     * 2. A behavior invocation expression whose qualified name resolves to a
     * behavior maps to a call behavior action for the named behavior. If the
     * behavior invocation expression has a non-empty tuple, then the call
     * behavior action is the target of a control flow whose source is the
     * structured activity node mapped from the tuple. Each input pin of the
     * call behavior action corresponds to an in or inout parameter of the
     * called behavior. If there is an argument expression for that parameter in
     * the tuple, then the input pin is the target of an object flow whose
     * source is the result source element of the argument expression.
     * Similarly, each output pin of the call behavior action (other than the
     * output pin for a return parameter) corresponds to an out or inout
     * parameter. If there is an argument expression for that parameter in the
     * type, then the output pin is the source of an object flow whose target is
     * assigned value input for the argument expression. NOTE: Call behavior
     * action pins corresponding to unmatched parameters remain unconnected. If
     * the behavior has a return parameter, then the output pin of the call
     * behavior action corresponding to that parameter is the result source
     * element for the behavior invocation action. Otherwise it has no result
     * source element.
     * 
     * 3. A behavior invocation expression whose qualified name resolves to an
     * association end maps to a read link action with end data for the ends of
     * the named association. Except for the end data for the target end, the
     * value input pins for each end are the target of an object flow from the
     * result source element of the mapping of the corresponding argument
     * expression. The result output pin of the read link action is the result
     * source element for the association selection.
     */

    /**
     * FEATURE INVOCATION MAPPING
     * 
     * 1. A feature invocation expression is mapped as either a single instance
     * feature invocation or a sequence feature invocation. For each kind of
     * invocation, the result source element of the mapping of the feature
     * expression is connected by an object flow to the appropriate target
     * activity node.
     * 
     * 2. A single instance feature invocation is mapped as either a
     * non-destructor operation call, an explicit destructor call, an implicit
     * destructor call or a signal send.
     * 
     * 3. A sequence feature invocation is mapped as an expansion region
     * similarly to a collect expression.
     * 
     * Operation Call
     * 
     * 4. An operation call (that is not a destructor call) maps to a call
     * operation action for the named operation. The result source element
     * mapped from the primary expression of the feature invocation expression
     * is connected by an object flow to the target input pin of the call
     * operation action.
     * 
     * 5. The call operation action has argument and result input and output
     * pins corresponding to the parameters of the operation. These pins are
     * connected to the appropriate mapping of argument and result expressions
     * from the tuple. If the operation has a return parameter, then the output
     * pin of the call operation action corresponding to that parameter is the
     * result source element for the feature invocation action. Otherwise it has
     * no result source element.
     * 
     * Destructor Call
     * 
     * 6. If an operation call is a destructor call, and the feature invocation
     * expression is not itself within the method of a destructor, then the call
     * operation action is followed by a destroy object action for the target
     * object with isDestroyOwnedObjects=true and isDestroyLinks=true. If the
     * feature invocation is within the method of a destructor, the destroy
     * object action is conditioned on a test that the target object is not the
     * context object. NOTE. Object destruction is always done with
     * isDestroyOwnedObjects=true and isDestroyLinks=true, because this is the
     * expected high-level behavior for object destruction.
     * 
     * 7. If an operation call is an implicit object destruction expression,
     * then it is mapped to just a destroy object action, as above, without any
     * operation call.
     * 
     * Signal Send
     * 
     * 8. A signal send maps to a send signal action for the named signal. The
     * result source element mapped from the target expression of the feature
     * invocation expression is connected by an object flow to the target input
     * pin of the send signal action. The send signal action has argument input
     * pins corresponding to the attributes of the signal. Each argument input
     * pin of the send signal action is the target of an object flow whose
     * source is the result source element of the argument expression (if there
     * is one) mapped from the tuple for the corresponding signal attribute. A
     * signal send has no result source element.
     */

    public Action mapTarget() throws MappingError {
        InvocationExpression expression = this.getInvocationExpression();
        Action action = null;
        if (expression.getIsImplicit()) {
            action = this.graph.addDestroyObjectAction(null);
        } else {
            ElementReference referent = expression.getReferent();
            FumlMapping mapping = this.fumlMap(referent);
            if (mapping instanceof ElementReferenceMapping) {
                mapping = ((ElementReferenceMapping) mapping).getMapping();
            }            
            if (mapping instanceof OperationDefinitionMapping) {
                Operation operation = 
                    ((OperationDefinitionMapping) mapping).getOperation();
                action = this.graph.addCallOperationAction(operation);
                this.resultSource = ActivityGraph.getReturnPin(action);

            } else if (mapping instanceof SignalDefinitionMapping) {
                Signal signal = ((SignalDefinitionMapping) mapping).getSignal();
                action = this.graph.addSendSignalAction(signal);
                
            } else if (mapping instanceof ActivityDefinitionMapping) {
                Behavior behavior = 
                    ((ActivityDefinitionMapping) mapping).getBehavior();
                action = this.graph.addCallBehaviorAction(behavior);
                this.resultSource = ActivityGraph.getReturnPin(action);
                 
            } else if (mapping instanceof PropertyDefinitionMapping) {
                Property associationEnd = 
                    ((PropertyDefinitionMapping) mapping).getProperty();
                action = this.graph.addReadLinkAction(associationEnd);
                this.resultSource = action.output.get(0);
                
            } else {
                this.throwError("Unknown referent mapping: " + 
                        mapping.getErrorMessage());
            }
        }
        return action;
    }

    public Action mapFeature(Action action) throws MappingError {
        InvocationExpression expression = this.getInvocationExpression();
        FeatureReference feature = expression.getFeature();
        Action thisAction = action;
        if (feature != null) {
            Expression primary = feature.getExpression();
            FumlMapping mapping = this.fumlMap(primary);
            if (!(mapping instanceof ExpressionMapping)) {
                this.throwError("Error mapping expression: " + primary);
            } else {
                ActivityNode targetNode = null;
                if (action instanceof CallOperationAction) {
                    CallOperationAction callAction = (CallOperationAction) action;
                    targetNode = callAction.target;
                    
                    if (expression.getIsDestructor()) {
                        ActivityNode targetPin = targetNode;
                        targetNode = this.graph.addForkNode("Fork");
                        
                        this.graph.addObjectFlow(targetNode, targetPin);
                        
                        DestroyObjectAction destroyAction = 
                            this.graph.addDestroyObjectAction(
                                    callAction.operation.class_);                    
                        targetPin = destroyAction.target;
                        
                        this.graph.addControlFlow(action, destroyAction);
                        
                        if (expression.getImpl().isContainedInDestructor()) {
                            this.addDestroyCheck(targetNode, targetPin);
                        } else {
                            this.graph.addObjectFlow(targetNode, targetPin);
                        }
                    }
                    
                } else if (action instanceof SendSignalAction){
                    SendSignalAction sendAction = (SendSignalAction) action;
                    
                    // NOTE: The type of the target pin must be properly set to
                    // satisfy the fUML constraint that the type of this pin has a
                    // reception for the signal being sent.
                    sendAction.target.setType(((ExpressionMapping)mapping).getType());
                    
                    targetNode = sendAction.target;
                    
                } else if (action instanceof DestroyObjectAction) {
                    DestroyObjectAction destroyAction = (DestroyObjectAction) action;
                    if (expression.getImpl().isContainedInDestructor()) {
                        targetNode = new ForkNode();
                        targetNode.setName("Fork");
                        this.addDestroyCheck(targetNode, destroyAction.target);
                    } else {
                        targetNode = destroyAction.target;
                    }
                }
    
                ActivityNode featureResult = 
                    ((ExpressionMapping) mapping).getResultSource();
                if (expression.getImpl().isSequenceFeatureInvocation()) {
                    
                    // Wrap the invocation action and input arguments mapping
                    // in an expansion region.
                    Collection<Element> elements = this.graph.getModelElements();
                    this.graph = new ActivityGraph();
                    ExpansionRegion region = this.graph.addExpansionRegion(
                            "Collect(" + action.name + ")", 
                            ExpansionKind.parallel, 
                            elements, featureResult, targetNode, 
                            this.resultSource);
                    if (this.resultSource != null) {
                        for (ExpansionNode expansionNode: region.outputElement) {
                            if (expansionNode.incoming.get(0).source == 
                                this.resultSource) {
                                this.resultSource = expansionNode;
                                break;
                            }
                        }
                    }

                    // Make the expansion region the new primary action for the
                    // mapping.
                    thisAction = region;
                    
                } else if (featureResult != null) {
                    // Connect the feature mapping result source directly to
                    // the action target pin.
                    this.graph.addObjectFlow(featureResult, targetNode);
                }
                this.graph.addAll(((ExpressionMapping)mapping).getGraph());
            }
        }
        return thisAction;
    }

    /**
     * Add the logic to test whether the context object is the object being
     * destroyed (from targetNode) and conditioning the destroy action on that.
     */
    private void addDestroyCheck(ActivityNode targetNode, ActivityNode targetPin)
        throws MappingError {
        ReadSelfAction readSelf = this.graph.addReadSelfAction(null);        
        TestIdentityAction testAction = 
            this.graph.addTestIdentityAction("self==" + targetNode.name);
        
        this.graph.addObjectFlow(readSelf.result, testAction.first);
        this.graph.addObjectFlow(targetNode, testAction.second);

        this.graph.addObjectDecisionNode("destroy check", 
                targetNode, testAction.result, targetPin, null);
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
    public ActivityNode getAssignedValueSource(String name) throws MappingError {
        return this.assignedValueSourceMap == null? null:
            this.assignedValueSourceMap.get(name);
    }
    
    @Override
    public Element getElement() {
        return this.action;
    }

    @Override
    public ActivityGraph getGraph() throws MappingError {
        this.getAction();
        return super.getGraph();
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
        Expression primary = feature == null ? null : feature.getExpression();
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
                ExpansionNode inputNode = ((ExpansionRegion) action).inputElement.get(0);
                ActivityNode target = inputNode.outgoing.get(0).target;
                if (target instanceof ForkNode) {
                    target = target.outgoing.get(0).target;
                }
                invocationAction = ((InputPin) target).owner;
            }
            if (invocationAction instanceof CallOperationAction) {
                System.out.println(prefix + " operation: "
                        + ((CallOperationAction) invocationAction).operation);
            } else if (invocationAction instanceof SendSignalAction) {
                System.out.println(prefix + " signal: " + 
                        ((SendSignalAction) invocationAction).signal);
            } else if (invocationAction instanceof CallBehaviorAction) {
                System.out.println(prefix + " behavior: " + 
                        ((CallBehaviorAction) invocationAction).behavior);
            }
        }
    }

} // InvocationExpressionMapping
