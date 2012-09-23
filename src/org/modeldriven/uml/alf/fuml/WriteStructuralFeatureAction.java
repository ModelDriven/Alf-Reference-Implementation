package org.modeldriven.uml.alf.fuml;


public class WriteStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.WriteStructuralFeatureAction {

	public WriteStructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.WriteStructuralFeatureAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.WriteStructuralFeatureAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.WriteStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getValue() {
		return new InputPin(this.getBase().value);
	}

	public void setValue(org.modeldriven.alf.uml.InputPin value) {
		this.getBase().setValue(((InputPin) value).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

}
