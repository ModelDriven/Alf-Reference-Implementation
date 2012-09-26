/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.execution.fuml;

import org.modeldriven.alf.uml.ElementFactory;
import org.modeldriven.alf.mapping.Mapping;
import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.FumlMapping;
import org.modeldriven.alf.mapping.fuml.FumlMappingFactory;
import org.modeldriven.alf.mapping.fuml.units.ClassDefinitionMapping;
import org.modeldriven.alf.syntax.units.ClassDefinition;
import org.modeldriven.alf.syntax.units.NamespaceDefinition;
import org.modeldriven.alf.syntax.units.OperationDefinition;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Operation;
import org.modeldriven.alf.uml.Behavior;

public abstract class Alf extends org.modeldriven.alf.execution.Alf {
    
    private Locus locus = null;
    
    public Locus getLocus() {
        if (this.locus == null) {
            this.locus = this.createLocus();
        }
        return this.locus;
    }
    
    protected abstract Locus createLocus();
    protected abstract FumlMappingFactory createFumlFactory();
    protected abstract ElementFactory createElementFactory();
    protected void createSystemServices() { }
    
    public void execute(NamespaceDefinition definition) {
        Locus locus = this.getLocus();
        FumlMapping.setExecutionFactory(locus.getFactory());
        FumlMapping.setFumlFactory(this.createFumlFactory());
        FumlMapping.setElementFactory(this.createElementFactory());
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
                    locus.getExecutor().execute((Behavior)element, null);
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
                        locus.getExecutor().execute(
                                ((Behavior)initializer.getMethod().get(0)), 
                                object);

                        // Execute the classifier behavior.
                        object.startBehavior(class_);
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
    
}
