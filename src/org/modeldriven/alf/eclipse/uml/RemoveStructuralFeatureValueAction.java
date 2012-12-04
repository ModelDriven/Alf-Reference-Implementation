package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class RemoveStructuralFeatureValueAction extends
		WriteStructuralFeatureAction implements
		org.modeldriven.alf.uml.RemoveStructuralFeatureValueAction {
	public RemoveStructuralFeatureValueAction() {
		this(UMLFactory.eINSTANCE.createRemoveStructuralFeatureValueAction());
	}

	public RemoveStructuralFeatureValueAction(
			fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.RemoveStructuralFeatureValueAction getBase() {
		return (org.eclipse.uml2.uml.RemoveStructuralFeatureValueAction) this.base;
	}

	public boolean getIsRemoveDuplicates() {
		return this.getBase().getIsRemoveDuplicates();
	}

	public void setIsRemoveDuplicates(boolean isRemoveDuplicates) {
		this.getBase().setIsRemoveDuplicates(isRemoveDuplicates);
	}

	public org.modeldriven.alf.uml.InputPin getRemoveAt() {
		return new InputPin(this.getBase().getRemoveAt());
	}

	public void setRemoveAt(org.modeldriven.alf.uml.InputPin removeAt) {
		this.getBase().setRemoveAt(
				removeAt == null ? null : ((InputPin) removeAt).getBase());
	}

}
