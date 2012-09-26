package org.modeldriven.alf.execution.fuml;

import java.util.List;

import org.modeldriven.alf.uml.PrimitiveType;

public interface ExecutionFactory {

    List<PrimitiveType> getBuiltInTypes();

    void addBuiltInType(PrimitiveType type);

    void addPrimitiveBehaviorPrototype(OpaqueBehaviorExecution execution);

}
