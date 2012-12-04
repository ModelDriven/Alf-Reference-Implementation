package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Behavior extends Class_ implements
		org.modeldriven.alf.uml.Behavior {

	public Behavior(fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Behavior getBase() {
		return (org.eclipse.uml2.uml.Behavior) this.base;
	}

	public boolean getIsReentrant() {
		return this.getBase().getIsReentrant();
	}

	public void setIsReentrant(boolean isReentrant) {
		this.getBase().setIsReentrant(isReentrant);
	}

	public org.modeldriven.alf.uml.BehavioralFeature getSpecification() {
		return new BehavioralFeature(this.getBase().getSpecification());
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
				.getOwnedParameter()) {
			list.add(new Parameter(element));
		}
		return list;
	}

	public void addOwnedParameter(
			org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase().addOwnedParameter(
				ownedParameter == null ? null : ((Parameter) ownedParameter)
						.getBase());
	}

	public org.modeldriven.alf.uml.BehavioredClassifier getContext() {
		return new BehavioredClassifier(this.getBase().getContext());
	}

}
