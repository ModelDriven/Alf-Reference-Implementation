/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * Copyright 2013 Ivar Jacobson International
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html.
 *******************************************************************************/
package org.modeldriven.alf.fuml.impl.execution;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.uml.Class_;
import org.modeldriven.fuml.library.libraryclass.ImplementationObject;

import fUML.Semantics.Loci.LociL3.ExecutionFactoryL3;

public class Locus implements org.modeldriven.alf.fuml.execution.Locus {
    
    private fUML.Semantics.Loci.LociL1.Locus base = null;
    
    public Locus() {
        this.base = new fUML.Semantics.Loci.LociL1.Locus();
        this.base.setExecutor(new fUML.Semantics.Loci.LociL1.Executor());
        this.base.setFactory(new org.modeldriven.fuml.environment.ExecutionFactory());
    }
    
    public Locus(fUML.Semantics.Loci.LociL1.Locus base) {
        this.base = base;
    }
    
    public fUML.Semantics.Loci.LociL1.Locus getBase() {
    	return this.base;
    }
    
    @Override
    public ExecutionFactory getFactory() {
        return this.base.factory == null? null: new ExecutionFactory(base.factory);
    }

    @Override
    public Executor getExecutor() {
        return this.base.executor == null? null: new Executor(base.executor);
    }

    @Override
    public Object_ instantiate(Class_ type) {
        fUML.Semantics.Classes.Kernel.Object_ object_ = 
                type == null? null: 
                this.base.instantiate(((org.modeldriven.alf.fuml.impl.uml.Class_)type).getBase());
        return object_ == null? null: new Object_(object_);
    }
    
    @Override
    public List<Object_> getExtent(Class_ class_) {
    	List<Object_> list = new ArrayList<Object_>();
    	for (fUML.Semantics.Classes.Kernel.ExtensionalValue object: 
    		this.getBase().getExtent(((org.modeldriven.alf.fuml.impl.uml.Class_)class_).getBase())) {
    		list.add(new Object_((fUML.Semantics.Classes.Kernel.Object_)object));
    	}
    	return list;
    }

    public void setExecutor(fUML.Semantics.Loci.LociL1.Executor executor) {
        this.base.setExecutor(executor);
    }

    public void setFactory(ExecutionFactoryL3 factory) {
        this.base.setFactory(factory);
    }

    public void add(ImplementationObject object) {
        if (object != null) {
            this.base.add(object);
        }
    }

}
