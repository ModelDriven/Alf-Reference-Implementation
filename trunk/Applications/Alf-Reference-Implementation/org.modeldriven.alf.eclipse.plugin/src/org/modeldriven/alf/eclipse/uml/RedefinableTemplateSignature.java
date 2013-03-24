package org.modeldriven.alf.eclipse.uml;

import org.eclipse.uml2.uml.UMLFactory;

public class RedefinableTemplateSignature extends TemplateSignature 
	implements org.modeldriven.alf.uml.RedefinableTemplateSignature {

	public RedefinableTemplateSignature() {
		super(UMLFactory.eINSTANCE.createRedefinableTemplateSignature());
	}
	
	@Override
	public org.eclipse.uml2.uml.RedefinableTemplateSignature getBase() {
		return (org.eclipse.uml2.uml.RedefinableTemplateSignature)this.base;
	}

}
