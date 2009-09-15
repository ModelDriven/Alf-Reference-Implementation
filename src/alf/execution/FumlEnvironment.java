package alf.execution;

import fUML.Semantics.Loci.*;
import fUML.Semantics.Classes.Kernel.ExtensionalValue;
import fUML.Semantics.Classes.Kernel.RedefinitionBasedDispatchStrategy;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import fUML.Semantics.CommonBehaviors.BasicBehaviors.ParameterValueList;
import fUML.Semantics.CommonBehaviors.Communications.FIFOGetNextEventStrategy;

import fUML.Syntax.Classes.Kernel.PrimitiveType;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior;
import fUML.Syntax.CommonBehaviors.BasicBehaviors.OpaqueBehavior;

public class FumlEnvironment extends Environment {
    
    private fUML.Semantics.Loci.Locus locus = null;
    
    public FumlEnvironment() {
        this.locus = new Locus();
        this.locus.setFactory(new ExecutionFactory());
        this.locus.setExecutor(new Executor());
    
        this.locus.factory.setStrategy(new RedefinitionBasedDispatchStrategy());
        this.locus.factory.setStrategy(new FIFOGetNextEventStrategy());
        this.locus.factory.setStrategy(new FirstChoiceStrategy());
    }
    
    public ParameterValueList execute(Behavior behavior, ParameterValueList inputs) {
        return this.locus.executor.execute(behavior, null, inputs);
    }
    
    public void addBuiltInType(PrimitiveType type) {
        this.locus.factory.addBuiltInType(type);
    }
    
    public void addPrimitiveBehavior(OpaqueBehavior behavior) {
        
    }
    
    public void addPrimitiveBehaviorPrototype(OpaqueBehaviorExecution execution) {
        this.locus.factory.addPrimitiveBehaviorPrototype(execution);
    }
    
    public void addInstance(ExtensionalValue value) {
        this.locus.add(value);
    }

}
