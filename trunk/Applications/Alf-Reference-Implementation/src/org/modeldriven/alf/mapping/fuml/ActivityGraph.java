package org.modeldriven.alf.mapping.fuml;

import java.util.ArrayList;
import java.util.Collection;

import fUML.Syntax.Actions.BasicActions.*;
import fUML.Syntax.Actions.CompleteActions.*;
import fUML.Syntax.Actions.IntermediateActions.*;
import fUML.Syntax.Activities.CompleteStructuredActivities.*;
import fUML.Syntax.Activities.ExtraStructuredActivities.*;
import fUML.Syntax.Activities.IntermediateActivities.*;
import fUML.Syntax.Classes.Kernel.*;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.*;

public class ActivityGraph {
    
    private Collection<Element> modelElements = new ArrayList<Element>();
    
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
    
    public Collection<Element> getModelElements() {
        return new ArrayList<Element>(this.modelElements);
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
    
    public DecisionNode addDecisionNode(
            String label,
            ActivityNode inputSource, ActivityNode decisionSource,
            ActivityNode trueTarget, ActivityNode falseTarget) {
        DecisionNode decision = new DecisionNode();
        decision.setName("Decision(" + label + ")");
        decision.setDecisionInputFlow(createObjectFlow(decisionSource, decision));
        this.add(decision);

        this.addObjectFlow(inputSource, decision);
        this.add(decision.decisionInputFlow);

        if (trueTarget != null) {
            LiteralBoolean literalTrue = new LiteralBoolean();
            literalTrue.setName("Value(true)");
            literalTrue.setValue(true);
            
            this.addObjectFlow(decision, trueTarget).setGuard(literalTrue);    
        }
        
        if (falseTarget != null) {
            LiteralBoolean literalFalse = new LiteralBoolean();
            literalFalse.setName("Value(false)");
            literalFalse.setValue(false);
            
            this.addObjectFlow(decision, falseTarget).setGuard(literalFalse);    
        }
        return decision;
    }
    
    public ForkNode addForkNode(String name) {
        ForkNode fork = new ForkNode();
        fork.setName(name);
        this.add(fork);
        return fork;
    }
    
    // Actions
    
    public AddStructuralFeatureValueAction addAddStructuralFeatureValueAction(
            Property property) {
        AddStructuralFeatureValueAction writeAction = 
            new AddStructuralFeatureValueAction();
        writeAction.setName("Write(" + property.qualifiedName + ")");
        writeAction.setStructuralFeature(property);
        this.add(writeAction);
        
        Classifier featuringClassifier = property.featuringClassifier.get(0);
        writeAction.setObject(createInputPin(
                writeAction.name + ".object", featuringClassifier, 1, 1));
        writeAction.setValue(createInputPin(
                writeAction.name + ".value", property.typedElement.type, 1, 1));
        writeAction.setResult(createOutputPin(
                writeAction.name + ".result", featuringClassifier, 1, 1));
        
        return writeAction;
    }
    
    public CallBehaviorAction addCallBehaviorAction(Behavior behavior) {
        CallBehaviorAction callAction = new CallBehaviorAction();
        callAction.setName("Call(" + behavior.name + ")");
        callAction.setBehavior(behavior);
        
        for (Parameter parameter: behavior.ownedParameter) {
            if (parameter.direction == ParameterDirectionKind.in ||
                    parameter.direction == ParameterDirectionKind.inout) {
                callAction.addArgument(createInputPin(
                        callAction.name + ".argument(" + parameter.name + ")", 
                        parameter.type, 
                        parameter.multiplicityElement.lower, 
                        parameter.multiplicityElement.upper.naturalValue));
            } else if (parameter.direction == ParameterDirectionKind.inout ||
                    parameter.direction == ParameterDirectionKind.out ||
                    parameter.direction == ParameterDirectionKind.return_) {
                callAction.addResult(createOutputPin(
                        callAction.name + ".result(" + parameter.name + ")", 
                        parameter.type, 
                        parameter.multiplicityElement.lower, 
                        parameter.multiplicityElement.upper.naturalValue));
            }
        }
        
        this.add(callAction);
        return callAction;
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
    
    public ReadExtentAction addReadExtentAction(Class_ class_) {
        ReadExtentAction readExtentAction = new ReadExtentAction();
        if (class_ != null) {
            readExtentAction.setName("ReadExtent(" + class_.name + ")");
            readExtentAction.setClassifier(class_);
            readExtentAction.setResult(createOutputPin(
                    readExtentAction.name + ".result", class_, 0, -1));
        }
        this.add(readExtentAction);
        return readExtentAction;
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
        addTo(node, nestedElements, this.modelElements);
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

        // Add nodes to expansion region.
        for (Element element : nestedElements) {
            if (element instanceof ActivityNode) {
                region.addNode((ActivityNode) element);
            } else if (element instanceof ControlFlow) {
                region.addEdge((ActivityEdge) element);
            }
        }
        
        // Add edges to expansion region.
        for (Element element : nestedElements) {
            if (element instanceof ObjectFlow ||
                    element instanceof ControlFlow) {
                ActivityEdge edge = (ActivityEdge) element;
                if (edge instanceof ControlFlow ||
                        isContainedIn(edge.source, region)) {
                    region.addEdge(edge);
                } else {
                    edge.source.outgoing.remove(edge);
                    edge.target.incoming.remove(edge);
                    
                    InputPin pin = createInputPin(
                            region.name + ".input(" + edge.source.name + ")",
                            null, 0, -1);
                    region.input.add(pin);
                    region.addEdge(createObjectFlow(pin, edge.target));
                    this.addObjectFlow(edge.source, pin);
                }
            }
        }

        // Connect external input source to region input node.
        ExpansionNode inputNode = new ExpansionNode();
        inputNode.setName(region.name + ".inputElement");
        region.addInputElement(inputNode);
        this.add(inputNode);
        this.addObjectFlow(inputSource, inputNode);

        // Connect region input node to internal input target.
        region.addEdge(createObjectFlow(inputNode, inputTarget));

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
    
    // Static Helper Methods
    
    public static void setPin(Pin pin, String name, Type type, int lower, int upper) {
        pin.setName(name);
        pin.setType(type);
        pin.setLower(lower);
        pin.setUpper(upper);        
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
                    outerElements.add(edge);
                }
            }
        }
    }

    /**
     * Checks that an activity node is contained directly within a structured 
     * activity node or indirectly within another structured activity node 
     * nested within the given one.
     */
    public static boolean isContainedIn(
            ActivityNode node, StructuredActivityNode container) {
        if (node instanceof Pin) {
            node = (ActivityNode)node.owner;
        }
        ActivityNode inStructuredNode = node.inStructuredNode;
        return inStructuredNode != null && (
                inStructuredNode == container || 
                isContainedIn(inStructuredNode, container));
    }

}
