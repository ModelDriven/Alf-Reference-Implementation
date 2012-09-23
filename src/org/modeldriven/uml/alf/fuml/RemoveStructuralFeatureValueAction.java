package org.modeldriven.uml.alf.fuml;


public class RemoveStructuralFeatureValueAction extends
		WriteStructuralFeatureAction implements
		org.modeldriven.alf.uml.RemoveStructuralFeatureValueAction {
	public RemoveStructuralFeatureValueAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction());
	}

	public RemoveStructuralFeatureValueAction(
			fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.RemoveStructuralFeatureValueAction) this.base;
	}

	public boolean getIsRemoveDuplicates() {
		return this.getBase().isRemoveDuplicates;
	}

	public void setIsRemoveDuplicates(boolean isRemoveDuplicates) {
		this.getBase().setIsRemoveDuplicates(isRemoveDuplicates);
	}

	public org.modeldriven.alf.uml.InputPin getRemoveAt() {
		return new InputPin(this.getBase().removeAt);
	}

	public void setRemoveAt(org.modeldriven.alf.uml.InputPin removeAt) {
		this.getBase().setRemoveAt(((InputPin) removeAt).getBase());
	}

}
