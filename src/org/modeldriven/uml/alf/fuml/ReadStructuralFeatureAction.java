package org.modeldriven.uml.alf.fuml;


public class ReadStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.ReadStructuralFeatureAction {
	public ReadStructuralFeatureAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction());
	}

	public ReadStructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ReadStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

}
