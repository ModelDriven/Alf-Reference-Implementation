package org.modeldriven.alf.fuml.impl.execution;

public class OpaqueBehaviorExecution implements
        org.modeldriven.alf.execution.fuml.OpaqueBehaviorExecution {
    
    private fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution base = null;
    
    public OpaqueBehaviorExecution(fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution base) {
        this.base = base;
    }
    
    public fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution getBase() {
        return this.base;
    }

}
