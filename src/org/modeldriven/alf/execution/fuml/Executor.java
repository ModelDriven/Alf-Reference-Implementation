package org.modeldriven.alf.execution.fuml;

import org.modeldriven.alf.uml.Behavior;

public interface Executor {
    // Note: Only handles execution of behaviors with no inputs or outputs.
    public void execute(Behavior behavior, Object_ context);
}
