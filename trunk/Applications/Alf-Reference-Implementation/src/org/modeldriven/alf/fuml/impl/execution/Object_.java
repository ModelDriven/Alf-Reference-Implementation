package org.modeldriven.alf.fuml.impl.execution;

import org.modeldriven.alf.uml.Class_;

import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;

public class Object_ implements org.modeldriven.alf.execution.fuml.Object_ {
    
    private fUML.Semantics.Classes.Kernel.Object_ base = null;
    
    public Object_(fUML.Semantics.Classes.Kernel.Object_ base) {
        this.base = base;
    }
    
    public fUML.Semantics.Classes.Kernel.Object_ getBase() {
        return this.base;
    }

    @Override
    public void startBehavior(Class_ classifier) {
        this.base.startBehavior(
                ((org.modeldriven.alf.fuml.impl.uml.Class_)classifier).getBase(), 
                new ParameterValueList());
    }
    
    public String toString() {
        return this.getBase().toString();
    }

}
