package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ClearStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.ClearStructuralFeatureAction {
	public ClearStructuralFeatureAction() {
		this(UMLFactory.eINSTANCE.createClearStructuralFeatureAction());
	}

	public ClearStructuralFeatureAction(
			fUML.Syntax.Actions.IntermediateActions.ClearStructuralFeatureAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ClearStructuralFeatureAction getBase() {
		return (org.eclipse.uml2.uml.ClearStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
