package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class Behavior extends Class implements org.modeldriven.alf.uml.Behavior {

	public Behavior(fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior getBase() {
		return (fUML.Syntax.CommonBehaviors.BasicBehaviors.Behavior) this.base;
	}

	public boolean getIsReentrant() {
		return this.getBase().isReentrant;
	}

	public void setIsReentrant(boolean isReentrant) {
		this.getBase().isReentrant = isReentrant;
	}

	public org.modeldriven.alf.uml.BehavioralFeature getSpecification() {
		return new BehavioralFeature(this.getBase().specification);
	}

	public void setSpecification(
			org.modeldriven.alf.uml.BehavioralFeature specification) {
		specification.addMethod(this);
	}

	public List<org.modeldriven.alf.uml.Parameter> getOwnedParameter() {
		List<org.modeldriven.alf.uml.Parameter> list = new ArrayList<org.modeldriven.alf.uml.Parameter>();
		for (fUML.Syntax.Classes.Kernel.Parameter element : this.getBase().ownedParameter) {
			list.add(new Parameter(element));
		}
		return list;
	}

	public void addOwnedParameter(org.modeldriven.alf.uml.Parameter ownedParameter) {
		this.getBase()
				.addOwnedParameter(((Parameter) ownedParameter).getBase());
	}

	public org.modeldriven.alf.uml.BehavioredClassifier getContext() {
		return new BehavioredClassifier(this.getBase().context);
	}

}
