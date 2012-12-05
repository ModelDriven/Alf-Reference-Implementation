package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class CallAction extends InvocationAction implements
		org.modeldriven.alf.uml.CallAction {

	public CallAction(org.eclipse.uml2.uml.CallAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.CallAction getBase() {
		return (org.eclipse.uml2.uml.CallAction) this.base;
	}

	public boolean getIsSynchronous() {
		return this.getBase().isSynchronous();
	}

	public void setIsSynchronous(boolean isSynchronous) {
		this.getBase().setIsSynchronous(isSynchronous);
	}

	public List<org.modeldriven.alf.uml.OutputPin> getResult() {
		List<org.modeldriven.alf.uml.OutputPin> list = new ArrayList<org.modeldriven.alf.uml.OutputPin>();
		for (org.eclipse.uml2.uml.OutputPin element : this.getBase()
				.getResults()) {
			list.add(new OutputPin(element));
		}
		return list;
	}

	public void addResult(org.modeldriven.alf.uml.OutputPin result) {
		this.getBase().getResults().add(
				result == null ? null : ((OutputPin) result).getBase());
	}

}
