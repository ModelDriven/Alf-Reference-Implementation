package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class TestIdentityAction extends Action implements
		org.modeldriven.alf.uml.TestIdentityAction {
	public TestIdentityAction() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createTestIdentityAction());
	}

	public TestIdentityAction(org.eclipse.uml2.uml.TestIdentityAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.TestIdentityAction getBase() {
		return (org.eclipse.uml2.uml.TestIdentityAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getSecond() {
		return new InputPin(this.getBase().getSecond());
	}

	public void setSecond(org.modeldriven.alf.uml.InputPin second) {
		this.getBase().setSecond(
				second == null ? null : ((InputPin) second).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().getResult());
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getFirst() {
		return new InputPin(this.getBase().getFirst());
	}

	public void setFirst(org.modeldriven.alf.uml.InputPin first) {
		this.getBase().setFirst(
				first == null ? null : ((InputPin) first).getBase());
	}

}
