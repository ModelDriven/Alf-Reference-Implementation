package org.modeldriven.alf.uml;

import java.util.List;

public interface BehavioralFeature extends Feature, Namespace {
    
    public List<Parameter> getOwnedParameter();

}
