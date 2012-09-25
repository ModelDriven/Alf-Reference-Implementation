/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.execution.fumlri;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ClassDefinition;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.fuml.library.channel.StandardInputChannelObject;
import org.modeldriven.fuml.library.channel.StandardOutputChannelObject;
import org.modeldriven.fuml.library.common.Status;
import org.modeldriven.fuml.library.libraryclass.ImplementationObject;

import fUML.Semantics.Classes.Kernel.Object_;
import fUML.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import fUML.Semantics.Loci.LociL1.Executor;
import fUML.Semantics.Loci.LociL1.FirstChoiceStrategy;
import fUML.Semantics.Loci.LociL1.Locus;
import fUML.Semantics.Loci.LociL3.ExecutionFactoryL3;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Behavior;
import org.modeldriven.alf.uml.fumlri.ElementFactory;

public class Alf extends org.modeldriven.alf.execution.Alf {
    
    private static Locus locus = null;
    private static ElementFactory elementFactory = new ElementFactory();
    
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
            Status.setStatusType(((org.modeldriven.alf.uml.fumlri.DataType)statusType).getBase());
        } else {
            System.out.println("Cannot find Status datatype.");
        }
    }
    
    private static void createSystemService (
            QualifiedName name,
            ImplementationObject object) {
        Classifier type = getClassifier(name);
        if (type instanceof Class_) {
            fUML.Syntax.Classes.Kernel.Class_ class_ = 
                    ((org.modeldriven.alf.uml.fumlri.Class_)type).getBase();
            object.types.addValue(class_);
            locus.add(object);
            printVerbose("Instantiated " + name.getPathName() + 
                    " as " + type.getName() + "(" + class_ + ")");
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
    
    public void execute(NamespaceDefinition definition) {
        createLocus();
        FumlMapping.setExecutionFactory(locus.factory);
        FumlMapping.setElementFactory(elementFactory);
        FumlMapping mapping = FumlMapping.getMapping(RootNamespace.getRootScope());
        try {
            mapping.getModelElements();
            Mapping elementMapping = definition.getImpl().getMapping();
            printVerbose("Mapped successfully.");
            Element element = ((FumlMapping)elementMapping).getElement();
            if (element instanceof Behavior && 
                    ((Behavior)element).getOwnedParameter().isEmpty() ||
                    element instanceof Class_ && 
                    ((Class_)element).getIsActive() && 
                    !((Class_)element).getIsAbstract() && 
                    ((Class_)element).getClassifierBehavior() != null) {
                createSystemServices();
                printVerbose("Executing...");
                if (element instanceof Behavior) {
                    locus.executor.execute(
                            ((org.modeldriven.alf.uml.fumlri.Behavior)element).getBase(), 
                            null, new ParameterValueList());
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
                        Object_ object = locus.instantiate(((org.modeldriven.alf.uml.fumlri.Class_)class_).getBase());

                        // Initialize the object.
                        ClassDefinitionMapping classMapping =
                                (ClassDefinitionMapping)elementMapping;
                        Operation initializer = 
                                classMapping.getInitializationOperation();
                        locus.executor.execute(
                                ((org.modeldriven.alf.uml.fumlri.Behavior)initializer.getMethod().get(0)).getBase(), 
                                object, 
                                new ParameterValueList());

                        // Execute the classifier behavior.
                        object.startBehavior(((org.modeldriven.alf.uml.fumlri.Class_)class_).getBase(), new ParameterValueList());
                    }
                }
            } else if (element instanceof Behavior) {
                System.out.println("Cannot execute a behavior with parameters.");
            } else if (element instanceof Class_) {
                Class_ class_ = (Class_)element;
                if (!class_.getIsActive()) {
                    System.out.println("Cannot execute a class that is not active.");
                } else if (class_.getIsAbstract()) {
                    System.out.println("Cannot execute an abstract class.");
                } else {
                    System.out.println("Cannot execute a class without a classifier behavior.");
                }
            }
        } catch (MappingError e) {
            System.out.println("Mapping failed.");
            System.out.println(e.getMapping());                    
            System.out.println(" error: " + e.getMessage());
        }
    }
    
    public Alf(String[] args){
        super(args);
    }
    
    public static void main(String[] args) {
        new Alf(args);
    }
}
