package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class CallAction extends InvocationAction implements
		org.modeldriven.alf.uml.CallAction {

	public CallAction(fUML.Syntax.Actions.BasicActions.CallAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CallAction getBase() {
		return (org.eclipse.uml2.uml.CallAction) this.base;
	}

	public boolean getIsSynchronous() {
		return this.getBase().getIsSynchronous();
	}

	public void setIsSynchronous(boolean isSynchronous) {
		this.getBase().setIsSynchronous(isSynchronous);
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

}
