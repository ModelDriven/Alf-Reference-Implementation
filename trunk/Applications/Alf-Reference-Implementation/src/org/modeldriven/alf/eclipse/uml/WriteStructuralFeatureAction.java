package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class WriteStructuralFeatureAction extends StructuralFeatureAction
		implements org.modeldriven.alf.uml.WriteStructuralFeatureAction {

	public WriteStructuralFeatureAction(
			org.eclipse.uml2.uml.WriteStructuralFeatureAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.WriteStructuralFeatureAction getBase() {
		return (org.eclipse.uml2.uml.WriteStructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getValue() {
		return wrap(this.getBase().getValue());
	}

	public void setValue(org.modeldriven.alf.uml.InputPin value) {
		this.getBase().setValue(
				value == null ? null : ((InputPin) value).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return wrap(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
