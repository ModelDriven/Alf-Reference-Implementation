package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ReadStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.ReadStructuralFeatureAction {
	public ReadStructuralFeatureAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createReadStructuralFeatureAction());
	}

	public ReadStructuralFeatureAction(
			org.eclipse.uml2.uml.ReadStructuralFeatureAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ReadStructuralFeatureAction getBase() {
		return (org.eclipse.uml2.uml.ReadStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
