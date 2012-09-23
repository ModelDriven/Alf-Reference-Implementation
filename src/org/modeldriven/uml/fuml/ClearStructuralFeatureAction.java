package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.OutputPin;
import org.modeldriven.uml.fuml.StructuralFeatureAction;

public class ClearStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.ClearStructuralFeatureAction {
	public ClearStructuralFeatureAction() {
		this(
				new fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction());
	}

	public ClearStructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

}
