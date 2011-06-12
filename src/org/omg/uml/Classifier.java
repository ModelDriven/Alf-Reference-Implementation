package org.omg.uml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Classifier extends Namespace 
    implements TemplateableElement, ParameterableElement {
    
    public boolean getIsAbstract() {
        return false;
    }
    
    public List<Feature> getFeature() {
        return new ArrayList<Feature>();
    }

    public List<Property> getAttribute() {
        return new ArrayList<Property>();
    }

    public TemplateSignature getTemplateSignature() {
        return null;
    }

    public TemplateParameter getTemplateParameter() {
        return null;
    }

    public boolean isTemplate() {
        return false;
    }

    public List<NamedElement> inheritableMembers() {
        return new ArrayList<NamedElement>();
    }

    public Set<Classifier> parents() {
        return new HashSet<Classifier>();
    }

    public Set<Classifier> allParents() {
        return new HashSet<Classifier>();
    }

    public boolean conformsTo(Classifier other) {
        return false;
    }

}
