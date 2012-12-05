package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class AddStructuralFeatureValueAction extends
		WriteStructuralFeatureAction implements
		org.modeldriven.alf.uml.AddStructuralFeatureValueAction {
	public AddStructuralFeatureValueAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createAddStructuralFeatureValueAction());
	}

	public AddStructuralFeatureValueAction(
			org.eclipse.uml2.uml.AddStructuralFeatureValueAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.AddStructuralFeatureValueAction getBase() {
		return (org.eclipse.uml2.uml.AddStructuralFeatureValueAction) this.base;
	}

	public boolean getIsReplaceAll() {
		return this.getBase().isReplaceAll();
	}

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.getBase().setIsReplaceAll(isReplaceAll);
	}

	public org.modeldriven.alf.uml.InputPin getInsertAt() {
		return (org.modeldriven.alf.uml.InputPin) wrap(this.getBase()
				.getInsertAt());
	}

	public void setInsertAt(org.modeldriven.alf.uml.InputPin insertAt) {
		this.getBase().setInsertAt(
				insertAt == null ? null : ((InputPin) insertAt).getBase());
	}

}
