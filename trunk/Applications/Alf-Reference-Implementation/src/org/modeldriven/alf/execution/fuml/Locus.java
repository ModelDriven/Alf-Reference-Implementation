package org.modeldriven.alf.execution.fuml;

import org.modeldriven.alf.uml.Class_;

public interface Locus {
    public ExecutionFactory getFactory();

    public Executor getExecutor();

    public Object_ instantiate(Class_ type);
}
