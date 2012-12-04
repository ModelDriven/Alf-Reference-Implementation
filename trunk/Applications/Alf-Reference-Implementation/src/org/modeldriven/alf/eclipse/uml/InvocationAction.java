package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class InvocationAction extends Action implements
		org.modeldriven.alf.uml.InvocationAction {

	public InvocationAction(
			fUML.Syntax.Actions.BasicActions.InvocationAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.InvocationAction getBase() {
		return (org.eclipse.uml2.uml.InvocationAction) this.base;
	}

	public List<org.modeldriven.alf.uml.InputPin> getArgument() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (org.eclipse.uml2.uml.InputPin element : this.getBase()
				.getArgument()) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addArgument(org.modeldriven.alf.uml.InputPin argument) {
		this.getBase().addArgument(
				argument == null ? null : ((InputPin) argument).getBase());
	}

}
