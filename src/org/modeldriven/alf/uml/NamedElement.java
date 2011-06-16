package org.modeldriven.alf.uml;

public interface NamedElement extends Element {
    
    public String getName();
    public VisibilityKind getVisibility();
    public Namespace getNamespace();

}
