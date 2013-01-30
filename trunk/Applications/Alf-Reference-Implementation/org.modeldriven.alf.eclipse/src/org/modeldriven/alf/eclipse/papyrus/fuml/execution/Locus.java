/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/
package org.modeldriven.alf.eclipse.papyrus.fuml.execution;

import org.modeldriven.alf.uml.Class_;

import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL3.ExecutionFactoryL3;

public class Locus implements org.modeldriven.alf.fuml.execution.Locus {
    
    org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus locus = null;
    
    public Locus() {
        this.locus = new org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus();
        this.locus.setExecutor(new org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Executor());
        this.locus.setFactory(new org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL3.ExecutionFactoryL3());
    }
    
    public Locus(org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Locus locus) {
        this.locus = locus;
    }
    
    @Override
    public ExecutionFactory getFactory() {
        return this.locus.factory == null? null: new ExecutionFactory(locus.factory);
    }

    @Override
    public Executor getExecutor() {
        return this.locus.executor == null? null: new Executor(locus.executor);
    }

    @Override
    public Object_ instantiate(Class_ type) {
        org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_ object_ = 
                type == null? null: 
                this.locus.instantiate(((org.modeldriven.alf.eclipse.uml.Class_)type).getBase());
        return object_ == null? null: new Object_(object_);
    }

    public void setExecutor(org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.Executor executor) {
        this.locus.setExecutor(executor);
    }

    public void setFactory(ExecutionFactoryL3 factory) {
        this.locus.setFactory(factory);
    }

    public void add(org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_ object) {
        if (object != null) {
            this.locus.add(object);
        }
    }

}
