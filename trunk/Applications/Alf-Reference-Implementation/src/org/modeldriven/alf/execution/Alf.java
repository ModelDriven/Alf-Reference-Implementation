/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.execution;

import org.apache.log4j.PropertyConfigurator;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.OperationDefinitionMapping;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ClassDefinition;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.fuml.library.channel.StandardInputChannelObject;
import org.modeldriven.fuml.library.channel.StandardOutputChannelObject;
import org.modeldriven.fuml.library.common.Status;
import org.modeldriven.fuml.library.libraryclass.ImplementationObject;

import fUML.Semantics.Classes.Kernel.BooleanValue;
import fUML.Semantics.Classes.Kernel.Object_;
import fUML.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import fUML.Semantics.Classes.Kernel.ValueList;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import fUML.Semantics.Loci.LociL1.Executor;
import fUML.Semantics.Loci.LociL1.FirstChoiceStrategy;
import fUML.Semantics.Loci.LociL1.Locus;
import fUML.Semantics.Loci.LociL3.ExecutionFactoryL3;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.Classes.Kernel.Property;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public class Alf {
    
    private static Locus locus = null;
    
    private static void createLocus() {
        locus = new Locus();
        locus.setExecutor(new Executor());
        locus.setFactory(new ExecutionFactoryL3());
        locus.factory.setStrategy(new RedefinitionBasedDispatchStrategy());
        locus.factory.setStrategy(new FIFOGetNextEventStrategy());
        locus.factory.setStrategy(new FirstChoiceStrategy());
    }
    
    private static void createSystemServices() {
        QualifiedName standardOutputChannel = 
            RootNamespace.getBasicInputOutput().getImpl().copy().
                addName("StandardOutputChannel");
        createSystemService
            (standardOutputChannel, new StandardOutputChannelObject());
        
        QualifiedName standardInputChannel = 
            RootNamespace.getBasicInputOutput().getImpl().copy().
                addName("StandardInputChannel");
        createSystemService
            (standardInputChannel, new StandardInputChannelObject());
        
        QualifiedName status = 
            RootNamespace.getBasicInputOutput().getImpl().copy().
                addName("Status");
        Classifier statusType = getClassifier(status);
        if (statusType instanceof DataType) {
            Status.setStatusType((DataType)statusType);
        } else {
            System.out.println("Cannot find Status datatype.");
        }
    }
    
    private static void createSystemService (
            QualifiedName name,
            ImplementationObject object) {
        Classifier type = getClassifier(name);
        if (type instanceof Class_) {
            object.types.addValue((Class_)type);
            locus.add(object);
            System.out.println("Instantiated " + name.getPathName() + 
                    " as " + type.name + "(" + type + ")");
        }
    }
    
    private static Classifier getClassifier(QualifiedName name) {
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

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");
        
        String fileName = null;
        
        if (args.length > 0) {
            fileName = args[0];
        }
        
        createLocus();
        FumlMapping.setExecutionFactory(locus.factory);       
        FumlMapping mapping = FumlMapping.parseAndMap(fileName);
        
        if (mapping != null) {
            try {
                mapping.getModelElements();
                System.out.println("Mapped successfully.");
                SyntaxElement parsedElement = FumlMapping.getParsedElement();
                // System.out.println("[Alf] parsedElement=" + parsedElement);
                NamespaceDefinition definition = 
                        ((UnitDefinition)parsedElement).getDefinition();
                Mapping elementMapping = definition.getImpl().getMapping();
                // System.out.println("[Alf] elementMapping=" + elementMapping);
                Element element = ((FumlMapping)elementMapping).getElement();
                if (element instanceof Behavior && 
                        ((Behavior)element).ownedParameter.isEmpty() ||
                    element instanceof Class_ && 
                        ((Class_)element).isActive && 
                        !((Class_)element).isAbstract && 
                        ((Class_)element).classifierBehavior != null) {
                    createSystemServices();
                    System.out.println("Executing...");
                    if (element instanceof Behavior) {
                        locus.executor.execute
                            ((Behavior)element, null, new ParameterValueList());
                    } else {
                        ClassDefinition classDefinition = 
                                (ClassDefinition)definition;
                        OperationDefinition constructorDefinition = 
                                classDefinition.getImpl().getDefaultConstructor();
                        if (constructorDefinition == null) {
                            System.out.println("Cannot instantiate: " + 
                                    classDefinition.getName());
                        } else {
                            // Instantiate active class.
                            Class_ class_ = (Class_)element;
                            Object_ object = locus.instantiate(class_);
                            
                            // Initialize object using the default constructor.
                            ClassDefinitionMapping classMapping =
                                    (ClassDefinitionMapping)elementMapping;
                            Property property = classMapping.getInitializationFlag();
                            BooleanValue booleanValue = new BooleanValue();
                            booleanValue.value = false;
                            ValueList values = new ValueList();
                            values.add(booleanValue);
                            object.setFeatureValue(property, values, 0);
                            Operation constructor = 
                                    ((OperationDefinitionMapping)constructorDefinition.
                                            getImpl().getMapping()).getOperation();
                            locus.executor.execute(
                                    constructor.method.get(0), object, 
                                    new ParameterValueList());
                            
                            // Execute the classifier behavior.
                            object.startBehavior(class_, new ParameterValueList());
                        }
                    }
                } else {
                    System.out.println("Cannot execute: " + element);
                }
            } catch (MappingError e) {
                System.out.println("Mapping failed.");
                System.out.println(e.getMapping());                    
                System.out.println(" error: " + e.getMessage());
                mapping.print();
            }
        }
    }

}
