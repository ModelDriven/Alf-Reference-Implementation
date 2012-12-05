package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class SignalEvent extends MessageEvent implements
		org.modeldriven.alf.uml.SignalEvent {
	public SignalEvent() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createSignalEvent());
	}

	public SignalEvent(org.eclipse.uml2.uml.SignalEvent base) {
		super(base);
	}

	public org.eclipse.uml2.uml.SignalEvent getBase() {
		return (org.eclipse.uml2.uml.SignalEvent) this.base;
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return (org.modeldriven.alf.uml.Signal) wrap(this.getBase().getSignal());
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(
				signal == null ? null : ((Signal) signal).getBase());
	}

}
