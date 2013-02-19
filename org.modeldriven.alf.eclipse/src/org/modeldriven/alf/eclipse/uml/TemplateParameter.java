package org.modeldriven.alf.eclipse.uml;

public class TemplateParameter extends Element implements org.modeldriven.alf.uml.TemplateParameter {

	public TemplateParameter(org.eclipse.uml2.uml.TemplateParameter base) {
		super(base);
	}
	
	@Override
	public org.eclipse.uml2.uml.TemplateParameter getBase() {
		return (org.eclipse.uml2.uml.TemplateParameter)this.base;
	}

	@Override
	public org.modeldriven.alf.uml.ParameterableElement getParameteredElement() {
		return (org.modeldriven.alf.uml.ParameterableElement)wrap(this.getBase().getParameteredElement());
	}

	@Override
	public void setParameteredElement(org.modeldriven.alf.uml.ParameterableElement parameteredElement) {
		this.getBase().setParameteredElement(parameteredElement == null? null:
				(org.eclipse.uml2.uml.ParameterableElement)((Element)parameteredElement).getBase());
	}

	@Override
	public org.modeldriven.alf.uml.ParameterableElement getOwnedParameteredElement() {
		return (org.modeldriven.alf.uml.ParameterableElement)wrap(this.getBase().getOwnedParameteredElement());
	}

	@Override
	public void setOwnedParameteredElement(
			org.modeldriven.alf.uml.ParameterableElement parameteredElement) {
		this.getBase().setOwnedParameteredElement(parameteredElement == null? null:
				(org.eclipse.uml2.uml.ParameterableElement)((Element)parameteredElement).getBase());
	}

	@Override
	public org.modeldriven.alf.uml.TemplateSignature getSignature() {
		return (org.modeldriven.alf.uml.TemplateSignature)wrap(this.getBase().getSignature());
	}

}
