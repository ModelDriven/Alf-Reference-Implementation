/*******************************************************************************
 * Copyright 2013-2017 Model Driven Solutions, Inc.
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.fuml.execution;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.modeldriven.alf.fuml.mapping.FumlMapping;
import org.modeldriven.alf.fuml.mapping.common.ElementReferenceMapping;
import org.modeldriven.alf.fuml.mapping.units.ActivityDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.DataTypeDefinitionMapping;
import org.modeldriven.alf.fuml.mapping.units.OperationDefinitionMapping;
import org.modeldriven.alf.fuml.units.RootNamespaceImpl;
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
            setUpExecutionEnvironment();
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
    
    public void setUpExecutionEnvironment() {
        this.addPrimitiveTypes(
                DataTypeDefinitionMapping.getPrimitiveTypes());
        this.addPrimitiveBehaviorPrototypes(
                this.instantiatePrimitiveBehaviorPrototypes());
        this.createSystemServices();
    }
    
    public UnitDefinition execute(UnitDefinition unit) {
        if (unit != null) {
            try {
                NamespaceDefinition definition = unit.getDefinition();
                Mapping elementMapping = definition.getImpl().getMapping();
                if (elementMapping == null) {
                    this.println(definition.getName() + " is unmapped.");
                } else {
                    Element element = ((FumlMapping)elementMapping).getElement();

                    if (element instanceof Behavior) {
                        Behavior behavior = (Behavior)element;
                        if (!behavior.getOwnedParameter().isEmpty()) {
                            this.println("Cannot execute a behavior with parameters.");                        
                        } else {
                            this.printVerbose("Executing...");
                            this.getLocus().getExecutor().execute(behavior, null);
                            return unit;
                        }
                    } else if (element instanceof Class_) { 
                        Class_ class_ = (Class_)element;
                        if (class_.getIsAbstract()) {
                            this.println("Cannot instantiate an abstract class.");
                        } else {
                            ClassDefinition classDefinition = 
                                    (ClassDefinition)definition;
                            OperationDefinition constructorDefinition = 
                                    classDefinition.getImpl().getConstructor();
                            if (constructorDefinition == null) {
                                this.println("Class does not have a default constructor.");
                            } else {
                                Locus locus = this.getLocus();

                                // Instantiate the class.
                                this.printVerbose("Instantiating...");
                                Object_ object = locus.instantiate(class_);

                                // Execute the default constructor.
                                Operation constructor = 
                                        ((OperationDefinitionMapping)constructorDefinition.getImpl().getMapping()).
                                        getOperation();
                                locus.getExecutor().execute(
                                        ((Behavior)constructor.getMethod().get(0)), 
                                        object);

                                if (class_.getIsActive() && class_.getClassifierBehavior() !=null ) {
                                    // Execute the classifier behavior.
                                    this.printVerbose("Executing...");
                                    object.startBehavior(class_);
                                }

                                return unit;
                            }
                        }
                    } else {
                        this.println("Unit is not executable.");
                    }
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
    
    @Override
    protected boolean matchesWithArg(String option) {
        return super.matchesWithArg(option) || "d".equals(option);
    }
    
    @Override
    protected void parseOptionWithArg(String option, String arg) {
        if (option.equals("d")) {
            this.setDebugLevel(arg);
        } else {
            super.parseOptionWithArg(option, arg);
        }
    }
    
    @Override
    protected void printUsage() {
        this.println("Usage is");
        this.println("  alf [options] unit");
        this.println("where unit is the qualified name of an Alf unit and");
        this.println("allowable options are:");
        this.println("  -d OFF|FATAL|ERROR|WARN|INFO|DEBUG|ALL");
        this.println("            Set debug logging level (default is as configured)");
        this.println("  -f        Treat unit as a file name rather than a qualifed name");
        this.println("  -l path   Set library directory path (default is \"" + 
                                    RootNamespaceImpl.DEFAULT_LIBRARY_DIRECTORY + "\")");
        this.println("  -m path   Set model directory path (default is \"" + 
                                    RootNamespaceImpl.DEFAULT_MODEL_DIRECTORY + "\")");
        this.println("  -p        Parse and constraint check only");
        this.println("  -P        Parse, constraint check and print abstract syntax tree");
        this.println("  -v        Set verbose mode (if used alone without unit, prints version info)");
    }
    
}
