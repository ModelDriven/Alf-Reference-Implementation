package org.modeldriven.alf.uml;

import java.util.Collection;

public interface TemplateParameterSubstitution {

    public TemplateParameter getFormal();
    public Collection<ParameterableElement> getActual();
    public Collection<ParameterableElement> getOwnedActual();
    
}
