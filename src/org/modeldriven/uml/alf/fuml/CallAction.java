package org.modeldriven.uml.alf.fuml;

import java.util.ArrayList;
import java.util.List;

public class CallAction extends InvocationAction implements
		org.modeldriven.alf.uml.CallAction {

	public CallAction(fUML.Syntax.Actions.BasicActions.CallAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.CallAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.CallAction) this.base;
	}

	public boolean getIsSynchronous() {
		return this.getBase().isSynchronous;
	}

	public void setIsSynchronous(boolean isSynchronous) {
		this.getBase().isSynchronous = isSynchronous;
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (fUML.Syntax.Actions.BasicActions.OutputPin element : this
				.getBase().result) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().addResult(((OutputPin) result).getBase());
	}

}
