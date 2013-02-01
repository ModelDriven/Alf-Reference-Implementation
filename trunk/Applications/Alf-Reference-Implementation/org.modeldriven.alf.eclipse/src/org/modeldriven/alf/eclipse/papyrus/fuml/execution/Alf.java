/*******************************************************************************
 * Copyright 2013 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.eclipse.papyrus.fuml.execution;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.channel.StandardInputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.channel.StandardOutputChannelObject;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.common.Status;
import org.modeldriven.alf.eclipse.papyrus.fuml.library.libraryclass.ImplementationObject;
import org.modeldriven.alf.fuml.mapping.FumlMappingFactory;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.Class_;
import org.modeldriven.alf.uml.Classifier;
import org.modeldriven.alf.uml.DataType;
import org.modeldriven.alf.uml.ElementFactory;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.FirstChoiceStrategy;
import org.eclipse.papyrus.moka.fuml.debug.Debug;

public class Alf extends org.modeldriven.alf.fuml.execution.Alf {
    
	@Override
    public void setDebugLevel(Level level) {
		super.setDebugLevel(level);
        Logger logger = Logger.getLogger(Debug.class);
        logger.setLevel(level);
    }
    
    @Override
    protected Locus createLocus() {
        Locus locus = new Locus();
        org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ExecutionFactory factory = locus.getFactory().getBase(); 
        factory.setStrategy(new RedefinitionBasedDispatchStrategy());
        factory.setStrategy(new FIFOGetNextEventStrategy());
        factory.setStrategy(new FirstChoiceStrategy());       
        
        return locus;
    }
    
    @Override
    protected FumlMappingFactory createFumlFactory() {
        return new org.modeldriven.alf.eclipse.papyrus.fuml.mapping.FumlMappingFactory();
    }
    
    @Override
    protected ElementFactory createElementFactory() {
        return new org.modeldriven.alf.eclipse.uml.ElementFactory();
    }
    
    @Override
    protected void createSystemServices() {
        QualifiedName standardOutputChannel = 
            RootNamespace.getBasicInputOutput().getImpl().copy().
                addName("StandardOutputChannel");
        this.createSystemService
            (standardOutputChannel, new StandardOutputChannelObject());
        
        QualifiedName standardInputChannel = 
            RootNamespace.getBasicInputOutput().getImpl().copy().
                addName("StandardInputChannel");
        this.createSystemService
            (standardInputChannel, new StandardInputChannelObject());
        
        QualifiedName status = 
            RootNamespace.getBasicInputOutput().getImpl().copy().
                addName("Status");
        Classifier statusType = getClassifier(status);
        if (statusType instanceof DataType) {
            Status.setStatusType(((org.modeldriven.alf.eclipse.uml.DataType)statusType).getBase());
        } else {
            System.out.println("Cannot find Status datatype.");
        }
    }
    
    private void createSystemService (
            QualifiedName name,
            ImplementationObject object) {
        Classifier type = getClassifier(name);
        if (type instanceof Class_) {
            org.eclipse.uml2.uml.Class class_ = 
                    ((org.modeldriven.alf.eclipse.uml.Class_)type).getBase();
            object.types.add(class_);
            ((Locus)this.getLocus()).add(object);
            printVerbose("Instantiated " + name.getPathName() + 
                    " as " + object.getClass().getName());
        }
    }
    
   public Alf(String[] args) {
        super(args);
   }
   
   public static void main(String[] args) {
       new Alf(args);
   }
}
