package org.modeldriven.alf.uml;

import java.util.Collection;

public interface RedefinableElement extends NamedElement {

    public Collection<RedefinableElement> getRedefinedElement();
    
}
