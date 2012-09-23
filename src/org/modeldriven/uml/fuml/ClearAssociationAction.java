package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.Association;
import org.modeldriven.uml.fuml.InputPin;

public class ClearAssociationAction extends Action implements
		org.modeldriven.alf.uml.ClearAssociationAction {
	public ClearAssociationAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.ClearAssociationAction());
	}

	public ClearAssociationAction(
			fUML.Syntax.Actions.IntermediateActions.ClearAssociationAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ClearAssociationAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ClearAssociationAction) this.base;
	}

	public org.modeldriven.alf.uml.Association getAssociation() {
		return new Association(this.getBase().association);
	}

	public void setAssociation(org.modeldriven.alf.uml.Association association) {
		this.getBase().setAssociation(((Association) association).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return new InputPin(this.getBase().object);
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(((InputPin) object).getBase());
	}

}
