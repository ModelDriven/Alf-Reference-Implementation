/*******************************************************************************
 * Copyright 2011-2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
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
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;
import org.modeldriven.alf.syntax.units.UnitDefinition;

import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.OpaqueBehavior;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.PrimitiveType;

public abstract class Alf extends AlfBase {
    
    protected Level debugLevel = Level.OFF;
    
    @Override
    public void setDebugLevel(String level) {
        this.debugLevel = Level.toLevel(level, this.debugLevel);
    }

    private Locus locus = null;
    
    protected abstract Locus createLocus();
    protected void createSystemServices() { }
    
    protected abstract OpaqueBehaviorExecution getUnimplementedBehaviorExecution();
    protected abstract OpaqueBehaviorExecution getOpaqueBehaviorExecution(Object object);
    protected abstract String getPrototypeClassName(Member definition, String prototypeName);
        
    public Locus getLocus() {
        if (this.locus == null) {
            this.locus = this.createLocus();
        }
        return this.locus;
    }
    
    protected void addPrimitiveTypes(Collection<PrimitiveType> primitiveTypes) {
        ExecutionFactory executionFactory = this.getLocus().getFactory();
        for (PrimitiveType primitiveType: primitiveTypes) {
            for (PrimitiveType builtInType: executionFactory.getBuiltInTypes()) {
                if (builtInType.getName() != null && builtInType.getName().equals(primitiveType.getName())) {
                    this.println("Duplicate primitive type: " + primitiveType.getName());
                }
            }
            executionFactory.addBuiltInType(primitiveType);
        }
    }
    
    protected void addPrimitiveBehaviorPrototypes(
            Collection<OpaqueBehaviorExecution> primitiveBehaviorPrototypes) {
        ExecutionFactory executionFactory = this.getLocus().getFactory();
        for (OpaqueBehaviorExecution primitiveBehaviorPrototype: primitiveBehaviorPrototypes) {
            executionFactory.addPrimitiveBehaviorPrototype(primitiveBehaviorPrototype);
        }
    }
    
    private Collection<OpaqueBehaviorExecution> instantiatePrimitiveBehaviorPrototypes() {
        Collection<OpaqueBehaviorExecution> executions = 
                new ArrayList<OpaqueBehaviorExecution>();
        for (ActivityDefinitionMapping primitiveBehaviorMapping: 
            ActivityDefinitionMapping.getPrimitiveBehaviorMappings()) {            
            String prototypeName = 
                    primitiveBehaviorMapping.getPrimitiveBehaviorPrototypeName();
            OpaqueBehaviorExecution execution = prototypeName == null? null:
                getPrimitiveBehaviorPrototype(
                        primitiveBehaviorMapping.getActivityDefinition(), prototypeName);
            if (execution != null) {
                execution.addType((OpaqueBehavior)primitiveBehaviorMapping.getElement());
                executions.add(execution);
            }
        }
        return executions;
    }
        
    private OpaqueBehaviorExecution getPrimitiveBehaviorPrototype(
            Member definition, String prototypeName) {
        OpaqueBehaviorExecution execution = this.getUnimplementedBehaviorExecution();
        try {
            execution = this.getOpaqueBehaviorExecution(
                Class.forName
                    (this.getPrototypeClassName(definition, prototypeName)).
                        newInstance());
        } catch (Exception e) {
        }
        return execution;
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
    
    public UnitDefinition execute(UnitDefinition unit) {
        if (unit != null) {
            try {
                NamespaceDefinition definition = unit.getDefinition();
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
                            this.instantiatePrimitiveBehaviorPrototypes());
                    this.createSystemServices();

                    this.printVerbose("Executing...");
                    if (element instanceof Behavior) {
                        locus.getExecutor().execute((Behavior)element, null);
                        return unit;
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

                            return unit;
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
    
    @Override
    public UnitDefinition process(UnitDefinition unit) {
        return this.execute(super.process(unit));
    }
    
    protected void configure() {
        super.configure();
        PropertyConfigurator.configure("log4j.properties");
    }
    
    public Alf() {
        super();
    }
    
    public Alf(String[] args) {
        super(args);
    }
    
}
