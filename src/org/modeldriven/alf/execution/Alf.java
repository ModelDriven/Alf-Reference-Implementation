/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.execution;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.common.ElementReferenceMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassDefinitionMapping;
import org.modeldriven.alf.mapping.fuml.units.ClassifierDefinitionMapping;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.ClassDefinition;
import org.modeldriven.alf.syntax.units.MissingUnit;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
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
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Classifier;
import fUML.Syntax.Classes.Kernel.DataType;
import fUML.Syntax.Classes.Kernel.Element;
import fUML.Syntax.Classes.Kernel.Operation;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;

public class Alf {
    
    private static boolean isVerbose = false;
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
            printVerbose("Instantiated " + name.getPathName() + 
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
    
    public static void setModelDirectory(String modelDirectory) {
        RootNamespace.setModelDirectory(modelDirectory);
    }
    
    public static void setLibraryDirectory(String libraryDirectory) {
        RootNamespace.setLibraryDirectory(libraryDirectory);
    }
    
    public static void setDebugLevel(Level level) {
        Logger logger = Logger.getLogger(fUML.Debug.class);
        logger.setLevel(level);
    }
    
    public static void setIsVerbose(boolean verbose) {
        isVerbose = verbose;
        RootNamespace.setIsVerbose(isVerbose);
    }
    
    private static void printVerbose(String message) {
        if (isVerbose) {
            System.out.println(message);
        }
    }
    
    public static String parseArgs(String[] args) {
        Logger logger = Logger.getLogger(fUML.Debug.class);
        Level level = logger.getLevel();

        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            if (arg.charAt(0) != '-') {
                break;
            }
            String option = arg.substring(1);
            i++;
            if (i < args.length) {
                if (option.equals("v")) {
                    setIsVerbose(true);
                } else if (option.matches("[mld]")) {
                    arg = args[i];
                    if (arg.charAt(0) == '-') {
                        break;
                    }
                    i++;
                    if (option.equals("m")) {
                        setModelDirectory(arg);
                    } else if (option.equals("l")) {
                        setLibraryDirectory(arg);
                    } else if (option.equals("d")) {
                        setDebugLevel(Level.toLevel(arg, level));
                        level = logger.getLevel();
                    }
                } else {
                    break;
                }
            }
        }
        
        return i == args.length - 1? args[i]: null;
    }

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");

        String unitName = parseArgs(args);
        
        if (unitName == null) {
            System.out.println("Usage is");
            System.out.println("  alf [options] qualifiedName");
            System.out.println("where qualifiedName identifies an Alf unit and");
            System.out.println("allowable options are:");
            System.out.println("  -d OFF|FATAL|ERROR|WARN|INFO|DEBUG|ALL");
            System.out.println("            Set debug logging level (default is as configured)");
            System.out.println("  -l path   Set library directory path (default is \"Library\")");
            System.out.println("  -m path   Set model directory path (default is \"Models\")");
            System.out.println("  -v        Set verbose mode");
            return;
        }
        
        int len = unitName.length();
        if (len > 4 && unitName.substring(len - 4, len).equals(".alf")) {
            unitName = unitName.substring(0, len - 4);
        }
        
        String[] names = unitName.replace(".","::").split("::");
        QualifiedName qualifiedName = new QualifiedName();
        for (String name: names) {
            qualifiedName.getImpl().addName(name);
        }

        RootNamespace root = RootNamespace.getRootScope();
        UnitDefinition unit = RootNamespace.resolveUnit(qualifiedName);
        if (!(unit instanceof MissingUnit)) {
            if (unit.getImpl().resolveStub()) {
                printVerbose("Resolved stub for " + qualifiedName.getPathName());
            }
            
            root.deriveAll();
            Collection<ConstraintViolation> violations = root.checkConstraints();
            if (!violations.isEmpty()) {
                System.out.println("Constraint violations:");
                for (ConstraintViolation violation: violations) {
                    System.out.println("  " + violation);
                }
                
            } else {
                printVerbose("No constraint violations.");
                createLocus();
                FumlMapping.setExecutionFactory(locus.factory);       
                FumlMapping mapping = FumlMapping.getMapping(root);
                try {
                    mapping.getModelElements();
                    printVerbose("Mapped successfully.");
                    NamespaceDefinition definition = unit.getDefinition();
                    Mapping elementMapping = definition.getImpl().getMapping();
                    Element element = ((FumlMapping)elementMapping).getElement();
                    if (element instanceof Behavior && 
                            ((Behavior)element).ownedParameter.isEmpty() ||
                            element instanceof Class_ && 
                            ((Class_)element).isActive && 
                            !((Class_)element).isAbstract && 
                            ((Class_)element).classifierBehavior != null) {
                        createSystemServices();
                        printVerbose("Executing...");
                        if (element instanceof Behavior) {
                            locus.executor.execute(
                                    (Behavior)element, null, new ParameterValueList());
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

                                // Initialize the object.
                                ClassDefinitionMapping classMapping =
                                        (ClassDefinitionMapping)elementMapping;
                                Operation initializer = 
                                        classMapping.getInitializationOperation();
                                locus.executor.execute(
                                        initializer.method.get(0), object, 
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
                    // mapping.print();
                }
            }
        }
    }
}
