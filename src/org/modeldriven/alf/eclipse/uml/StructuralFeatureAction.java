package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class StructuralFeatureAction extends Action implements
		org.modeldriven.alf.uml.StructuralFeatureAction {

	public StructuralFeatureAction(
			org.eclipse.uml2.uml.StructuralFeatureAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.StructuralFeatureAction getBase() {
		return (org.eclipse.uml2.uml.StructuralFeatureAction) this.base;
	}

	public org.modeldriven.alf.uml.StructuralFeature getStructuralFeature() {
		return wrap(this.getBase().getStructuralFeature());
	}

	public void setStructuralFeature(
			org.modeldriven.alf.uml.StructuralFeature structuralFeature) {
		this.getBase().setStructuralFeature(
				structuralFeature == null ? null
						: ((StructuralFeature) structuralFeature).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getObject() {
		return wrap(this.getBase().getObject());
	}

	public void setObject(org.modeldriven.alf.uml.InputPin object) {
		this.getBase().setObject(
				object == null ? null : ((InputPin) object).getBase());
	}

}
