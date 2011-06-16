package org.modeldriven.alf.uml;

import java.util.List;

public interface TemplateSignature extends Element {
    
    public List<TemplateParameter> getParameter();

    public TemplateableElement getTemplate();

}
