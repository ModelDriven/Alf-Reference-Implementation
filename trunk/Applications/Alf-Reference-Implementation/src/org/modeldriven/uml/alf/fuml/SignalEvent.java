package org.modeldriven.uml.alf.fuml;


public class SignalEvent extends MessageEvent implements
		org.modeldriven.alf.uml.SignalEvent {
	public SignalEvent() {
		this(new fUML.Syntax.CommonBehaviors.Communications.SignalEvent());
	}

	public SignalEvent(
			fUML.Syntax.CommonBehaviors.Communications.SignalEvent base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.SignalEvent getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.SignalEvent) this.base;
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return new Signal(this.getBase().signal);
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(((Signal) signal).getBase());
	}

}
