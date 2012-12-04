package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class AddStructuralFeatureValueAction extends
		WriteStructuralFeatureAction implements
		org.modeldriven.alf.uml.AddStructuralFeatureValueAction {
	public AddStructuralFeatureValueAction() {
		this(UMLFactory.eINSTANCE.createAddStructuralFeatureValueAction());
	}

	public AddStructuralFeatureValueAction(
			fUML.Syntax.Actions.IntermediateActions.AddStructuralFeatureValueAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.AddStructuralFeatureValueAction getBase() {
		return (org.eclipse.uml2.uml.AddStructuralFeatureValueAction) this.base;
	}

	public boolean getIsReplaceAll() {
		return this.getBase().getIsReplaceAll();
	}

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.getBase().setIsReplaceAll(isReplaceAll);
	}

	public org.modeldriven.alf.uml.InputPin getInsertAt() {
		return new InputPin(this.getBase().getInsertAt());
	}

	public void setInsertAt(org.modeldriven.alf.uml.InputPin insertAt) {
		this.getBase().setInsertAt(
				insertAt == null ? null : ((InputPin) insertAt).getBase());
	}

}
