/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * Copyright 2013 Ivar Jacobson International SA
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.uml.*;

public class ActivityGraph {
    
    private ElementFactory elementFactory = null;
    private Collection<Element> modelElements = new ArrayList<Element>();
    
    public ActivityGraph(ElementFactory elementFactory) {
        this.elementFactory = elementFactory;
    }
    
    public ActivityGraph(ActivityGraph graph) {
        this(graph.getElementFactory());
        this.addAll(graph);
    }
    
    public ElementFactory getElementFactory() {
        return this.elementFactory;
    }
    
    public <T extends Element> T create(Class<T> class_) {
        return this.elementFactory.newInstance(class_);
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
    
    public void clear() {
        this.modelElements.clear();
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
        ActivityFinalNode finalNode = this.create(ActivityFinalNode.class);
        finalNode.setName(name);
        this.add(finalNode);
        return finalNode;
    }
    
    public DecisionNode addDecisionNode(
            String label, boolean isObjectFlow,
            ActivityNode inputSource, ActivityNode decisionSource,
            ActivityNode trueTarget, ActivityNode falseTarget) {
        DecisionNode decision = this.create(DecisionNode.class);
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
            this.add(decision.getDecisionInputFlow());
        }

        if (trueTarget != null) {                    
            LiteralBoolean literalTrue = this.create(LiteralBoolean.class);
            literalTrue.setName("Value(true)");
            literalTrue.setValue(true);
            
            ActivityEdge flow = isObjectFlow? 
                    this.addObjectFlow(decision, trueTarget):
                    this.addControlFlow(decision, trueTarget);
            flow.setGuard(literalTrue);    
        }
        
        if (falseTarget != null) {                    
            LiteralBoolean literalFalse = this.create(LiteralBoolean.class);
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
        ForkNode fork = this.create(ForkNode.class);
        fork.setName(name);
        this.add(fork);
        return fork;
    }
    
    public InitialNode addInitialNode(String name) {
        InitialNode initialNode = this.create(InitialNode.class);
        initialNode.setName(name);
        this.add(initialNode);
        return initialNode;
    }
    
    public JoinNode addJoinNode(String name) {
        JoinNode join = this.create(JoinNode.class);
        join.setName(name);
        this.add(join);
        return join;
    }
    
    public MergeNode addMergeNode(String name) {
        MergeNode mergeNode = this.create(MergeNode.class);
        mergeNode.setName(name);
        this.add(mergeNode);
        return mergeNode;
    }
    
    // Actions
    
    public AcceptEventAction addAcceptEventAction(
            Collection<Signal> signals, boolean hasOutput) {
        AcceptEventAction acceptAction = this.create(AcceptEventAction.class);
        acceptAction.setIsUnmarshall(false);
        
        StringBuilder signalNames = new StringBuilder();
        for (Signal signal: signals) {
            if (signalNames.length() > 0) {
                signalNames.append(",");
            }
            signalNames.append(signal.getName());
            
            SignalEvent event = this.create(SignalEvent.class);
            event.setName("Event(" + signal.getName() + ")");
            event.setSignal(signal);
            this.modelElements.add(event);
            
            Trigger trigger = this.create(Trigger.class);
            trigger.setEvent(event);
            acceptAction.addTrigger(trigger);
        }
        
        acceptAction.setName("Accept(" + signalNames + ")");
        
        if (hasOutput) {
            acceptAction.addResult(createOutputPin(
                    acceptAction.getName() + ".result", null, 1, 1));
        }
        
        this.add(acceptAction);
        return acceptAction;
    }
    
    public AddStructuralFeatureValueAction addAddStructuralFeatureValueAction(
            Property property, boolean isReplaceAll) {
        AddStructuralFeatureValueAction writeAction = 
            this.create(AddStructuralFeatureValueAction.class);
        writeAction.setName("Write(" + property.getName() + ")");
        writeAction.setStructuralFeature(property);
        writeAction.setIsReplaceAll(isReplaceAll);
        this.add(writeAction);
        
        Classifier featuringClassifier = property.getFeaturingClassifier().get(0);
        writeAction.setObject(createInputPin(
                writeAction.getName() + ".object", featuringClassifier, 1, 1));
        writeAction.setValue(createInputPin(
                writeAction.getName() + ".value", property.getType(), 1, 1));
        writeAction.setResult(createOutputPin(
                writeAction.getName() + ".result", featuringClassifier, 1, 1));
        
        if (property.getIsOrdered() && !isReplaceAll) {
            writeAction.setInsertAt(this.createInputPin(
                    writeAction.getName() + ".insertAt", 
                    FumlMapping.getUnlimitedNaturalType(), 1, 1));
        }
        
        return writeAction;
    }
    
    public CallBehaviorAction addCallBehaviorAction(Behavior behavior) {
        CallBehaviorAction callAction = this.create(CallBehaviorAction.class);
        callAction.setName("Call(" + behavior.getName() + ")");
        callAction.setBehavior(behavior);
        addPinsFromParameters(callAction, behavior.getOwnedParameter());
        this.add(callAction);
        return callAction;
    }
    
    public CallOperationAction addCallOperationAction(Operation operation) {
        CallOperationAction callAction = this.create(CallOperationAction.class);
        callAction.setName("Call(" + operation.getName() + ")");
        callAction.setOperation(operation);
        callAction.setTarget(createInputPin(
                callAction.getName() + ".target", operation.getClass_(), 1, 1));
        addPinsFromParameters(callAction, operation.getOwnedParameter());
        this.add(callAction);
        return callAction;
    }
    
    public ClearAssociationAction addClearAssociationAction(Association association) {
        ClearAssociationAction clearAction = this.create(ClearAssociationAction.class);
        clearAction.setName("Clear(" + association.getName() + ")");
        clearAction.setAssociation(association);
        clearAction.setObject(createInputPin(
                clearAction.getName() + ".object", null, 1, 1));
        clearAction.getObject().setIsOrdered(false);
        this.add(clearAction);
        return clearAction;
    }
    
    public ClearStructuralFeatureAction addClearStructuralFeatureAction(
            Property property) {
        ClearStructuralFeatureAction clearAction = this.create(ClearStructuralFeatureAction.class);
        clearAction.setName("Clear(" + property.getName() + ")");
        clearAction.setStructuralFeature(property);
        this.add(clearAction);

        Classifier featuringClassifier = property.getFeaturingClassifier().get(0);
        clearAction.setObject(createInputPin(
                clearAction.getName() + ".object", featuringClassifier, 1, 1));
        clearAction.getObject().setIsOrdered(false);
        clearAction.setResult(createOutputPin(
                clearAction.getName() + ".result", featuringClassifier, 1, 1));
        return clearAction;
    }
    
    public CreateLinkAction addCreateLinkAction(Association association) {
        CreateLinkAction createAction = this.create(CreateLinkAction.class);
        createAction.setName("this.create(" + association.getName() + ")");
        for (Property end: association.getOwnedEnd()) {
            InputPin valuePin = createInputPin(
                    createAction.getName() + ".value(" + end.getName() + ")", 
                    end.getType(), 1, 1);
            valuePin.setIsOrdered(end.getIsOrdered());
            createAction.addInputValue(valuePin);
            LinkEndCreationData endData = this.create(LinkEndCreationData.class);
            endData.setEnd(end);
            endData.setValue(valuePin);
            endData.setIsReplaceAll(false);
            if (end.getIsOrdered()) {
                InputPin insertAtPin = createInputPin(
                        createAction.getName() + ".insertAt(" + end.getName() + ")", 
                        FumlMapping.getUnlimitedNaturalType(), 1, 1);
                createAction.addInputValue(insertAtPin);
                endData.setInsertAt(insertAtPin);
            }
            createAction.addEndData(endData);
        }
        /*
        // NOTE: Setting isReplaceAll=true on opposite ends maintains the
        // upper bound for an end with multiplicity upper bound of 1.
        for (LinkEndCreationData endData: createAction.endData) {
            if (endData.end.getUpper() == 1) {
                for (LinkEndCreationData otherEndData: createAction.endData) {
                    if (otherEndData != endData) {
                        otherEndData.setIsReplaceAll(true);
                    }
                }
            }
        }
        */
        this.add(createAction);
        return createAction;
    }
    
    public CreateObjectAction addCreateObjectAction(Class_ class_) {
        CreateObjectAction createAction = this.create(CreateObjectAction.class);
        createAction.setName("Create(" + class_.getName() + ")");
        createAction.setClassifier(class_);            
        createAction.setResult(createOutputPin(
                createAction.getName() + ".result", class_, 1, 1));
        this.add(createAction);
        return createAction;
    }
    
    public DestroyLinkAction addDestroyLinkAction(Association association) {
        DestroyLinkAction destroyAction = this.create(DestroyLinkAction.class);
        destroyAction.setName("Destroy(" + association.getName() + ")");
        for (Property end: association.getOwnedEnd()) {
            InputPin valuePin = createInputPin(
                    destroyAction.getName() + ".value(" + end.getName() + ")", 
                    end.getType(), 1, 1);
            valuePin.setIsOrdered(end.getIsOrdered());
            destroyAction.addInputValue(valuePin);
            LinkEndDestructionData endData = this.create(LinkEndDestructionData.class);
            endData.setEnd(end);
            endData.setValue(valuePin);
            endData.setIsDestroyDuplicates(!end.getIsOrdered());
            if (end.getIsOrdered()) {
                InputPin destroyAtPin = createInputPin(
                        destroyAction.getName() + ".destroyAt(" + end.getName() + ")", 
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
        DestroyObjectAction destroyAction = this.create(DestroyObjectAction.class);
        destroyAction.setName("DestroyObject");
        destroyAction.setIsDestroyLinks(true);
        destroyAction.setIsDestroyOwnedObjects(true);
        destroyAction.setTarget(createInputPin(
                destroyAction.getName() + ".target", class_, 1, 1));
        this.add(destroyAction);
        return destroyAction;
    }
    
    public LoopNode addLoopNode(
            String name, boolean isTestedFirst, InputPin... inputs) {
        LoopNode loopNode = this.create(LoopNode.class);
        loopNode.setName(name);
        loopNode.setIsTestedFirst(isTestedFirst);
        for (InputPin input: inputs) {
            String variableName = input.getName();
            input.setName(loopNode.getName() + ".loopVariableInput(" + variableName + ")");
            loopNode.addLoopVariableInput(input);
            loopNode.addLoopVariable(createOutputPin(
                    loopNode.getName() + ".loopVariable()(" + variableName + ")", 
                    input.getType(), 
                    input.getLower(), 
                    input.getUpper()));
            loopNode.addResult(createOutputPin(
                    loopNode.getName() + ".result(" + variableName + ")", 
                    input.getType(), 
                    input.getLower(), 
                    input.getUpper()));
        }
        this.add(loopNode);
        return loopNode;
    }
    
    public void addLoopTest(
            LoopNode loopNode, Collection<Element> test, OutputPin decider) {
        this.addToStructuredNode(loopNode, test);
        for (Element element: test) {
            if (element instanceof ExecutableNode) {
                loopNode.addTest((ExecutableNode)element);
            }
        }
        loopNode.setDecider(decider);
    }
    
    public void addLoopBodyPart(
            LoopNode loopNode, Collection<Element> bodyPart, OutputPin... bodyOutputs) {
        this.addToStructuredNode(loopNode, bodyPart);
        for (Element element: bodyPart) {
            if (element instanceof ExecutableNode) {
                loopNode.addBodyPart((ExecutableNode)element);
            }
        }
        for (OutputPin bodyOutput: bodyOutputs) {
            loopNode.addBodyOutput(bodyOutput);
        }
    }
    
    public ReadExtentAction addReadExtentAction(Class_ class_) {
        ReadExtentAction readExtentAction = this.create(ReadExtentAction.class);
        readExtentAction.setName(
                "ReadExtent(" + (class_ == null? "": class_.getName()) + ")");
        readExtentAction.setClassifier(class_);
        readExtentAction.setResult(createOutputPin(
                readExtentAction.getName() + ".result", class_, 0, -1));
        this.add(readExtentAction);
        return readExtentAction;
    }
    
    public ReadIsClassifiedObjectAction addReadIsClassifiedObjectAction(
            Classifier classifier, boolean isDirect) {
        ReadIsClassifiedObjectAction action = this.create(ReadIsClassifiedObjectAction.class);
        action.setName("ReadIsClassifiedObject(" + 
                (classifier == null? "": classifier.getName()) + ")");
        action.setClassifier(classifier);
        action.setIsDirect(isDirect);
        action.setObject(createInputPin(action.getName() + ".object", null, 1, 1));
        action.setResult(createOutputPin(
                action.getName() + ".result", FumlMapping.getBooleanType(), 1, 1));
        this.add(action);
        return action;
    }
    
    public ReadLinkAction addReadLinkAction(Property associationEnd) {
        ReadLinkAction readAction = this.create(ReadLinkAction.class);
        readAction.setName("ReadLink(" + associationEnd.getName() + ")");
        
        LinkEndData openEnd = this.create(LinkEndData.class);
        openEnd.setEnd(associationEnd);
        readAction.addEndData(openEnd);
        
        List<Property> otherEnds = 
            new ArrayList<Property>(associationEnd.getAssociation().getOwnedEnd());
        otherEnds.remove(associationEnd);
        addPinsFromProperties(readAction, otherEnds);
        
        addPin(readAction, this.create(OutputPin.class), associationEnd);
        this.add(readAction);
        return readAction;
    }
    
    public ReadSelfAction addReadSelfAction(Type type) {
        ReadSelfAction readSelfAction = this.create(ReadSelfAction.class);
        readSelfAction.setName("ReadSelf");
        readSelfAction.setResult(createOutputPin(
                readSelfAction.getName() + ".result", type, 1, 1));
        this.add(readSelfAction);
        return readSelfAction;
    }
    
    public ReadStructuralFeatureAction addReadStructuralFeatureAction(
            Property property) {
        ReadStructuralFeatureAction readAction = this.create(ReadStructuralFeatureAction.class);
        readAction.setName("Read(" + property.getName() + ")");
        readAction.setStructuralFeature(property);
        readAction.setObject(createInputPin(
                readAction.getName() + ".object", 
                getOppositeType(property), 1, 1));
        readAction.setResult(createOutputPin(
                readAction.getName() + ".result", property.getType(), 1, 1));
        this.add(readAction);
        return readAction;
    }
    
    private static Type getOppositeType(Property property) {
        // Type type = property.getFeaturingClassifier().get(0);
        Type type = (Type)property.getOwner();
        if (type instanceof Association) {
            List<Property> memberEnds = ((Association)type).getMemberEnd();
            int i = property.equals(memberEnds.get(0))? 1: 0;
            type = memberEnds.get(i).getType();
        }
        return type;
    }
    
    public ReclassifyObjectAction addReclassifyObjectAction(
            Classifier type,
            Collection<Classifier> oldClassifiers, 
            Collection<Classifier> newClassifiers,
            boolean isReplaceAll) {
        ReclassifyObjectAction reclassifyAction = this.create(ReclassifyObjectAction.class);
        
        StringBuilder oldClassifierList = new StringBuilder();
        for (Classifier oldClassifier: oldClassifiers) {
            reclassifyAction.addOldClassifier(oldClassifier);
            if (oldClassifierList.length() > 0) {
                oldClassifierList.append(",");
            }
            oldClassifierList.append(oldClassifier.getName());
        }
        
        if (oldClassifierList.length() == 0 && isReplaceAll) {
            oldClassifierList = new StringBuilder("*");
        }
        
        StringBuilder newClassifierList = new StringBuilder();
        for (Classifier newClassifier: newClassifiers) {
            reclassifyAction.addNewClassifier(newClassifier);
            if (newClassifierList.length() > 0) {
                newClassifierList.append(",");
            }
            newClassifierList.append(newClassifier.getName());
        }
        
        reclassifyAction.setName(
                "Reclassify(" + oldClassifierList + 
                " to " + newClassifierList + ")");
        reclassifyAction.setObject(createInputPin(
                reclassifyAction.getName() + ".object", type, 1, 1));
        reclassifyAction.setIsReplaceAll(isReplaceAll);
        
        this.add(reclassifyAction);
        return reclassifyAction;
    }
    
    public ReduceAction addReduceAction(
            Behavior behavior, Type type, boolean isOrdered) {
        ReduceAction reduceAction = this.create(ReduceAction.class);
        reduceAction.setName("Reduce(" + behavior.getName() + ")");
        reduceAction.setReducer(behavior);
        reduceAction.setIsOrdered(isOrdered);
        reduceAction.setCollection(this.createInputPin(
                reduceAction.getName() + ".collection", type, 0, -1));
        reduceAction.setResult(this.createOutputPin(
                reduceAction.getName() + ".result", type, 0, 1));
        this.add(reduceAction);
        return reduceAction;
    }
    
    public RemoveStructuralFeatureValueAction addRemoveStructuralFeatureValueAction(
            Property property, boolean isRemoveDuplicates) {
        RemoveStructuralFeatureValueAction removeAction = 
            this.create(RemoveStructuralFeatureValueAction.class);
        removeAction.setName("Remove(" + property.getName() + ")");
        removeAction.setStructuralFeature(property);
        removeAction.setIsRemoveDuplicates(isRemoveDuplicates);
        this.add(removeAction);
        
        Classifier featuringClassifier = property.getFeaturingClassifier().get(0);
        removeAction.setObject(createInputPin(
                removeAction.getName() + ".object", featuringClassifier, 1, 1));
        removeAction.setResult(createOutputPin(
                removeAction.getName() + ".result", featuringClassifier, 1, 1));
        
        if (!property.getIsOrdered() || 
                property.getIsUnique() || 
                isRemoveDuplicates) {
            removeAction.setValue(createInputPin(
                    removeAction.getName() + ".value", property.getType(), 1, 1));
        } else {
            removeAction.setRemoveAt(createInputPin(
                    removeAction.getName() + ".removeAt", property.getType(), 1, 1));
        }
        
        return removeAction;
    }
    
    public SendSignalAction addSendSignalAction(Signal signal) {
        SendSignalAction sendAction = this.create(SendSignalAction.class);
        sendAction.setName("SendSignal(" + signal.getName() + ")");
        sendAction.setSignal(signal);
        sendAction.setTarget(createInputPin(
                sendAction.getName() + ".target", null, 1, 1));
        // TODO: Use getAllAttributes(signal), once this is supportable by fUML.
        addPinsFromProperties(sendAction, signal.getAttribute());
        this.add(sendAction);
        return sendAction;
    }
    
    public StartObjectBehaviorAction addStartObjectBehaviorAction(Class_ class_) {
        StartObjectBehaviorAction startAction = 
            this.create(StartObjectBehaviorAction.class);
        startAction.setName("Start(" + (class_ == null? "any": class_.getName()) + ")");
        startAction.setObject(createInputPin(
                startAction.getName() + ".object", class_, 1, 1));
        this.add(startAction);
        return startAction;
    }
    
    public TestIdentityAction addTestIdentityAction(String condition) {
        TestIdentityAction testAction = this.create(TestIdentityAction.class);
        testAction.setName("Test(" + condition + ")");
        testAction.setFirst(createInputPin(testAction.getName() + ".first", null, 1, 1));
        testAction.setSecond(createInputPin(testAction.getName() + ".second", null, 1, 1));        
        testAction.setResult(createOutputPin(
                    testAction.getName() + ".result",
                    FumlMapping.getBooleanType(),
                    1, 1));
        this.add(testAction);
        return testAction;
    }
    
    public ValueSpecificationAction addValueSpecificationAction(
            ValueSpecification value, String literalString) {
        ValueSpecificationAction valueAction = this.create(ValueSpecificationAction.class);
        valueAction.setName("Value(" + literalString + ")");
        valueAction.setValue(value);
        valueAction.setResult(createOutputPin(
                valueAction.getName() + ".result", value.getType(), 1, 1));
        this.add(valueAction);        
        return valueAction;
    }
    
    public ValueSpecificationAction addNullValueSpecificationAction() {
        return this.addValueSpecificationAction(this.create(LiteralNull.class), "null");
    }
    
    public ValueSpecificationAction addBooleanValueSpecificationAction(
            boolean value) {
        LiteralBoolean literal = this.create(LiteralBoolean.class);
        literal.setValue(value);
        literal.setType(FumlMapping.getBooleanType());
        return this.addValueSpecificationAction(literal, Boolean.toString(value));
    }

    public ValueSpecificationAction addUnlimitedNaturalValueSpecificationAction(
            int value) {
        LiteralUnlimitedNatural literal = this.create(LiteralUnlimitedNatural.class);
        literal.setValue(value);
        literal.setType(FumlMapping.getUnlimitedNaturalType());
        return this.addValueSpecificationAction(literal, 
                value < 0? "*": Integer.toString(value));
    }

    public ValueSpecificationAction addNaturalValueSpecificationAction(
            int value) {
        LiteralInteger literal = this.create(LiteralInteger.class);
        literal.setValue(value);
        literal.setType(FumlMapping.getNaturalType());
        return this.addValueSpecificationAction(literal, Integer.toString(value));
    }

    public ValueSpecificationAction addIntegerValueSpecificationAction(
            int value) {
        LiteralInteger literal = this.create(LiteralInteger.class);
        literal.setValue(value);
        literal.setType(FumlMapping.getIntegerType());
        return this.addValueSpecificationAction(literal, Integer.toString(value));
    }

    public ValueSpecificationAction addStringValueSpecificationAction(
            String value) {
        LiteralString literal = this.create(LiteralString.class);
        literal.setValue(value);
        literal.setType(FumlMapping.getStringType());
        return this.addValueSpecificationAction(literal, "\"" + value + "\"");
    }
    
    public ValueSpecificationAction addDataValueSpecificationAction(
            InstanceSpecification instance) {
        InstanceValue value = this.create(InstanceValue.class);
        value.setType(instance.getClassifier().get(0));
        value.setInstance(instance);        
        return this.addValueSpecificationAction(value, instance.getName());
    }
    
    public ValueSpecificationAction addDataValueSpecificationAction(
            DataType dataType) {
        InstanceSpecification instance = this.create(InstanceSpecification.class);
        instance.setName(dataType.getName());
        instance.addClassifier(dataType);
        this.modelElements.add(instance);
        return this.addDataValueSpecificationAction(instance);
    }
    
    // Structured Activity Nodes
    
    public StructuredActivityNode addStructuredActivityNode( 
            String name,
            Collection<Element> nestedElements) {
        StructuredActivityNode node = this.create(StructuredActivityNode.class);
        node.setName(name);        
        this.add(node);
        if (nestedElements != null) {
            this.addToStructuredNode(node, nestedElements);
        }
        return node;
    }
    
    public ExpansionRegion addExpansionRegion(
            String name, 
            String mode,
            Collection<Element> nestedElements, 
            ActivityNode inputSource,
            ActivityNode inputTarget,
            ActivityNode resultSource) {
        ExpansionRegion region = this.create(ExpansionRegion.class);
        region.setName(name);
        region.setMode(mode);
        this.add(region);

        // Add elements to expansion region.
        this.addToExpansionRegion(region, nestedElements);

        // Add input expansion node.
        ExpansionNode inputNode = this.create(ExpansionNode.class);
        inputNode.setName(region.getName() + ".inputElement");
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
            ExpansionNode outputNode = this.create(ExpansionNode.class);
            outputNode.setName(region.getName() + ".outputElement");
            region.addOutputElement(outputNode);
            this.add(outputNode);
            
            region.addEdge(createObjectFlow(resultSource, outputNode));
        }
        
        return region;
    }
    
    public ExpansionNode addInputExpansionNode(
            String label, ExpansionRegion region) {
        ExpansionNode node = this.create(ExpansionNode.class);
        node.setName(region.getName() + ".inputElement(" + label + ")");
        region.addInputElement(node);
        this.add(node);
        return node;
    }
    
    public ExpansionNode addOutputExpansionNode(
            String label, ExpansionRegion region) {
        ExpansionNode node = this.create(ExpansionNode.class);
        node.setName(region.getName() + ".outputElement(" + label + ")");
        region.addOutputElement(node);
        this.add(node);
        return node;
    }
    
    public void addToStructuredNode(
            StructuredActivityNode node, 
            Collection<Element> nestedElements) {
        // NOTE: Add nested activity nodes first, before checking on activity
        // edge source and target containment.
        for (Element element: nestedElements) {
            if (element instanceof ActivityNode) {
                node.addNode((ActivityNode)element);
            } else if (!(element instanceof ActivityEdge)) {
                this.modelElements.add(element);
            }
        }
        
        for (Element element: nestedElements) {
            if (element instanceof ActivityEdge) {
                ActivityEdge edge = (ActivityEdge)element;
                this.addToStructuredNode(node, edge);
            }
        }
    }
    
    public void addToStructuredNode(
            StructuredActivityNode node,
            ActivityEdge edge) {
        if (isContainedIn(edge.getSource(), node) &&
                isContainedIn(edge.getTarget(), node)) {
            node.addEdge(edge);
        } else {
            this.add(edge);
        }
    }

    public void addToExpansionRegion(
            ExpansionRegion region,
            Collection<Element> nestedElements) {
        // Add nodes to expansion region.
        for (Element element : nestedElements) {
            if (element instanceof ActivityNode) {
                region.addNode((ActivityNode) element);
            } else if (!(element instanceof ActivityEdge)) {
                this.modelElements.add(element);
            }
        }
        
        // Add activity edges to expansion region. 
        for (Element element : nestedElements) {
            if (element instanceof ActivityEdge) {
                ActivityEdge edge = (ActivityEdge) element;
                ActivityNode source = edge.getSource();
                ActivityNode target = edge.getTarget();
                boolean sourceIsContained = isContainedIn(source, region);
                boolean targetIsContained = isContainedIn(target, region);
                if (sourceIsContained && targetIsContained ||
                        source instanceof ExpansionNode && 
                            region.equals(((ExpansionNode)source).getRegionAsInput()) ||
                        target instanceof ExpansionNode && 
                            region.equals(((ExpansionNode)target).getRegionAsOutput())) {
                    region.addEdge(edge);
                } else if (!sourceIsContained && targetIsContained){
                    source.removeOutgoing(edge);
                    target.removeIncoming(edge);
                    
                    if (edge instanceof ControlFlow) {
                        // If an incoming control flow crosses into the region,
                        // redirect it to target the region itself.
                        this.addControlFlow(source, region);
                        
                    } else {
                        // If an incoming object flow crosses into the region, 
                        // add an input pin at the boundary. 
                        
                        int lower = 0;
                        int upper = -1;
                        Type type = null;
                        if (target instanceof ObjectNode) {
                            type = ((ObjectNode)target).getType();
                            if (target instanceof Pin) {
                                Pin targetPin = (Pin)target;
                                lower = targetPin.getLower();
                                upper = targetPin.getUpper();
                            }
                        }

                        InputPin pin = createInputPin(
                                region.getName() + ".input(" + source.getName() + ")",
                                type, lower, upper);
                        region.addStructuredNodeInput(pin);
                        region.addEdge(createObjectFlow(pin, target));
                        this.addObjectFlow(source, pin);
                    }
                } else if (sourceIsContained && !targetIsContained) {
                    source.removeOutgoing(edge);
                    target.removeIncoming(edge);
                    
                    if (edge instanceof ControlFlow) {
                        // If an outgoing control flow crosses out of the region,
                        // redirect it to have the region as its source.
                        this.addControlFlow(region, target);
                        
                    } else {
                        // If an outgoing object flow crosses out of the region, 
                        // add an output expansion node at the boundary.
                        ExpansionNode outputNode = this.create(ExpansionNode.class);
                        outputNode.setName(region.getName() + 
                                ".outputElement(" + source.getName() + ")");
                        region.addOutputElement(outputNode);
                        region.addEdge(createObjectFlow(source, outputNode));
                        this.add(outputNode);
                        this.addObjectFlow(outputNode, target);
                    }
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
    
    public InputPin createInputPin(String name, Type type, int lower, int upper) {
        InputPin pin = this.create(InputPin.class);
        setPin(pin, name, type, lower, upper);
        return pin;
    }

    public OutputPin createOutputPin(String name, Type type, int lower, int upper) {
        OutputPin pin = this.create(OutputPin.class);
        setPin(pin, name, type, lower, upper);
        return pin;
    }
    
    public ObjectFlow createObjectFlow(ActivityNode source, ActivityNode target) {
        ObjectFlow flow = this.create(ObjectFlow.class);
        flow.setSource(source);
        flow.setTarget(target);
        return flow;
    }

    public ControlFlow createControlFlow(ActivityNode source, ActivityNode target) {
        ControlFlow flow = this.create(ControlFlow.class);
        flow.setSource(source);
        flow.setTarget(target);
        return flow;
    }
    
    public StructuredActivityNode createPassthruNode(
            String name, Type type, int lower, int upper) {
        StructuredActivityNode structuredNode = this.create(StructuredActivityNode.class);
        structuredNode.setName("Passthru(" + name + ")");
        
        InputPin inputPin = 
            createInputPin(structuredNode.getName() + ".input", type, lower, upper);
        OutputPin outputPin = 
            createOutputPin(structuredNode.getName() + ".output", type, lower, upper);
        structuredNode.addStructuredNodeInput(inputPin);
        structuredNode.addStructuredNodeOutput(outputPin);
        structuredNode.addEdge(createObjectFlow(inputPin, outputPin));
        
        return structuredNode;
    }
    
    public void addTo(
            StructuredActivityNode node, 
            Collection<Element> nestedElements,
            Collection<Element> outerElements) {
        ActivityGraph graph = new ActivityGraph(this.getElementFactory());
        graph.addToStructuredNode(node, nestedElements);
        outerElements.addAll(graph.getModelElements());
    }

    private void addPinsFromParameters(
            Action action, List<Parameter> parameters) {

        for (Parameter parameter : parameters) {
            String direction = parameter.getDirection();
            
            // NOTE: Both an input pin AND and output pin are added for an inout
            // parameter.
            if (direction.equals("in") || direction.equals("inout")) {
                addPin(action, this.create(InputPin.class), parameter);
            }
            if (direction.equals("out") || direction.equals("inout")
                    || direction.equals("return")) {
                addPin(action, this.create(OutputPin.class), parameter);
            }
        }
    }

    private static void addPin(Action action, Pin pin, Parameter parameter) {
        pin.setLower(parameter.getLower());
        pin.setUpper(parameter.getUpper());
        pin.setIsOrdered(parameter.getIsOrdered());
        pin.setIsUnique(parameter.getIsUnique());
        pin.setType(parameter.getType());

        if (pin instanceof InputPin) {
            if (action instanceof InvocationAction) {
                pin.setName(action.getName() + ".argument(" + parameter.getName() + ")");
                ((InvocationAction) action).addArgument((InputPin)pin);
            }
        } else if (action instanceof CallAction) {
            pin.setName(action.getName() + ".result(" + parameter.getName() + ")");
            ((CallAction) action).addResult((OutputPin)pin);
        }
    }
    
    private void addPinsFromProperties(
            Action action, List<Property> properties) {
        for (Property property : properties) {
            addPin(action, this.create(InputPin.class), property);
        }
    }

    private void addPin(Action action, Pin pin, Property property) {
        pin.setLower(property.getLower());
        pin.setUpper(property.getUpper());
        pin.setIsOrdered(property.getIsOrdered());
        pin.setIsUnique(property.getIsUnique());
        pin.setType(property.getType());

        if (pin instanceof InputPin) {
            InputPin inputPin = (InputPin)pin;
            if (action instanceof SendSignalAction) {
                inputPin.setName(action.getName() + ".argument(" + property.getName() + ")");
                ((InvocationAction) action).addArgument(inputPin);
            } else if (action instanceof ReadLinkAction) {
                inputPin.setName(action.getName() + ".inputValue(" + property.getName() + ")");
                LinkEndData endData = this.create(LinkEndData.class);
                endData.setEnd(property);
                endData.setValue(inputPin);
                ((ReadLinkAction) action).addInputValue(inputPin);
                ((ReadLinkAction) action).addEndData(endData);
            }
        } else if (action instanceof ReadLinkAction) {
            pin.setName(action.getName() + ".result");
            ((ReadLinkAction) action).setResult((OutputPin) pin);
        }
    }

    public static OutputPin getReturnPin(Action action) {
        List<Parameter> parameters =
            action instanceof CallOperationAction? 
                    ((CallOperationAction)action).getOperation().getOwnedParameter():
            action instanceof CallBehaviorAction?
                    ((CallBehaviorAction)action).getBehavior().getOwnedParameter():
            new ArrayList<Parameter>();
        int i = 0;
        for (Parameter parameter: parameters) {
            String direction = parameter.getDirection();
            if (direction.equals("return")) {
                return action.getOutput().get(i);
            } else if (direction.equals("out") || direction.equals("inout")) {
                i++;
            }
        }
        return null;
    }
    
    /**
     * Get all the attributes for the given classifier and any inherited from
     * its parents.
     * 
     * NOTE: This operation is based on the Classifier::allAttributes OCL 
     * operation defined in UML 2.5.
     */
    public static List<Property> getAllAttributes(Classifier classifier) {
        List<Property> allAttributes = 
                new ArrayList<Property>(classifier.getAttribute());
        Collection<NamedElement> members = classifier.getMember();
        for (Classifier parent: classifier.allParents()) {
            for (Property attribute: getAllAttributes(parent)) {
                if (members.contains(attribute)) {
                    allAttributes.add(attribute);
                }
            }
        }
        return allAttributes;
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
                ((LoopNode)container).getLoopVariable().contains(node)) {
            return true;
        } else {
            if (node instanceof Pin) {
                node = (ActivityNode)node.getOwner();
                
                // A pin that is owned by a structured activity node is
                // considered to be "contained in" that node.
                if (node == null) {
                    // NOTE: node can only be false if the pin is a loop
                    // loop variable, and then it could only be contained in
                    // container if container was the loop node for that loop
                    // variable, in which case this would have been caught by
                    // the test above.
                    return false;
                } else if (node.equals(container)) {
                    return true;
                }
            }
            ActivityNode inStructuredNode = node.getInStructuredNode();
            return inStructuredNode != null && (
                    inStructuredNode.equals(container) || 
                    isContainedIn(inStructuredNode, container));
        }
    }
    
    /**
     * Checks that an activity node is contained directly within a a given 
     * collection of element or indirectly within a structured activity node 
     * that is one of those elements.
     */
    public static boolean isContainedIn(Pin pin, Collection<Element> elements) {
        for (Element element: elements) {
            if (pin.getOwner().equals(element) || 
                    element instanceof StructuredActivityNode && 
                    isContainedIn(pin, (StructuredActivityNode)element)) {
                return true;
            }
        }
        return false;
    }

}
