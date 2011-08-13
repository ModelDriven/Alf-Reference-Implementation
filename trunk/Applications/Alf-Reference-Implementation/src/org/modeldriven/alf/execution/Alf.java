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
import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.syntax.units.UnitDefinition;
import org.modeldriven.fuml.library.channel.StandardOutputChannelObject;
import org.modeldriven.fuml.library.libraryclass.ImplementationObject;

import fUML.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import fUML.Semantics.Loci.LociL1.Executor;
import fUML.Semantics.Loci.LociL1.FirstChoiceStrategy;
import fUML.Semantics.Loci.LociL1.Locus;
import fUML.Semantics.Loci.LociL3.ExecutionFactoryL3;
import fUML.Syntax.Classes.Kernel.Class_;
import fUML.Syntax.Classes.Kernel.Element;
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
    }
    
    private static void createSystemService (
            QualifiedName name,
            ImplementationObject object) {
        ElementReference referent = 
            name.getImpl().getClassifierReferent();
        FumlMapping mapping = FumlMapping.getMapping(referent);
        if (mapping instanceof ElementReferenceMapping) {
            mapping = ((ElementReferenceMapping)mapping).getMapping();
            if (mapping instanceof ClassDefinitionMapping) {
                try {
                    Class_ type = (Class_)((ClassDefinitionMapping)mapping).
                        getClassifier();
                    object.types.addValue(type);
                    locus.add(object);
                    System.out.println("Instantiated " + name.getPathName() + 
                            " as " + type.name + "(" + type + ")");
                } catch (MappingError e) {
                    System.out.println("Cannot map " + name.getPathName());
                    System.out.println(" error: " + e.getMessage());
                }
            }
        }
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
                System.out.println("[Alf] parsedElement=" + parsedElement);
                Mapping elementMapping = ((UnitDefinition)parsedElement).
                    getDefinition().getImpl().getMapping();
                System.out.println("[Alf] elementMapping=" + elementMapping);
                Element behavior = ((FumlMapping)elementMapping).getElement();
                if (behavior instanceof Behavior && 
                        ((Behavior)behavior).ownedParameter.isEmpty()) {
                    createSystemServices();
                    System.out.println("Executing...");
                    locus.executor.execute
                        ((Behavior)behavior, null, new ParameterValueList());
                } else {
                    System.out.println("Cannot execute: " + behavior);
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
