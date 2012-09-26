package org.modeldriven.alf.execution.fuml;

import org.modeldriven.alf.uml.Class_;

public interface Object_ {
    // Note: Only handles starting behaviors with no inputs.
    public void startBehavior(Class_ classifier);
}
