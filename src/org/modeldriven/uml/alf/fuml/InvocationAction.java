package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class InvocationAction extends Action implements
		org.modeldriven.alf.uml.InvocationAction {

	public InvocationAction(
			fUML.Syntax.Actions.BasicActions.InvocationAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.InvocationAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.InvocationAction) this.base;
	}

	public List<org.modeldriven.alf.uml.InputPin> getArgument() {
		List<org.modeldriven.alf.uml.InputPin> list = new ArrayList<org.modeldriven.alf.uml.InputPin>();
		for (fUML.Syntax.Actions.BasicActions.InputPin element : this.getBase().argument) {
			list.add(new InputPin(element));
		}
		return list;
	}

	public void addArgument(org.modeldriven.alf.uml.InputPin argument) {
		this.getBase().addArgument(((InputPin) argument).getBase());
	}

}
