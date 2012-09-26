package org.modeldriven.alf.fumlri.execution;

import org.modeldriven.alf.uml.Class_;
import org.modeldriven.fuml.library.libraryclass.ImplementationObject;

import fUML.Semantics.Loci.LociL3.ExecutionFactoryL3;

public class Locus implements org.modeldriven.alf.execution.fuml.Locus {
    
    fUML.Semantics.Loci.LociL1.Locus locus = null;
    
    public Locus() {
        this.locus = new fUML.Semantics.Loci.LociL1.Locus();
        this.locus.setExecutor(new fUML.Semantics.Loci.LociL1.Executor());
        this.locus.setFactory(new fUML.Semantics.Loci.LociL3.ExecutionFactoryL3());
    }
    
    public Locus(fUML.Semantics.Loci.LociL1.Locus locus) {
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
        fUML.Semantics.Classes.Kernel.Object_ object_ = 
                type == null? null: 
                this.locus.instantiate(((org.modeldriven.alf.fumlri.uml.Class_)type).getBase());
        return object_ == null? null: new Object_(object_);
    }

    public void setExecutor(fUML.Semantics.Loci.LociL1.Executor executor) {
        this.locus.setExecutor(executor);
    }

    public void setFactory(ExecutionFactoryL3 factory) {
        this.locus.setFactory(factory);
    }

    public void add(ImplementationObject object) {
        if (object != null) {
            this.locus.add(object);
        }
    }

}
