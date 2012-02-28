/*
 * Copyright 2011-2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.CompleteActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Activities.ExtraStructuredActivities.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;
import fUML.Syntax.CommonBehaviors.Communications.*;

public class ActivityGraph {
    
    private Collection<Element> modelElements = new ArrayList<Element>();
    
    public ActivityGraph() {
    }
    
    public ActivityGraph(ActivityGraph graph) {
        this.addAll(graph);
    }
    
    public void add(ActivityNode node) {
        this.modelElements.add(node);
    }
    
    public void add(ActivityEdge edge) {
        this.modelElements.add(edge);
    }
    
    public void addAll(ActivityGraph graph) {
        this.modelElements.addAll(graph.getModelElements());
    }
    
    public void remove(Element element) {
        this.modelElements.remove(element);
    }
    
    public void removeAll(Collection<Element> elements) {
        this.modelElements.removeAll(elements);
    }
    
    public Collection<Element> getModelElements() {
        return new ArrayList<Element>(this.modelElements);
    }
    
    public boolean isEmpty() {
        return this.modelElements.isEmpty();
    }
    
    // Activity Edges
    
    public ObjectFlow addObjectFlow(ActivityNode source, ActivityNode target) {
        ObjectFlow flow = createObjectFlow(source, target);
        this.add(flow);
        return flow;
    }

    public ControlFlow addControlFlow(ActivityNode source, ActivityNode target) {
        ControlFlow flow = createControlFlow(source, target);
        this.add(flow);
        return flow;
    }
    
    // Control Nodes
    
    public ActivityFinalNode addActivityFinalNode(String name) {
        ActivityFinalNode finalNode = new ActivityFinalNode();
        finalNode.setName(name);
        this.add(finalNode);
        return finalNode;
    }
    
    public DecisionNode addDecisionNode(
            String label, boolean isObjectFlow,
            ActivityNode inputSource, ActivityNode decisionSource,
            ActivityNode trueTarget, ActivityNode falseTarget) {
        DecisionNode decision = new DecisionNode();
        decision.setName("Decision(" + label + ")");
        this.add(decision);
        
        if (inputSource != null) {
            if (isObjectFlow) {
                this.addObjectFlow(inputSource, decision);
            } else {
                this.addControlFlow(inputSource, decision);
            }
        }
        
        if (decisionSource != null) {
            decision.setDecisionInputFlow(createObjectFlow(decisionSource, decision));
            this.add(decision.decisionInputFlow);
        }

        if (trueTarget != null) {                    
            LiteralBoolean literalTrue = new LiteralBoolean();
            literalTrue.setName("Value(true)");
            literalTrue.setValue(true);
            
            ActivityEdge flow = isObjectFlow? 
                    this.addObjectFlow(decision, trueTarget):
                    this.addControlFlow(decision, trueTarget);
            flow.setGuard(literalTrue);    
        }
        
        if (falseTarget != null) {                    
            LiteralBoolean literalFalse = new LiteralBoolean();
            literalFalse.setName("Value(false)");
            literalFalse.setValue(false);
            
            ActivityEdge flow = isObjectFlow? 
                    this.addObjectFlow(decision, falseTarget):
                    this.addControlFlow(decision, falseTarget);
            flow.setGuard(literalFalse);    
        }
        return decision;
    }
    
    public DecisionNode addObjectDecisionNode(
            String label,
            ActivityNode inputSource, ActivityNode decisionSource,
            ActivityNode trueTarget, ActivityNode falseTarget) {
        return this.addDecisionNode(label, true, 
                inputSource, decisionSource, trueTarget, falseTarget);
    }
    
    public DecisionNode addControlDecisionNode(
            String label,
            ActivityNode inputSource, ActivityNode decisionSource,
            ActivityNode trueTarget, ActivityNode falseTarget) {
        return this.addDecisionNode(label, false, 
                inputSource, decisionSource, trueTarget, falseTarget);
    }
    
    public ForkNode addForkNode(String name) {
        ForkNode fork = new ForkNode();
        fork.setName(name);
        this.add(fork);
        return fork;
    }
    
    public InitialNode addInitialNode(String name) {
        InitialNode initialNode = new InitialNode();
        initialNode.setName(name);
        this.add(initialNode);
        return initialNode;
    }
    
    public MergeNode addMergeNode(String name) {
        MergeNode mergeNode = new MergeNode();
        mergeNode.setName(name);
        this.add(mergeNode);
        return mergeNode;
    }
    
    // Actions
    
    public AddStructuralFeatureValueAction addAddStructuralFeatureValueAction(
            Property property, boolean isReplaceAll) {
        AddStructuralFeatureValueAction writeAction = 
            new AddStructuralFeatureValueAction();
        writeAction.setName("Write(" + property.qualifiedName + ")");
        writeAction.setStructuralFeature(property);
        writeAction.setIsReplaceAll(isReplaceAll);
        this.add(writeAction);
        
        Classifier featuringClassifier = property.featuringClassifier.get(0);
        writeAction.setObject(createInputPin(
                writeAction.name + ".object", featuringClassifier, 1, 1));
        writeAction.setValue(createInputPin(
                writeAction.name + ".value", property.typedElement.type, 1, 1));
        writeAction.setResult(createOutputPin(
                writeAction.name + ".result", featuringClassifier, 1, 1));
        
        if (property.multiplicityElement.isOrdered && !isReplaceAll) {
            writeAction.setInsertAt(ActivityGraph.createInputPin(
                    writeAction + ".insertAt", 
                    FumlMapping.getUnlimitedNaturalType(), 1, 1));
        }
        
        return writeAction;
    }
    
    public CallBehaviorAction addCallBehaviorAction(Behavior behavior) {
        CallBehaviorAction callAction = new CallBehaviorAction();
        callAction.setName("Call(" + behavior.qualifiedName + ")");
        callAction.setBehavior(behavior);
        addPinsFromParameters(callAction, behavior.ownedParameter);
        this.add(callAction);
        return callAction;
    }
    
    public CallOperationAction addCallOperationAction(Operation operation) {
        CallOperationAction callAction = new CallOperationAction();
        callAction.setName("Call(" + operation.qualifiedName + ")");
        callAction.setOperation(operation);
        callAction.setTarget(createInputPin(
                callAction.name + ".target", operation.class_, 1, 1));
        addPinsFromParameters(callAction, operation.ownedParameter);
        this.add(callAction);
        return callAction;
    }
    
    public ClearAssociationAction addClearAssociationAction(Association association) {
        ClearAssociationAction clearAction = new ClearAssociationAction();
        clearAction.setName("Clear(" + association.qualifiedName + ")");
        clearAction.setAssociation(association);
        clearAction.setObject(createInputPin(
                clearAction.name + ".object", null, 1, 1));
        clearAction.object.multiplicityElement.isOrdered = false;
        this.add(clearAction);
        return clearAction;
    }
    
    public ClearStructuralFeatureAction addClearStructuralFeatureAction(
            Property property) {
        ClearStructuralFeatureAction clearAction = new ClearStructuralFeatureAction();
        clearAction.setName("Clear(" + property.qualifiedName + ")");
        clearAction.setStructuralFeature(property);
        this.add(clearAction);

        Classifier featuringClassifier = property.featuringClassifier.get(0);
        clearAction.setObject(createInputPin(
                clearAction.name + ".object", featuringClassifier, 1, 1));
        clearAction.object.multiplicityElement.isOrdered = false;
        clearAction.setResult(createOutputPin(
                clearAction.name + ".result", featuringClassifier, 1, 1));
        return clearAction;
    }
    
    public CreateLinkAction addCreateLinkAction(Association association) {
        CreateLinkAction createAction = new CreateLinkAction();
        createAction.setName("Create(" + association.qualifiedName + ")");
        for (Property end: association.ownedEnd) {
            InputPin valuePin = createInputPin(
                    createAction.name + ".value(" + end.name + ")", 
                    end.typedElement.type, 1, 1);
            valuePin.setIsOrdered(end.multiplicityElement.isOrdered);
            createAction.addInputValue(valuePin);
            LinkEndCreationData endData = new LinkEndCreationData();
            endData.setEnd(end);
            endData.setValue(valuePin);
            endData.setIsReplaceAll(false);
            if (end.multiplicityElement.isOrdered) {
                InputPin insertAtPin = createInputPin(
                        createAction.name + ".insertAt(" + end.name + ")", 
                        FumlMapping.getUnlimitedNaturalType(), 1, 1);
                createAction.addInputValue(insertAtPin);
                endData.setInsertAt(insertAtPin);
            }
            createAction.addEndData(endData);
        }
        this.add(createAction);
        return createAction;
    }
    
    public CreateObjectAction addCreateObjectAction(Class_ class_) {
        CreateObjectAction createAction = new CreateObjectAction();
        createAction.setName("Create(" + class_.qualifiedName + ")");
        createAction.setClassifier(class_);            
        createAction.setResult(createOutputPin(
                createAction.name + ".result", class_, 1, 1));
        this.add(createAction);
        return createAction;
    }
    
    public DestroyLinkAction addDestroyLinkAction(Association association) {
        DestroyLinkAction destroyAction = new DestroyLinkAction();
        destroyAction.setName("Destroy(" + association.qualifiedName + ")");
        for (Property end: association.ownedEnd) {
            InputPin valuePin = createInputPin(
                    destroyAction.name + ".value(" + end.name + ")", 
                    end.typedElement.type, 1, 1);
            valuePin.setIsOrdered(end.multiplicityElement.isOrdered);
            destroyAction.addInputValue(valuePin);
            LinkEndDestructionData endData = new LinkEndDestructionData();
            endData.setEnd(end);
            endData.setValue(valuePin);
            endData.setIsDestroyDuplicates(!end.multiplicityElement.isOrdered);
            if (end.multiplicityElement.isOrdered) {
                InputPin destroyAtPin = createInputPin(
                        destroyAction.name + ".destroyAt(" + end.name + ")", 
                        FumlMapping.getUnlimitedNaturalType(), 1, 1);
                destroyAction.addInputValue(destroyAtPin);
                endData.setDestroyAt(destroyAtPin);
            }
            destroyAction.addEndData(endData);
        }
        this.add(destroyAction);
        return destroyAction;
    }
    public DestroyObjectAction addDestroyObjectAction(Class_ class_) {
        DestroyObjectAction destroyAction = new DestroyObjectAction();
        destroyAction.setName("DestroyObject");
        destroyAction.setIsDestroyLinks(true);
        destroyAction.setIsDestroyOwnedObjects(true);
        destroyAction.setTarget(createInputPin(
                destroyAction.name + ".target", class_, 1, 1));
        this.add(destroyAction);
        return destroyAction;
    }
    
    public LoopNode addLoopNode(
            String name, boolean isTestedFirst, InputPin... inputs) {
        LoopNode loopNode = new LoopNode();
        loopNode.setName(name);
        loopNode.setIsTestedFirst(isTestedFirst);
        for (InputPin input: inputs) {
            input.setName(loopNode.name + ".loopVariableInput(" + input.name + ")");
            loopNode.addLoopVariableInput(input);
            loopNode.addLoopVariable(createOutputPin(
                    loopNode.name + ".loopVariable(" + input.name + ")", 
                    input.typedElement.type, 
                    input.multiplicityElement.lower, 
                    input.multiplicityElement.upper.naturalValue));
            loopNode.addResult(createOutputPin(
                    loopNode.name + ".result(" + input.name + ")", 
                    input.typedElement.type, 
                    input.multiplicityElement.lower, 
                    input.multiplicityElement.upper.naturalValue));
        }
        this.add(loopNode);
        return loopNode;
    }
    
    public void addLoopTest(
            LoopNode loopNode, Collection<Element> test, OutputPin decider) {
        this.addToStructuredNode(loopNode, test);
        for (Element element: test) {
            if (element instanceof ExecutableNode) {
                loopNode.test.add((ExecutableNode)element);
            }
        }
        loopNode.setDecider(decider);
    }
    
    public void addLoopBodyPart(
            LoopNode loopNode, Collection<Element> bodyPart, OutputPin... bodyOutputs) {
        this.addToStructuredNode(loopNode, bodyPart);
        for (Element element: bodyPart) {
            if (element instanceof ExecutableNode) {
                loopNode.bodyPart.add((ExecutableNode)element);
            }
        }
        for (OutputPin bodyOutput: bodyOutputs) {
            loopNode.bodyOutput.add(bodyOutput);
        }
    }
    
    public ReadExtentAction addReadExtentAction(Class_ class_) {
        ReadExtentAction readExtentAction = new ReadExtentAction();
        readExtentAction.setName(
                "ReadExtent(" + (class_ == null? "": class_.name) + ")");
        readExtentAction.setClassifier(class_);
        readExtentAction.setResult(createOutputPin(
                readExtentAction.name + ".result", class_, 0, -1));
        this.add(readExtentAction);
        return readExtentAction;
    }
    
    public ReadIsClassifiedObjectAction addReadIsClassifiedObjectAction(
            Classifier classifier, boolean isDirect) {
        ReadIsClassifiedObjectAction action = new ReadIsClassifiedObjectAction();
        action.setName("ReadIsClassifiedObject(" + 
                (classifier == null? "": classifier.name) + ")");
        action.setClassifier(classifier);
        action.setIsDirect(isDirect);
        action.setObject(createInputPin(action.name + ".object", null, 1, 1));
        action.setResult(createOutputPin(
                action.name + ".result", FumlMapping.getBooleanType(), 1, 1));
        this.add(action);
        return action;
    }
    
    public ReadLinkAction addReadLinkAction(Property associationEnd) {
        ReadLinkAction readAction = new ReadLinkAction();
        readAction.setName("ReadLink(" + associationEnd.qualifiedName + ")");
        
        LinkEndData openEnd = new LinkEndData();
        openEnd.setEnd(associationEnd);
        readAction.addEndData(openEnd);
        
        List<Property> otherEnds = 
            new ArrayList<Property>(associationEnd.association.ownedEnd);
        otherEnds.remove(associationEnd);
        addPinsFromProperties(readAction, otherEnds);
        
        addPin(readAction, new OutputPin(), associationEnd);
        this.add(readAction);
        return readAction;
    }
    
    public ReadSelfAction addReadSelfAction(Type type) {
        ReadSelfAction readSelfAction = new ReadSelfAction();
        readSelfAction.setName("ReadSelf");
        readSelfAction.setResult(createOutputPin(
                readSelfAction.name + ".result", type, 1, 1));
        this.add(readSelfAction);
        return readSelfAction;
    }
    
    public ReadStructuralFeatureAction addReadStructuralFeatureAction(
            Property property) {
        ReadStructuralFeatureAction readAction = new ReadStructuralFeatureAction();
        readAction.setName("ReadStructuralFeature(" + property.name + ")");
        readAction.setStructuralFeature(property);
        readAction.setObject(ActivityGraph.createInputPin(
                readAction.name + ".object", 
                property.featuringClassifier.get(0), 1, 1));
        readAction.setResult(ActivityGraph.createOutputPin(
                readAction.name + ".result", property.typedElement.type, 1, 1));
        this.add(readAction);
        return readAction;
    }
    
    public ReduceAction addReduceAction(
            Behavior behavior, Type type, boolean isOrdered) {
        ReduceAction reduceAction = new ReduceAction();
        reduceAction.setName("Reduce(" + behavior.name + ")");
        reduceAction.setReducer(behavior);
        reduceAction.setIsOrdered(isOrdered);
        reduceAction.setCollection(ActivityGraph.createInputPin(
                reduceAction.name + ".collection", type, 0, -1));
        reduceAction.setResult(ActivityGraph.createOutputPin(
                reduceAction.name + ".result", type, 0, 1));
        this.add(reduceAction);
        return reduceAction;
    }
    
    public RemoveStructuralFeatureValueAction addRemoveStructuralFeatureValueAction(
            Property property, boolean isRemoveDuplicates) {
        RemoveStructuralFeatureValueAction removeAction = 
            new RemoveStructuralFeatureValueAction();
        removeAction.setName("Remove(" + property.qualifiedName + ")");
        removeAction.setStructuralFeature(property);
        removeAction.setIsRemoveDuplicates(isRemoveDuplicates);
        this.add(removeAction);
        
        Classifier featuringClassifier = property.featuringClassifier.get(0);
        removeAction.setObject(createInputPin(
                removeAction.name + ".object", featuringClassifier, 1, 1));
        removeAction.setResult(createOutputPin(
                removeAction.name + ".result", featuringClassifier, 1, 1));
        
        if (!property.multiplicityElement.isOrdered || 
                property.multiplicityElement.isUnique || 
                isRemoveDuplicates) {
            removeAction.setValue(createInputPin(
                    removeAction.name + ".value", property.typedElement.type, 1, 1));
        } else {
            removeAction.setRemoveAt(createInputPin(
                    removeAction.name + ".removeAt", property.typedElement.type, 1, 1));
        }
        
        return removeAction;
    }
    
    public SendSignalAction addSendSignalAction(Signal signal) {
        SendSignalAction sendAction = new SendSignalAction();
        sendAction.setName("SendSignal(" + signal.qualifiedName + ")");
        sendAction.setSignal(signal);
        sendAction.setTarget(createInputPin(
                sendAction.name + ".target", null, 1, 1));
        addPinsFromProperties(sendAction, signal.attribute);
        this.add(sendAction);
        return sendAction;
    }
    
    public StartObjectBehaviorAction addStartObjectBehaviorAction(Class_ class_) {
        StartObjectBehaviorAction startAction = 
            new StartObjectBehaviorAction();
        startAction.setName("Start(" + (class_ == null? "any": class_.name) + ")");
        startAction.setObject(createInputPin(
                startAction.name + ".object", class_, 1, 1));
        this.add(startAction);
        return startAction;
    }
    
    public TestIdentityAction addTestIdentityAction(String condition) {
        TestIdentityAction testAction = new TestIdentityAction();
        testAction.setName("Test(" + condition + ")");
        testAction.setFirst(createInputPin(testAction.name + ".first", null, 1, 1));
        testAction.setSecond(createInputPin(testAction.name + ".second", null, 1, 1));        
        testAction.setResult(createOutputPin(
                    testAction.name + ".result",
                    FumlMapping.getBooleanType(),
                    1, 1));
        this.add(testAction);
        return testAction;
    }
    
    public ValueSpecificationAction addValueSpecificationAction(
            ValueSpecification value, String literalString) {
        ValueSpecificationAction valueAction = new ValueSpecificationAction();
        valueAction.setName("Value(" + literalString + ")");
        valueAction.setValue(value);
        valueAction.setResult(createOutputPin(
                valueAction.name + ".result", value.type, 1, 1));
        this.add(valueAction);        
        return valueAction;
    }
    
    public ValueSpecificationAction addNullValueSpecificationAction() {
        return this.addValueSpecificationAction(new LiteralNull(), "null");
    }
    
    public ValueSpecificationAction addBooleanValueSpecificationAction(
            boolean value) {
        LiteralBoolean literal = new LiteralBoolean();
        literal.setValue(value);
        literal.setType(FumlMapping.getBooleanType());
        return this.addValueSpecificationAction(literal, Boolean.toString(value));
    }

    public ValueSpecificationAction addUnlimitedNaturalValueSpecificationAction(
            int value) {
        LiteralUnlimitedNatural literal = new LiteralUnlimitedNatural();
        literal.setValue(value);
        literal.setType(FumlMapping.getUnlimitedNaturalType());
        return this.addValueSpecificationAction(literal, 
                value < 0? "*": Integer.toString(value));
    }

    public ValueSpecificationAction addNaturalValueSpecificationAction(
            int value) {
        LiteralInteger literal = new LiteralInteger();
        literal.setValue(value);
        literal.setType(FumlMapping.getNaturalType());
        return this.addValueSpecificationAction(literal, Integer.toString(value));
    }

    public ValueSpecificationAction addStringValueSpecificationAction(
            String value) {
        LiteralString literal = new LiteralString();
        literal.setValue(value);
        literal.setType(FumlMapping.getStringType());
        return this.addValueSpecificationAction(literal, "\"" + value + "\"");
    }
    
    public ValueSpecificationAction addDataValueSpecificationAction(
            InstanceSpecification instance) {
        InstanceValue value = new InstanceValue();
        value.setType(instance.classifier.get(0));
        value.setInstance(instance);        
        return this.addValueSpecificationAction(value, instance.name);
    }
    
    public ValueSpecificationAction addDataValueSpecificationAction(
            DataType dataType) {
        InstanceSpecification instance = new InstanceSpecification();
        instance.setName(dataType.name);
        instance.addClassifier(dataType);
        return this.addDataValueSpecificationAction(instance);
    }
    
    // Structured Activity Nodes
    
    public StructuredActivityNode addStructuredActivityNode( 
            String name,
            Collection<Element> nestedElements) {
        StructuredActivityNode node = new StructuredActivityNode();
        node.setName(name);        
        this.add(node);
        this.addToStructuredNode(node, nestedElements);
        return node;
    }
    
    public ExpansionRegion addExpansionRegion(
            String name, 
            ExpansionKind mode,
            Collection<Element> nestedElements, 
            ActivityNode inputSource,
            ActivityNode inputTarget,
            ActivityNode resultSource) {
        ExpansionRegion region = new ExpansionRegion();
        region.setName(name);
        region.setMode(mode);
        this.add(region);

        // Add nodes and control flows to expansion region.
        // NOTE: Control flows must be between nested elements.
        for (Element element : nestedElements) {
            if (element instanceof ActivityNode) {
                region.addNode((ActivityNode) element);
            } else if (element instanceof ControlFlow) {
                region.addEdge((ActivityEdge) element);
            }
        }
        
        // Add object flows to expansion region. If an incoming object flow
        // crosses into the region, add an input pin at the boundary. If an
        // outgoing object flow crosses out of the region, add an output
        // expansion node at the boundary.
        for (Element element : nestedElements) {
            if (element instanceof ObjectFlow) {
                ActivityEdge edge = (ActivityEdge) element;
                ActivityNode source = edge.source;
                ActivityNode target = edge.target;
                boolean sourceIsContained = isContainedIn(source, region);
                boolean targetIsContained = isContainedIn(target, region);
                if (sourceIsContained && targetIsContained) {
                    region.addEdge(edge);
                } else if (!sourceIsContained && targetIsContained){
                    source.outgoing.remove(edge);
                    target.incoming.remove(edge);

                    int lower = 0;
                    int upper = -1;
                    Type type = null;
                    if (target instanceof ObjectNode) {
                        type = ((ObjectNode)target).typedElement.type;
                        if (target instanceof Pin) {
                            Pin targetPin = (Pin)target;
                            lower = targetPin.multiplicityElement.lower;
                            upper = targetPin.multiplicityElement.upper.naturalValue;
                        }
                    }

                    InputPin pin = createInputPin(
                            region.name + ".input(" + source.name + ")",
                            type, lower, upper);
                    region.addStructuredNodeInput(pin);
                    region.addEdge(createObjectFlow(pin, target));
                    this.addObjectFlow(source, pin);
                } else if (sourceIsContained && !targetIsContained) {
                    source.outgoing.remove(edge);
                    target.incoming.remove(edge);

                    ExpansionNode outputNode = new ExpansionNode();
                    outputNode.setName(region.name + 
                            ".outputElement(" + edge.source.name + ")");
                    region.addOutputElement(outputNode);
                    region.addEdge(createObjectFlow(source, outputNode));
                    this.add(outputNode);
                    this.addObjectFlow(outputNode, target);
                } else {
                    this.add(edge);
                }
            }
        }

        ExpansionNode inputNode = new ExpansionNode();
        inputNode.setName(region.name + ".inputElement");
        region.addInputElement(inputNode);
        this.add(inputNode);
        
        // Connect external input source (if any) to region input node.
        if (inputSource != null) {
            this.addObjectFlow(inputSource, inputNode);
        }

        // Connect region input node to internal input target (if any).
        if (inputTarget != null) {
            region.addEdge(createObjectFlow(inputNode, inputTarget));
        }

        // Connect internal result source (if any) to region output node.
        if (resultSource != null) {
            ExpansionNode outputNode = new ExpansionNode();
            outputNode.setName(region.name + ".outputElement");
            region.addOutputElement(outputNode);
            this.add(outputNode);
            
            region.addEdge(createObjectFlow(resultSource, outputNode));
        }
        
        return region;
    }
    
    public ExpansionNode addInputExpansionNode(
            String label, ExpansionRegion region) {
        ExpansionNode node = new ExpansionNode();
        node.setName(region.name + ".inputElement(" + label + ")");
        region.addInputElement(node);
        this.add(node);
        return node;
    }
    
    public ExpansionNode addOutputExpansionNode(
            String label, ExpansionRegion region) {
        ExpansionNode node = new ExpansionNode();
        node.setName(region.name + ".outputElement(" + label + ")");
        region.addOutputElement(node);
        this.add(node);
        return node;
    }
    
    public void addToStructuredNode(
            StructuredActivityNode node, 
            Collection<Element> nestedElements) {
        for (Element element: nestedElements) {
            if (element instanceof ActivityNode) {
                node.addNode((ActivityNode)element);
            } else if (element instanceof ControlFlow) {
                node.addEdge((ActivityEdge)element);
            }
        }
        for (Element element: nestedElements) {
            if (element instanceof ObjectFlow) {
                ActivityEdge edge = (ActivityEdge)element;
                if (isContainedIn(edge.source, node) &&
                        isContainedIn(edge.target, node)) {
                    node.addEdge(edge);
                } else {
                    this.add(edge);
                }
            }
        }
    }

    // Static Helper Methods
    
    public static void setPin(Pin pin, String name, Type type, int lower, int upper) {
        pin.setName(name);
        pin.setType(type);
        pin.setLower(lower);
        pin.setUpper(upper);
        pin.setIsOrdered(true);
        pin.setIsUnique(false);
    }
    
    public static InputPin createInputPin(String name, Type type, int lower, int upper) {
        InputPin pin = new InputPin();
        setPin(pin, name, type, lower, upper);
        return pin;
    }

    public static OutputPin createOutputPin(String name, Type type, int lower, int upper) {
        OutputPin pin = new OutputPin();
        setPin(pin, name, type, lower, upper);
        return pin;
    }
    
    public static ObjectFlow createObjectFlow(ActivityNode source, ActivityNode target) {
        ObjectFlow flow = new ObjectFlow();
        flow.setSource(source);
        flow.setTarget(target);
        return flow;
    }

    public static ControlFlow createControlFlow(ActivityNode source, ActivityNode target) {
        ControlFlow flow = new ControlFlow();
        flow.setSource(source);
        flow.setTarget(target);
        return flow;
    }
    
    public static void addTo(
            StructuredActivityNode node, 
            Collection<Element> nestedElements,
            Collection<Element> outerElements) {
        ActivityGraph graph = new ActivityGraph();
        graph.addToStructuredNode(node, nestedElements);
        outerElements.addAll(graph.getModelElements());
    }

    private static void addPinsFromParameters(
            Action action, List<Parameter> parameters) {

        for (Parameter parameter : parameters) {
            ParameterDirectionKind direction = parameter.direction;
            
            // NOTE: Both an input pin AND and output pin are added for an inout
            // parameter.
            if (direction == ParameterDirectionKind.in || 
                    direction == ParameterDirectionKind.inout) {
                addPin(action, new InputPin(), parameter);
            }
            if (direction == ParameterDirectionKind.out
                    || direction == ParameterDirectionKind.inout
                    || direction == ParameterDirectionKind.return_) {
                addPin(action, new OutputPin(), parameter);
            }
        }
    }

    private static void addPin(Action action, Pin pin, Parameter parameter) {
        pin.setLower(parameter.multiplicityElement.lower);
        pin.setUpper(parameter.multiplicityElement.upper.naturalValue);
        pin.setIsOrdered(parameter.multiplicityElement.isOrdered);
        pin.setIsUnique(parameter.multiplicityElement.isUnique);
        pin.setType(parameter.type);

        if (pin instanceof InputPin) {
            if (action instanceof InvocationAction) {
                pin.setName(action.name + ".argument(" + parameter.name + ")");
                ((InvocationAction) action).addArgument((InputPin)pin);
            }
        } else if (action instanceof CallAction) {
            pin.setName(action.name + ".result(" + parameter.name + ")");
            ((CallAction) action).addResult((OutputPin)pin);
        }
    }
    
    private static void addPinsFromProperties(
            Action action, List<Property> properties) {
        for (Property property : properties) {
            addPin(action, new InputPin(), property);
        }
    }

    private static void addPin(Action action, Pin pin, Property property) {
        pin.setLower(property.multiplicityElement.lower);
        pin.setLower(property.multiplicityElement.upper.naturalValue);
        pin.setIsOrdered(property.multiplicityElement.isOrdered);
        pin.setIsUnique(property.multiplicityElement.isUnique);
        pin.setType(property.typedElement.type);

        if (pin instanceof InputPin) {
            InputPin inputPin = new InputPin();
            if (action instanceof SendSignalAction) {
                inputPin.setName(action.name + ".argument(" + property.name + ")");
                ((InvocationAction) action).addArgument(inputPin);
            } else if (action instanceof ReadLinkAction) {
                inputPin.setName(action.name + ".inputValue(" + property.name + ")");
                LinkEndData endData = new LinkEndData();
                endData.setEnd(property);
                endData.setValue(inputPin);
                ((ReadLinkAction) action).addInputValue(inputPin);
                ((ReadLinkAction) action).addEndData(endData);
            }
        } else if (action instanceof ReadLinkAction) {
            pin.setName(action.name + ".result");
            ((ReadLinkAction) action).setResult((OutputPin) pin);
        }
    }

    public static OutputPin getReturnPin(Action action) {
        List<Parameter> parameters =
            action instanceof CallOperationAction? 
                    ((CallOperationAction)action).operation.ownedParameter:
            action instanceof CallBehaviorAction?
                    ((CallBehaviorAction)action).behavior.ownedParameter:
            new ArrayList<Parameter>();
        int i = 0;
        for (Parameter parameter: parameters) {
            ParameterDirectionKind direction = parameter.direction;
            if (direction == ParameterDirectionKind.return_) {
                return action.output.get(i);
            } else if (direction == ParameterDirectionKind.out ||
                    direction == ParameterDirectionKind.inout) {
                i++;
            }
        }
        return null;
    }

    /**
     * Checks that an activity node is contained directly within a structured 
     * activity node or indirectly within another structured activity node 
     * nested within the given one.
     */
    public static boolean isContainedIn(
            ActivityNode node, StructuredActivityNode container) {
        // NOTE: A special check is necessary for loop variables, because the
        // loopVariable association is not an ownership association.
        if (container instanceof LoopNode && 
                ((LoopNode)container).loopVariable.contains(node)) {
            return true;
        } else {
            if (node instanceof Pin) {
                node = (ActivityNode)node.owner;
                
                // A pin that is owned by a structured activity node is
                // considered to be "contained in" that node.
                if (node == container) {
                    return true;
                }
            }
            ActivityNode inStructuredNode = node.inStructuredNode;
            return inStructuredNode != null && (
                    inStructuredNode == container || 
                    isContainedIn(inStructuredNode, container));
            }
    }

}
