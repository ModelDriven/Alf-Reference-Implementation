/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.impl.execution;

import org.apache.log4j.Logger;
import org.modeldriven.alf.fuml.execution.OpaqueBehaviorExecution;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.ElementFactory;

import org.modeldriven.fuml.library.channel.StandardInputChannelObject;
import org.modeldriven.fuml.library.channel.StandardOutputChannelObject;
import org.modeldriven.fuml.library.common.Status;
import org.modeldriven.fuml.library.libraryclass.ImplementationObject;

import fUML.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import fUML.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import fUML.Semantics.Loci.LociL1.FirstChoiceStrategy;

public class Alf extends org.modeldriven.alf.fuml.execution.Alf {
    
    @Override
    public void setDebugLevel(String level) {
        super.setDebugLevel(level);
        Logger logger = Logger.getLogger(fUML.Debug.class);
        logger.setLevel(this.debugLevel);
    }
    
    @Override
    protected org.modeldriven.alf.fuml.execution.Locus createLocus() {
        Locus locus = new Locus();
        fUML.Semantics.Loci.LociL1.ExecutionFactory factory = locus.getFactory().getBase(); 
        factory.setStrategy(new RedefinitionBasedDispatchStrategy());
        factory.setStrategy(new FIFOGetNextEventStrategy());
        factory.setStrategy(new FirstChoiceStrategy());       
        
        return locus;
    }
    
    @Override
    protected ElementFactory createElementFactory() {
        return org.modeldriven.alf.fuml.impl.uml.Element.FACTORY;
    }
    
    @Override
    protected OpaqueBehaviorExecution getUnimplementedBehaviorExecution() {
        return new UnimplementedBehaviorExecution();
    }
    
    @Override
    protected OpaqueBehaviorExecution getOpaqueBehaviorExecution(Object object) {
    	fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution execution =
    			object instanceof org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution?
    					new org.modeldriven.alf.fuml.impl.library.OpaqueBehaviorExecution(
    							(org.modeldriven.alf.fuml.library.OpaqueBehaviorExecution)object):
    						(fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution)object;
        return new org.modeldriven.alf.fuml.impl.execution.OpaqueBehaviorExecution(execution);
    }
    
    @Override
    protected String getPrototypeClassName(Member definition, String prototypeName) {
        QualifiedName definitionName = definition.getImpl().getQualifiedName();
        String rootName = definitionName.getNameBinding().get(0).getName();
        return "org.modeldriven." + 
            (rootName.equals("FoundationalModelLibrary")? "fuml": "alf.fuml") +
            ".library." + 
            definition.getNamespace().getName().toLowerCase() + "." + prototypeName;
    }

     @Override
    protected void createSystemServices() {
        QualifiedName standardOutputChannel = 
            RootNamespace.getRootScope().getBasicInputOutput().getImpl().copy().
                addName("StandardOutputChannel");
        this.createSystemService
            (standardOutputChannel, new StandardOutputChannelObject());
        
        QualifiedName standardInputChannel = 
            RootNamespace.getRootScope().getBasicInputOutput().getImpl().copy().
                addName("StandardInputChannel");
        this.createSystemService
            (standardInputChannel, new StandardInputChannelObject());
        
        QualifiedName status = 
            RootNamespace.getRootScope().getBasicInputOutput().getImpl().copy().
                addName("Status");
        Classifier statusType = getClassifier(status);
        if (statusType instanceof DataType) {
            Status.setStatusType(((org.modeldriven.alf.fuml.impl.uml.DataType)statusType).getBase());
        } else {
            System.out.println("Cannot find Status datatype.");
        }
    }
    
    private void createSystemService (
            QualifiedName name,
            ImplementationObject object) {
        Classifier type = getClassifier(name);
        if (type instanceof Class_) {
            fUML.Syntax.Classes.Kernel.Class_ class_ = 
                    ((org.modeldriven.alf.fuml.impl.uml.Class_)type).getBase();
            object.types.addValue(class_);
            ((Locus)this.getLocus()).add(object);
            printVerbose("Instantiated " + name.getPathName() + 
                    " as " + object.getClass().getName());
        }
    }
    
    public static void main(String[] args) {
        new Alf().run(args);
    }
}
