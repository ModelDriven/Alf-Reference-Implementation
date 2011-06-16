package org.modeldriven.alf.uml;

import java.util.List;
import java.util.Set;

public interface Classifier extends 
    Type, Namespace, TemplateableElement, ParameterableElement {
    
    public boolean getIsAbstract();
    public List<Feature> getFeature();
    public List<Property> getAttribute();
    
    public List<NamedElement> inheritableMembers();
    public Set<Classifier> parents();
    public Set<Classifier> allParents();
    public boolean conformsTo(Classifier other);

}
