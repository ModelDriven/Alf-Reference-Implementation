package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ClearAssociationAction extends Action implements
		org.modeldriven.alf.uml.ClearAssociationAction {
	public ClearAssociationAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createClearAssociationAction());
	}

	public ClearAssociationAction(
			org.eclipse.uml2.uml.ClearAssociationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ClearAssociationAction getBase() {
		return (org.eclipse.uml2.uml.ClearAssociationAction) this.base;
	}

	public org.modeldriven.alf.uml.Association getAssociation() {
		return wrap(this.getBase().getAssociation());
	}

	public void setAssociation(org.modeldriven.alf.uml.Association association) {
		this.getBase().setAssociation(
				association == null ? null : ((Association) association)
						.getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return wrap(this.getBase().getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

}
