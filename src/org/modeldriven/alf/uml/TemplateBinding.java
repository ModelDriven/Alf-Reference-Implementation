package org.modeldriven.alf.uml;

import java.util.Collection;

public interface TemplateBinding extends Element {
    
    public TemplateSignature getSignature();
    public Collection<TemplateParameterSubstitution> getParameterSubstitution();
    
}
