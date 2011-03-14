package org.omg.uml;

import java.util.ArrayList;
import java.util.List;

public class Behavior extends Class {
    
    public BehavioredClassifier getContext() {
        return null;
    }
    
    public List<Parameter> getOwnedParameter() {
        return new ArrayList<Parameter>();
    }

}
