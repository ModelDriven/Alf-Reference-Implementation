package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Generalization extends Element implements
		org.modeldriven.alf.uml.Generalization {
	public Generalization() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createGeneralization());
	}

	public Generalization(org.eclipse.uml2.uml.Generalization base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Generalization getBase() {
		return (org.eclipse.uml2.uml.Generalization) this.base;
	}

	public boolean getIsSubstitutable() {
		return this.getBase().getIsSubstitutable();
	}

	public void setIsSubstitutable(boolean isSubstitutable) {
		this.getBase().setIsSubstitutable(isSubstitutable);
	}

	public org.modeldriven.alf.uml.Classifier getSpecific() {
		return new Classifier(this.getBase().getSpecific());
	}

	public org.modeldriven.alf.uml.Classifier getGeneral() {
		return new Classifier(this.getBase().getGeneral());
	}

	public void setGeneral(org.modeldriven.alf.uml.Classifier general) {
		this.getBase().setGeneral(
				general == null ? null : ((Classifier) general).getBase());
	}

}
