package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.WriteStructuralFeatureAction;

public class AddStructuralFeatureValueAction extends
		WriteStructuralFeatureAction implements
		org.modeldriven.alf.uml.AddStructuralFeatureValueAction {
	public AddStructuralFeatureValueAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.AddStructuralFeatureValueAction());
	}

	public AddStructuralFeatureValueAction(
			fUML.Syntax.Actions.IntermediateActions.AddStructuralFeatureValueAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.AddStructuralFeatureValueAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.AddStructuralFeatureValueAction) this.base;
	}

	public boolean getIsReplaceAll() {
		return this.getBase().isReplaceAll;
	}

	public void setIsReplaceAll(boolean isReplaceAll) {
		this.getBase().setIsReplaceAll(isReplaceAll);
	}

	public org.modeldriven.alf.uml.InputPin getInsertAt() {
		return new InputPin(this.getBase().insertAt);
	}

	public void setInsertAt(org.modeldriven.alf.uml.InputPin insertAt) {
		this.getBase().setInsertAt(((InputPin) insertAt).getBase());
	}

}
