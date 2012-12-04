package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class SendSignalAction extends InvocationAction implements
		org.modeldriven.alf.uml.SendSignalAction {
	public SendSignalAction() {
		this(UMLFactory.eINSTANCE.createSendSignalAction());
	}

	public SendSignalAction(
			fUML.Syntax.Actions.BasicActions.SendSignalAction base) {
		super(base);
	}

	public org.eclipse.uml2.uml.SendSignalAction getBase() {
		return (org.eclipse.uml2.uml.SendSignalAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return new InputPin(this.getBase().getTarget());
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(
				target == null ? null : ((InputPin) target).getBase());
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return new Signal(this.getBase().getSignal());
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(
				signal == null ? null : ((Signal) signal).getBase());
	}

}
