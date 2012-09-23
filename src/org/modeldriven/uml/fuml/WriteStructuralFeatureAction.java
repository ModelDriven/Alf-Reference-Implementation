package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.OutputPin;
import org.modeldriven.uml.fuml.StructuralFeatureAction;

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
