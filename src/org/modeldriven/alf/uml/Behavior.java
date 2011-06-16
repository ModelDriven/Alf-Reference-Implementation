package org.modeldriven.alf.uml;

import java.util.List;

public interface Behavior extends Class {
    
    public BehavioredClassifier getContext();
    
    public List<Parameter> getOwnedParameter();
}
