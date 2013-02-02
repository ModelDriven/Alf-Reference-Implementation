/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.AssignmentExpressionMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.InternalElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.units.ClassDefinition;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.PropertyDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.*;

public class ClassDefinitionMapping extends ClassifierDefinitionMapping {
    
    private Property initializationFlag = null;
    private Operation initializationOperation = null;
    protected Collection<Element> otherElements = new ArrayList<Element>();

    /**
     * 1. A non-active class definition maps to a class with isActive=false.
     * 
     * Class Members
     * 
     * 2. A nested classifier definition maps to a classifier as specified for
     * the appropriate kind of classifier. If the nested classifier definition
     * is a stub declaration, then the stub declaration is mapped according to
     * the associated subunit definition. The resulting classifier is a nested
     * classifier of the class.
     * 
     * 3. A property definition maps to an owned attribute of the class.
     * 
     * 4. An operation definition maps to an owned operation of the class.
     * 
     * Default Constructors and Destructors
     * 
     * 5. If a class definition has no operation definitions that are
     * constructors, a public, concrete owned operation is added to the class
     * with the same name as the class and no parameters and the standard
     * <<Create>> stereotype applied. It has a corresponding method activity that
     * is a private owned behavior of the class with the default behavior.
     * Within this behavior, initializers for attributes of the class are mapped
     * as sequenced structured activity nodes containing the mappings of the
     * initializer expressions.
     * 
     * 6. If a class definition has no operation definitions that are
     * destructors, a public, concrete owned operation with the name <<destroy>>,
     * no parameters and the standard <<Destroy>> stereotype applied is added to
     * the class. It has a corresponding method activity that is a private owned
     * behavior of the class with the default behavior.
     */

    // For mapping of active class definitions, see ActiveClassDefinitionMapping.
    // For default constructor behavior mapping, see OperationDefinitionMapping.
    // Subunits are handled by NamespaceDefinitionMapping.

    @Override
    public Classifier mapClassifier() {
        ClassDefinition definition = this.getClassDefinition();
        Class_ class_ = this.create(Class_.class);
        class_.setName(definition.getName());
        
        // Create initialization flag.
        this.initializationFlag = this.create(Property.class);
        this.initializationFlag.setName(
                makeDistinguishableName(definition, definition.getName() + 
                        "$initializationFlag"));
        this.initializationFlag.setType(getBooleanType());
        this.initializationFlag.setLower(1);
        this.initializationFlag.setUpper(1);        
        class_.addOwnedAttribute(this.initializationFlag);

        // Create initialization operation.
        this.initializationOperation = this.create(Operation.class);
        this.initializationOperation.setName(
                makeDistinguishableName(definition, definition.getName() + 
                        "$initialization"));
        this.initializationOperation.setVisibility("protected");
        class_.addOwnedOperation(this.initializationOperation);
        
        return class_;
    }

