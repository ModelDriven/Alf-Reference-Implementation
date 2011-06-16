package org.modeldriven.alf.uml;

public interface TemplateableElement extends Element {
    
    public TemplateSignature getTemplateSignature();
    public boolean isTemplate();
    public TemplateBinding getTemplateBinding();

}
