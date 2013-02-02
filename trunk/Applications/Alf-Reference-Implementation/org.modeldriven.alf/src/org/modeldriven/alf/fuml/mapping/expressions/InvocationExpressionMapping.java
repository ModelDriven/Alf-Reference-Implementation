/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import java.util.Collection;
import java.util.Map;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.fuml.mapping.units.ActivityDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.OperationDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.PropertyDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.SignalDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.FeatureReference;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.Tuple;

import org.modeldriven.alf.uml.*;

public abstract class InvocationExpressionMapping extends ExpressionMapping {

    protected Action action = null;
    protected ActivityNode resultSource = null;
    private ActivityGraph lhsGraph = this.createActivityGraph();
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
        
        // NOTE: Adding left-hand side elements here prevents them from being
        // wrapped in the expansion region mapped from a sequence feature
        // invocation.
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
            Element element = referent.getImpl().getUml();
            if (element == null) {
                FumlMapping mapping = this.fumlMap(referent);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping) mapping).getMapping();
                }          
                if (mapping instanceof OperationDefinitionMapping) {
                    element = 
                        ((OperationDefinitionMapping) mapping).getOperation();
                } else if (mapping instanceof SignalDefinitionMapping) {
                    element = ((SignalDefinitionMapping) mapping).getSignal();
                    
                } else if (mapping instanceof ActivityDefinitionMapping) {
                    element = 
                        ((ActivityDefinitionMapping) mapping).getBehavior();
                } else if (mapping instanceof PropertyDefinitionMapping) {
                    element = 
                        ((PropertyDefinitionMapping) mapping).getProperty();
                    
                } else {
                    this.throwError("Unknown referent mapping: " + 
                            mapping.getErrorMessage());
                }
            }
            if (element instanceof Operation) {
                action = this.graph.addCallOperationAction((Operation)element);
                this.resultSource = ActivityGraph.getReturnPin(action);

            } else if (element instanceof Signal) {
                action = this.graph.addSendSignalAction((Signal)element);
                
            } else if (element instanceof Behavior) {
                action = this.graph.addCallBehaviorAction((Behavior)element);
                this.resultSource = ActivityGraph.getReturnPin(action);
                 
            } else if (element instanceof Property) {
                action = this.graph.addReadLinkAction((Property)element);
                this.resultSource = action.getOutput().get(0);
                
            } else {
                this.throwError("Unknown referent: " + element);
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
                    targetNode = callAction.getTarget();
                    
                    if (expression.getIsDestructor()) {
                        ActivityNode targetPin = targetNode;
                        targetNode = this.graph.addForkNode("Fork");
                        
                        this.graph.addObjectFlow(targetNode, targetPin);
                        
                        DestroyObjectAction destroyAction = 
                            this.graph.addDestroyObjectAction(
                                    callAction.getOperation().getClass_());                    
                        targetPin = destroyAction.getTarget();
                        
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
                    sendAction.getTarget().setType(((ExpressionMapping)mapping).getType());
                    
                    targetNode = sendAction.getTarget();
                    
                } else if (action instanceof DestroyObjectAction) {
                    DestroyObjectAction destroyAction = (DestroyObjectAction) action;
                    if (expression.getImpl().isContainedInDestructor()) {
                        targetNode = this.create(ForkNode.class);
                        targetNode.setName("Fork");
                        this.addDestroyCheck(targetNode, destroyAction.getTarget());
                    } else {
                        targetNode = destroyAction.getTarget();
                    }
                }
    
                ActivityNode featureResult = 
                    ((ExpressionMapping) mapping).getResultSource();
                if (expression.getImpl().isSequenceFeatureInvocation()) {
                    
                    // Wrap the invocation action and input arguments mapping
                    // in an expansion region.
                    Collection<Element> elements = this.graph.getModelElements();
                    this.graph = this.createActivityGraph();
                    ExpansionRegion region = this.graph.addExpansionRegion(
                            "Collect(" + action.getName() + ")", 
                            "parallel", 
                            elements, featureResult, targetNode, 
                            this.resultSource);
                    if (this.resultSource != null) {
                        for (ExpansionNode expansionNode: region.getOutputElement()) {
                            if (expansionNode.getIncoming().get(0).getSource().
                                    equals(this.resultSource)) {
                                this.resultSource = expansionNode;
                                break;
                            }
                        }
                    }
                    
                    // Wrap the feature expression in a structured activity
                    // node as the source of a control flow to the expansion
                    // region.
                    ActivityNode featureNode = 
                            this.graph.addStructuredActivityNode(
                                    "Feature(" + featureResult.getName() + ")", 
                                    mapping.getModelElements());
                    this.graph.addControlFlow(featureNode, region);

                    // Make the expansion region the new primary action for the
                    // mapping.
                    thisAction = region;
                    
                } else if (featureResult != null) {
                    // Connect the feature mapping result source directly to
                    // the action target pin.
                    this.graph.addObjectFlow(featureResult, targetNode);
                    this.graph.addAll(((ExpressionMapping)mapping).getGraph());
                }
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
            this.graph.addTestIdentityAction("self==" + targetNode.getName());
        
        this.graph.addObjectFlow(readSelf.getResult(), testAction.getFirst());
        this.graph.addObjectFlow(targetNode, testAction.getSecond());

        this.graph.addObjectDecisionNode("destroy check", 
                targetNode, testAction.getResult(), targetPin, null);
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
                ExpansionNode inputNode = ((ExpansionRegion) action).getInputElement().get(0);
                ActivityNode target = inputNode.getOutgoing().get(0).getTarget();
                if (target instanceof ForkNode) {
                    target = target.getOutgoing().get(0).getTarget();
                }
                invocationAction = ((InputPin) target).getOwner();
            }
            if (invocationAction instanceof CallOperationAction) {
                System.out.println(prefix + " operation: "
                        + ((CallOperationAction) invocationAction).getOperation());
            } else if (invocationAction instanceof SendSignalAction) {
                System.out.println(prefix + " signal: " + 
                        ((SendSignalAction) invocationAction).getSignal());
            } else if (invocationAction instanceof CallBehaviorAction) {
                System.out.println(prefix + " behavior: " + 
                        ((CallBehaviorAction) invocationAction).getBehavior());
            }
        }
    }

} // InvocationExpressionMapping
