package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Action;
import org.modeldriven.uml.fuml.InputPin;
import org.modeldriven.uml.fuml.OutputPin;

public class TestIdentityAction extends Action implements
		org.modeldriven.alf.uml.TestIdentityAction {
	public TestIdentityAction() {
		this(new fUML.Syntax.Actions.IntermediateActions.TestIdentityAction());
	}

	public TestIdentityAction(
			fUML.Syntax.Actions.IntermediateActions.TestIdentityAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.IntermediateActions.TestIdentityAction getBase() {
		return (fUML.Syntax.Actions.IntermediateActions.TestIdentityAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getSecond() {
		return new InputPin(this.getBase().second);
	}

	public void setSecond(org.modeldriven.alf.uml.InputPin second) {
		this.getBase().setSecond(((InputPin) second).getBase());
	}

	public org.modeldriven.alf.uml.OutputPin getResult() {
		return new OutputPin(this.getBase().result);
	}

	public void setResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().setResult(((OutputPin) result).getBase());
	}

	public org.modeldriven.alf.uml.InputPin getFirst() {
		return new InputPin(this.getBase().first);
	}

	public void setFirst(org.modeldriven.alf.uml.InputPin first) {
		this.getBase().setFirst(((InputPin) first).getBase());
	}

}
