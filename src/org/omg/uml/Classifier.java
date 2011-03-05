package org.omg.uml;

import java.util.ArrayList;
import java.util.List;

public class Classifier extends NamedElement {
    
    public boolean getIsAbstract() {
        return false;
    }
    
    public List<Feature> getFeature() {
        return new ArrayList<Feature>();
    }

    public boolean isTemplate() {
        return false;
    }

    public List<NamedElement> inheritableMembers() {
        return new ArrayList<NamedElement>();
    }

}
