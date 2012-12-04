package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class AcceptEventAction extends Action implements
		org.modeldriven.alf.uml.AcceptEventAction {
	public AcceptEventAction() {
		this(UMLFactory.eINSTANCE.createAcceptEventAction());
	}

	public AcceptEventAction(
			fUML.Syntax.Actions.CompleteActions.AcceptEventAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.AcceptEventAction getBase() {
		return (org.eclipse.uml2.uml.AcceptEventAction) this.base;
	}

	public boolean getIsUnmarshall() {
		return this.getBase().getIsUnmarshall();
	}

	public void setIsUnmarshall(boolean isUnmarshall) {
		this.getBase().setIsUnmarshall(isUnmarshall);
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getResult()) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().addResult(
				result == null ? null : ((OutputPin) result).getBase());
	}

	public List<org.modeldriven.alf.uml.Trigger> getTrigger() {
		List<org.modeldriven.alf.uml.Trigger> list = new ArrayList<org.modeldriven.alf.uml.Trigger>();
		for (org.eclipse.uml2.uml.Trigger element : this.getBase().getTrigger()) {
			list.add(new Trigger(element));
		}
		return list;
	}

	public void addTrigger(org.modeldriven.alf.uml.Trigger trigger) {
		this.getBase().addTrigger(
				trigger == null ? null : ((Trigger) trigger).getBase());
	}

}
