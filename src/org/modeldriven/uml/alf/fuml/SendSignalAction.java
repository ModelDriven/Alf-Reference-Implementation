package org.modeldriven.uml.alf.fuml;


public class SendSignalAction extends InvocationAction implements
		org.modeldriven.alf.uml.SendSignalAction {
	public SendSignalAction() {
		this(new fUML.Syntax.Actions.BasicActions.SendSignalAction());
	}

	public SendSignalAction(
			fUML.Syntax.Actions.BasicActions.SendSignalAction base) {
		super(base);
	}

	public fUML.Syntax.Actions.BasicActions.SendSignalAction getBase() {
		return (fUML.Syntax.Actions.BasicActions.SendSignalAction) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getTarget() {
		return new InputPin(this.getBase().target);
	}

	public void setTarget(org.modeldriven.alf.uml.InputPin target) {
		this.getBase().setTarget(((InputPin) target).getBase());
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return new Signal(this.getBase().signal);
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(((Signal) signal).getBase());
	}

}
