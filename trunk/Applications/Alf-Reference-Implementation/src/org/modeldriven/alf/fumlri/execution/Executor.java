package org.modeldriven.alf.fumlri.execution;

import org.modeldriven.alf.execution.fuml.Object_;
import org.modeldriven.alf.uml.Behavior;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class Executor implements org.modeldriven.alf.execution.fuml.Executor {
    
    private fUML.Semantics.Loci.LociL1.Executor base = null;
    
    public Executor() {
        this(new fUML.Semantics.Loci.LociL1.Executor());
    }
    
    public Executor(fUML.Semantics.Loci.LociL1.Executor base) {
        this.base = base;
    }
    
    public fUML.Semantics.Loci.LociL1.Executor getBase() {
        return this.base;
    }

    @Override
    public void execute(Behavior behavior, Object_ context) {
        this.base.execute(
                ((org.modeldriven.alf.fumlri.uml.Behavior)behavior).getBase(), 
                ((org.modeldriven.alf.fumlri.execution.Object_)context).getBase(),
                new ParameterValueList());
    }

}
