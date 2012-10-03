package org.modeldriven.alf.fuml.impl.execution;

import java.util.ArrayList;
import java.util.List;

import org.modeldriven.alf.execution.fuml.OpaqueBehaviorExecution;
import org.modeldriven.alf.uml.PrimitiveType;

public class ExecutionFactory implements org.modeldriven.alf.execution.fuml.ExecutionFactory {
    
    private fUML.Semantics.Loci.LociL1.ExecutionFactory base = null;
    
    public ExecutionFactory() {
        this(new fUML.Semantics.Loci.LociL3.ExecutionFactoryL3());
    }

    public ExecutionFactory(fUML.Semantics.Loci.LociL1.ExecutionFactory factory) {
        this.base = factory;
    }
    
    public fUML.Semantics.Loci.LociL1.ExecutionFactory getBase() {
        return this.base;
    }

    @Override
    public List<PrimitiveType> getBuiltInTypes() {
        List<PrimitiveType> builtInTypes = new ArrayList<PrimitiveType>();
        for (fUML.Syntax.Classes.Kernel.PrimitiveType type: this.getBase().builtInTypes) {
            builtInTypes.add(new org.modeldriven.alf.fuml.impl.uml.PrimitiveType(type));
        }
        return builtInTypes;
    }

    @Override
    public void addBuiltInType(PrimitiveType type) {
        this.getBase().addBuiltInType(
                ((org.modeldriven.alf.fuml.impl.uml.PrimitiveType)type).getBase());
    }

    @Override
    public void addPrimitiveBehaviorPrototype(OpaqueBehaviorExecution execution) {
        this.getBase().addPrimitiveBehaviorPrototype(
                ((org.modeldriven.alf.fuml.impl.execution.OpaqueBehaviorExecution)execution).getBase());
    }
    
}
