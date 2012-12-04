package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class BehavioralFeature extends Feature implements
		org.modeldriven.alf.uml.BehavioralFeature {

	public BehavioralFeature(fUML.Syntax.Classes.Kernel.BehavioralFeature base) {
		super(base);
	}

	public org.eclipse.uml2.uml.BehavioralFeature getBase() {
		return (org.eclipse.uml2.uml.BehavioralFeature) this.base;
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

	public boolean getIsAbstract() {
		return this.getBase().getIsAbstract();
	}

	public void setIsAbstract(boolean isAbstract) {
		this.getBase().setIsAbstract(isAbstract);
	}

	public List<org.modeldriven.alf.uml.Behavior> getMethod() {
		List<org.modeldriven.alf.uml.Behavior> list = new ArrayList<org.modeldriven.alf.uml.Behavior>();
		for (org.eclipse.uml2.uml.Behavior element : this.getBase().getMethod()) {
			list.add(new Behavior(element));
		}
		return list;
	}

	public void addMethod(org.modeldriven.alf.uml.Behavior method) {
		this.getBase().addMethod(
				method == null ? null : ((Behavior) method).getBase());
	}

	public String getConcurrency() {
		return this.getBase().getConcurrency().toString();
	}

	public void setConcurrency(String concurrency) {
		this.getBase().setConcurrency(
				fUML.Syntax.CommonBehaviors.BasicBehaviors.CallConcurrencyKind
						.valueOf(concurrency));
	}

}
