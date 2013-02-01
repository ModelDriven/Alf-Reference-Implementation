/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution;

import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.ActivityDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.DataTypeDefinitionMapping;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ClassDefinition;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Behavior;

public abstract class Alf extends AlfBase {
    
    public NamespaceDefinition execute(NamespaceDefinition definition) {
        if (definition != null) {
            try {
                Mapping elementMapping = definition.getImpl().getMapping();
                Element element = ((FumlMapping)elementMapping).getElement();

                if (element instanceof Behavior && 
                        ((Behavior)element).getOwnedParameter().isEmpty() ||
                        element instanceof Class_ && 
                        ((Class_)element).getIsActive() && 
                        !((Class_)element).getIsAbstract() && 
                        ((Class_)element).getClassifierBehavior() != null) {

                    // Set up execution environment
                    Locus locus = this.getLocus();
                    this.addPrimitiveTypes(
                            DataTypeDefinitionMapping.getPrimitiveTypes());
                    this.addPrimitiveBehaviorPrototypes(
                            ActivityDefinitionMapping.getPrimitiveBehaviorPrototypes());
                    this.createSystemServices();

                    this.printVerbose("Executing...");
                    if (element instanceof Behavior) {
                        locus.getExecutor().execute((Behavior)element, null);
                        return definition;
                    } else {
                        ClassDefinition classDefinition = 
                                (ClassDefinition)definition;
                        OperationDefinition constructorDefinition = 
                                classDefinition.getImpl().getDefaultConstructor();
                        if (constructorDefinition == null) {
                            this.println("Cannot instantiate: " + 
                                    classDefinition.getName());
                        } else {
                            // Instantiate active class.
                            Class_ class_ = (Class_)element;
                            Object_ object = locus.instantiate(class_);

                            // Initialize the object.
                            ClassDefinitionMapping classMapping =
                                    (ClassDefinitionMapping)elementMapping;
                            Operation initializer = 
                                    classMapping.getInitializationOperation();
                            locus.getExecutor().execute(
                                    ((Behavior)initializer.getMethod().get(0)), 
                                    object);

                            // Execute the classifier behavior.
                            object.startBehavior(class_);

                            return definition;
                        }
                    }
                } else if (element instanceof Behavior) {
                    this.println("Cannot execute a behavior with parameters.");
                } else if (element instanceof Class_) {
                    Class_ class_ = (Class_)element;
                    if (!class_.getIsActive()) {
                        this.println("Cannot execute a class that is not active.");
                    } else if (class_.getIsAbstract()) {
                        this.println("Cannot execute an abstract class.");
                    } else {
                        this.println("Cannot execute a class without a classifier behavior.");
                    }
                } else {
                    this.println("Unit not executable.");
                }
            } catch (MappingError e) {
                this.println("Mapping failed.");
                this.println(e.getMapping().toString());                  
                this.println(" error: " + e.getMessage());
            }
        }
        return null;
    }
    
    public static Classifier getClassifier(QualifiedName name) {
        Classifier classifier = null;
        ElementReference referent = 
            name.getImpl().getClassifierReferent();
        FumlMapping mapping = FumlMapping.getMapping(referent);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
        }
        if (mapping instanceof ClassifierDefinitionMapping) {
            try {
                classifier = ((ClassifierDefinitionMapping)mapping).getClassifier();
            } catch (MappingError e) {
                System.out.println("Cannot map " + name.getPathName());
                System.out.println(" error: " + e.getMessage());
            }
        }
        return classifier;
    }
    
    @Override
    public NamespaceDefinition process(UnitDefinition unit) {
        return this.execute(super.process(unit));
    }
    
    public Alf() {
        super();
    }
    
    public Alf(String[] args) {
        super(args);
    }
    
}
