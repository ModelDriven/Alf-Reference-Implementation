package org.modeldriven.alf.uml;

public interface NamedElement extends Element {
    
    public String getName();
    public VisibilityKind getVisibility();
    public Namespace getNamespace();
    
    public boolean isDistinguishableFrom(NamedElement n, Namespace ns);

}
