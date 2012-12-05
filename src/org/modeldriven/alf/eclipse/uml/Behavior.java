package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Behavior extends Class_ implements
		org.modeldriven.alf.uml.Behavior {

	public Behavior(org.eclipse.uml2.uml.Behavior base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Behavior getBase() {
		return (org.eclipse.uml2.uml.Behavior) this.base;
	}

	public boolean getIsReentrant() {
		return this.getBase().isReentrant();
	}

	public void setIsReentrant(boolean isReentrant) {
		this.getBase().setIsReentrant(isReentrant);
	}

	public org.modeldriven.alf.uml.BehavioralFeature getSpecification() {
		return (org.modeldriven.alf.uml.BehavioralFeature) wrap(this.getBase()
				.getSpecification());
	}

	public void setSpecification(
			org.modeldriven.alf.uml.BehavioralFeature specification) {
		this.getBase().setSpecification(
				specification == null ? null
						: ((BehavioralFeature) specification).getBase());
	}

	public List<org.modeldriven.alf.uml.Parameter> getOwnedParameter() {
		List<org.modeldriven.alf.uml.Parameter> list = new ArrayList<org.modeldriven.alf.uml.Parameter>();
		for (org.eclipse.uml2.uml.Parameter element : this.getBase()
				.getOwnedParameters()) {
			list.add((org.modeldriven.alf.uml.Parameter) wrap(element));
		}
		return list;
	}

	public void addOwnedParameter(
			org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase().getOwnedParameters().add(
				ownedParameter == null ? null : ((Parameter) ownedParameter)
						.getBase());
	}

	public org.modeldriven.alf.uml.BehavioredClassifier getContext() {
		return (org.modeldriven.alf.uml.BehavioredClassifier) wrap(this
				.getBase().getContext());
	}

}