    @Override
    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);
        Class_ class_ = (Class_)classifier;
        
        // Add method for initialization operation.
        ActivityGraph graph = this.createActivityGraph();
        ActivityGraph initializationGraph = this.createActivityGraph();

        ReadSelfAction readSelfAction = 
                graph.addReadSelfAction(class_);
        ActivityNode selfFork = graph.addForkNode(
                "Fork(" + readSelfAction.getResult().getName() + ")");
        graph.addObjectFlow(readSelfAction.getResult(), selfFork);

        NamespaceDefinition classDefinition = this.getClassDefinition();
        Property initializationFlag = this.getInitializationFlag();
        ActivityNode previousNode = null;
                
        // Add initialization of superclass properties.
        for (ElementReference superclassReference: 
            this.getClassDefinition().getSpecializationReferent()) {
            // TODO: Implement superclass initialization for non-Alf superclasses.
            if (superclassReference instanceof InternalElementReference) {
                FumlMapping mapping = this.fumlMap(superclassReference);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (!(mapping instanceof ClassDefinitionMapping)) {
                    this.throwError("Error mapping superclass reference for " + 
                            superclassReference.getImpl().getName() + ": " + 
                            mapping.getErrorMessage());
                } else {
                    CallOperationAction callAction = initializationGraph.addCallOperationAction(
                            ((ClassDefinitionMapping)mapping).getInitializationOperation());
                    initializationGraph.addObjectFlow(selfFork, callAction.getTarget());
                    if (previousNode != null) {
                        initializationGraph.addControlFlow(previousNode, callAction);
                    }
                    previousNode = callAction;
                }
            }
        }
        
        // Add initialization of each property that has an initializer.
        for (Member member: classDefinition.getOwnedMember()) {
            if (member instanceof PropertyDefinition) {
                PropertyDefinition propertyDefinition =
                        (PropertyDefinition)member;
                Expression initializer = 
                        propertyDefinition.getInitializer();
                if (initializer != null) {
                    Mapping mapping = this.fumlMap(propertyDefinition);
                    if (!(mapping instanceof PropertyDefinitionMapping)) {
                        this.throwError("Error mapping property " + 
                                propertyDefinition.getName() + ": " + 
                                mapping.getErrorMessage());
                    } else {
                        Property property = 
                                ((PropertyDefinitionMapping)mapping).
                                getProperty();
                        mapping = this.fumlMap(initializer);
                        if (!(mapping instanceof ExpressionMapping)) {
                            this.throwError("Error mapping initializer for " + 
                                    propertyDefinition.getName() + ": " + 
                                    mapping.getErrorMessage());
                        } else {
                            ExpressionMapping expressionMapping =
                                    (ExpressionMapping)mapping;
                            ActivityGraph subgraph = this.createActivityGraph();
                            subgraph.addAll(expressionMapping.getGraph());
                            AssignmentExpressionMapping.mapPropertyAssignment(
                                    property, subgraph, selfFork, 
                                    expressionMapping.getResultSource(), this);
                            StructuredActivityNode node = 
                                    initializationGraph.addStructuredActivityNode(
                                            "Initialize(" + property.getName() + ")", 
                                            subgraph.getModelElements());
                            if (previousNode != null) {
                                initializationGraph.addControlFlow(previousNode, node);
                            }
                            previousNode = node;
                        }
                    }
                }
            }
        }
        
        // Add action to set initialization to true.
        ActivityGraph subgraph = this.createActivityGraph();
        ValueSpecificationAction valueAction = 
                subgraph.addBooleanValueSpecificationAction(true);
        AssignmentExpressionMapping.mapPropertyAssignment(
                initializationFlag, subgraph, 
                selfFork, valueAction.getResult(), this);
        ActivityNode node = 
                initializationGraph.addStructuredActivityNode(
                        "Set(initializationFlag)", 
                        subgraph.getModelElements());
        if (previousNode != null) {
            initializationGraph.addControlFlow(previousNode, node);
        }

        StructuredActivityNode initializationNode = 
                graph.addStructuredActivityNode(
                        "Initialization", initializationGraph.getModelElements());

        // Use decision node to skip initialization if this object is already
        // initialized.
        // Add actions to read initialization flag.
        ActivityNode initialNode = graph.addInitialNode("InitialNode");
        ReadStructuralFeatureAction readAction = 
                graph.addReadStructuralFeatureAction(initializationFlag);
        CallBehaviorAction callAction = graph.addCallBehaviorAction(
                getBehavior(RootNamespace.getSequenceFunctionIsEmpty()));
        graph.addObjectFlow(selfFork, readAction.getObject());
        graph.addObjectFlow(readAction.getResult(), callAction.getArgument().get(0));
        graph.addControlDecisionNode(
                "Test(" + initializationFlag.getName() + ")", 
                initialNode, callAction.getResult().get(0), 
                initializationNode, null);

        // Add method to initialization operation.
        Activity initializationMethod = this.create(Activity.class);
        initializationMethod.setName(this.initializationOperation.getName());
        this.otherElements.addAll(ActivityDefinitionMapping.addElements(
                initializationMethod, graph.getModelElements(), null, this));
        this.initializationOperation.addMethod(initializationMethod);
        class_.addOwnedBehavior(initializationMethod);
    }

    @Override
    public void addMemberTo(Element element, NamedElement namespace) throws MappingError {
        Class_ class_ = (Class_) namespace;

        if (element instanceof Property) {
            class_.addOwnedAttribute((Property) element);
        } else if (element instanceof Operation) {
            class_.addOwnedOperation((Operation) element);
        } else if (element instanceof Classifier) {
            class_.addNestedClassifier((Classifier) element);
        } else if (element instanceof InstanceSpecification || element instanceof Event) {
            // NOTE: An instance specification can result from a data value
            // specification action within an activity. The instance value for
            // such an action uses but does not own an instance specification.
            // An event can result from an accept event action within the
            // classifier behavior of a nested active class. The triggers for
            // such an action reference but do not own events.
            this.otherElements.add(element);
        } else {
            this.throwError("Member not legal for a class: " + element);
        }
    }
    
    public Property getInitializationFlag() throws MappingError {
        this.getClassifier();
        return this.initializationFlag;
    }
    
    public Operation getInitializationOperation() throws MappingError {
        this.getClassifier();
        return this.initializationOperation;
    }

    @Override
    public List<Element> getModelElements() throws MappingError {
        List<Element> elements = super.getModelElements();
        elements.addAll(this.otherElements);
        return elements;
    }

    public ClassDefinition getClassDefinition() {
        return (ClassDefinition) this.getSource();
    }

    @Override
    public String toString() {
        Class_ class_ = (Class_) this.getElement();
        return super.toString() + 
                (class_ == null ? "" : " isActive:" + class_.getIsActive());
    }

} // ClassDefinitionMapping
