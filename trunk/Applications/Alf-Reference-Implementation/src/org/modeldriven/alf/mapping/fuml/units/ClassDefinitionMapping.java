/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.units;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.ActivityGraph;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.expressions.AssignmentExpressionMapping;
import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.units.ClassDefinition;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.PropertyDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Actions.BasicActions.CallOperationAction;
import fUML.Syntax.Actions.IntermediateActions.ReadSelfAction;
import fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction;
import fUML.Syntax.Actions.IntermediateActions.ValueSpecificationAction;
import fUML.Syntax.Activities.CompleteStructuredActivities.StructuredActivityNode;
import fUML.Syntax.Activities.IntermediateActivities.Activity;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.NamedElement;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.Classes.Kernel.VisibilityKind;

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
     * «Create» stereotype applied. It has a corresponding method activity that
     * is a private owned behavior of the class with the default behavior.
     * Within this behavior, initializers for attributes of the class are mapped
     * as sequenced structured activity nodes containing the mappings of the
     * initializer expressions.
     * 
     * 6. If a class definition has no operation definitions that are
     * destructors, a public, concrete owned operation with the name “destroy”,
     * no parameters and the standard «Destroy» stereotype applied is added to
     * the class. It has a corresponding method activity that is a private owned
     * behavior of the class with the default behavior.
     */

    // For mapping of active class definitions, see ActiveClassDefinitionMapping.
    // For default constructor behavior mapping, see OperationDefinitionMapping.
    // Subunits are handled by NamespaceDefinitionMapping.

    @Override
    public Classifier mapClassifier() {
        ClassDefinition definition = this.getClassDefinition();
        Class_ class_ = new Class_();
        
        // Create initialization flag.
        this.initializationFlag = new Property();
        this.initializationFlag.setName(
                makeDistinguishableName(definition, definition.getName() + 
                        "$initializationFlag"));
        this.initializationFlag.setType(getBooleanType());
        this.initializationFlag.setLower(1);
        this.initializationFlag.setUpper(1);        
        class_.addOwnedAttribute(this.initializationFlag);

        // Create initialization operation.
        this.initializationOperation = new Operation();
        this.initializationOperation.setName(
                makeDistinguishableName(definition, definition.getName() + 
                        "$initialization"));
        this.initializationOperation.setVisibility(VisibilityKind.protected_);
        class_.addOwnedOperation(this.initializationOperation);
        
        return class_;
    }

    @Override
    public void mapTo(Classifier classifier) throws MappingError {
        super.mapTo(classifier);
        Class_ class_ = (Class_)classifier;
        
        // Add method for initialization operation.
        ActivityGraph graph = new ActivityGraph();
        ActivityGraph initializationGraph = new ActivityGraph();

        ReadSelfAction readSelfAction = 
                graph.addReadSelfAction(class_);
        ActivityNode selfFork = graph.addForkNode(
                "Fork(" + readSelfAction.result + ")");
        graph.addObjectFlow(readSelfAction.result, selfFork);

        NamespaceDefinition classDefinition = this.getClassDefinition();
        Property initializationFlag = this.getInitializationFlag();
        ActivityGraph subgraph = new ActivityGraph();
        ActivityNode previousNode = null;
                
        // Add initialization of superclass properties.
        for (ElementReference superclassReference: 
            this.getClassDefinition().getSpecializationReferent()) {
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
                initializationGraph.addObjectFlow(selfFork, callAction.target);
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
                            subgraph = new ActivityGraph();
                            subgraph.addAll(expressionMapping.getGraph());
                            AssignmentExpressionMapping.mapPropertyAssignment(
                                    property, subgraph, selfFork, 
                                    expressionMapping.getResultSource());
                            StructuredActivityNode node = 
                                    initializationGraph.addStructuredActivityNode(
                                            "Initialize(" + property.name + ")", 
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
        ValueSpecificationAction valueAction = 
                subgraph.addBooleanValueSpecificationAction(true);
        AssignmentExpressionMapping.mapPropertyAssignment(
                initializationFlag, subgraph, 
                selfFork, valueAction.result);
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
        graph.addObjectFlow(selfFork, readAction.object);
        graph.addObjectFlow(readAction.result, callAction.argument.get(0));
        graph.addControlDecisionNode(
                "Test(" + initializationFlag.name + ")", 
                initialNode, callAction.result.get(0), 
                initializationNode, null);

        // Add method to initialization operation.
        Activity initializationMethod = new Activity();
        initializationMethod.setName(this.initializationOperation.name);
        ActivityDefinitionMapping.addElements(
                initializationMethod, graph.getModelElements(), null, this);
        this.initializationOperation.addMethod(initializationMethod);
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

    public ClassDefinition getClassDefinition() {
        return (ClassDefinition) this.getSource();
    }

    @Override
    public String toString() {
        Class_ class_ = (Class_) this.getElement();
        return super.toString() + 
                (class_ == null ? "" : " isActive:" + class_.isActive);
    }

} // ClassDefinitionMapping
