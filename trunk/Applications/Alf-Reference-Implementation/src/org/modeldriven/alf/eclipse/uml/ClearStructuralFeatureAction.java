package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ClearStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.ClearStructuralFeatureAction {
	public ClearStructuralFeatureAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createClearStructuralFeatureAction());
	}

	public ClearStructuralFeatureAction(
			org.eclipse.uml2.uml.ClearStructuralFeatureAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ClearStructuralFeatureAction getBase() {
		return (org.eclipse.uml2.uml.ClearStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return wrap(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
