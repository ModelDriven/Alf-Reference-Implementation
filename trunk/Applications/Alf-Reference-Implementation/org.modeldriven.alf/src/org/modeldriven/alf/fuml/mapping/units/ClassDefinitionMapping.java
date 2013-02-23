/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.units;

import org.modeldriven.alf.fuml.mapping.ActivityGraph;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.expressions.AssignmentExpressionMapping;
import org.modeldriven.alf.fuml.mapping.expressions.ExpressionMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
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
        
        String name = this.getDefinitionBaseName();
        
        // Create initialization flag.
        this.initializationFlag = this.create(Property.class);
        this.initializationFlag.setName(
                makeDistinguishableName(definition, name + 
                        "$initializationFlag"));
        this.initializationFlag.setType(getBooleanType());
        this.initializationFlag.setLower(1);
        this.initializationFlag.setUpper(1);
        this.initializationFlag.setVisibility("protected");
        class_.addOwnedAttribute(this.initializationFlag);

        // Create initialization operation.
        this.initializationOperation = this.create(Operation.class);
        this.initializationOperation.setName(
                makeDistinguishableName(definition, name + 
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
            Operation initializationOperation = null;
            Class_ superClass = (Class_)superclassReference.getImpl().getUml();
            if (superClass != null) {
                initializationOperation = getInitializationOperation(superClass);
            } else {
                FumlMapping mapping = this.fumlMap(superclassReference);
                if (mapping instanceof ElementReferenceMapping) {
                    mapping = ((ElementReferenceMapping)mapping).getMapping();
                }
                if (!(mapping instanceof ClassDefinitionMapping)) {
                    this.throwError("Error mapping superclass reference for " + 
                            superclassReference.getImpl().getName() + ": " + 
                            mapping.getErrorMessage());
                } else {
                    initializationOperation = ((ClassDefinitionMapping)mapping).
                            getInitializationOperation();
                }
            }
            if (initializationOperation != null) {
                CallOperationAction callAction = 
                        initializationGraph.addCallOperationAction(
                                initializationOperation);
                initializationGraph.addObjectFlow(selfFork, callAction.getTarget());
                if (previousNode != null) {
                    initializationGraph.addControlFlow(previousNode, callAction);
                }
                previousNode = callAction;
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
        ReadStructuralFeatureAction readAction = 
                graph.addReadStructuralFeatureAction(initializationFlag);
        graph.addObjectFlow(selfFork, readAction.getObject());
        
        CallBehaviorAction sizeAction = graph.addCallBehaviorAction(
                getBehavior(RootNamespace.getListFunctionSize()));
        graph.addObjectFlow(readAction.getResult(), sizeAction.getArgument().get(0));
        
        valueAction = 
                graph.addIntegerValueSpecificationAction(0);
        
        TestIdentityAction testAction = graph.addTestIdentityAction("Size==0");
        graph.addObjectFlow(sizeAction.getResult().get(0), testAction.getFirst());
        graph.addObjectFlow(valueAction.getResult(), testAction.getSecond());
        
        ActivityNode initialNode = graph.addInitialNode("InitialNode");
        graph.addControlDecisionNode(
                "Test(" + initializationFlag.getName() + ")", 
                initialNode, testAction.getResult(), 
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
        } else if (element instanceof PackageableElement) {
            // Save element to be packaged at a higher nesting level.
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

    private static Operation getInitializationOperation(Class_ class_) {
        Operation operation = null;
        String initializerName = class_.getName() + "$initialization$";
        int n = initializerName.length();
        
        for (Operation ownedOperation: class_.getOwnedOperation()) {
            String operationName = ownedOperation.getName();
            if (operationName != null && 
                    operationName.length() > n &&
                    operationName.substring(0, n).equals(initializerName) &&
                    operationName.substring(n).matches("[0-9]+")) {
                operation = ownedOperation;
            }
        }
        
        return operation;
    }
    
    public ClassDefinition getClassDefinition() {
        return (ClassDefinition) this.getSource();
    }

    /**
     * If the classifier definition name has the form of a template
     * instantiation, then return the name of the base template. This results
     * in the names of the initialization flag and operation being the same
     * for a template instantiation as for its template.
     */
    public String getDefinitionBaseName() {
        String name = this.getClassifierDefinition().getName();
        if (name.length() > 2 && name.substring(0,2).equals("$$")) {
            int i = name.indexOf("__");
            if (i > -1) {
                name = name.substring(2, i);
            }
        }
        return name;
    }

    @Override
    public String toString() {
        Class_ class_ = (Class_) this.getElement();
        return super.toString() + 
                (class_ == null ? "" : " isActive:" + class_.getIsActive());
    }

} // ClassDefinitionMapping
