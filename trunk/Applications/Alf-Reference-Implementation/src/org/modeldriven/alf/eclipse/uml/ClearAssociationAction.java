package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ClearAssociationAction extends Action implements
		org.modeldriven.alf.uml.ClearAssociationAction {
	public ClearAssociationAction() {
		this(UMLFactory.eINSTANCE.createClearAssociationAction());
	}

	public ClearAssociationAction(
			fUML.Syntax.Actions.IntermediateActions.ClearAssociationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ClearAssociationAction getBase() {
		return (org.eclipse.uml2.uml.ClearAssociationAction) this.base;
	}

	public org.modeldriven.alf.uml.Association getAssociation() {
		return new Association(this.getBase().getAssociation());
	}

	public void setAssociation(org.modeldriven.alf.uml.Association association) {
		this.getBase().setAssociation(
				association == null ? null : ((Association) association)
						.getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

}
