package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.MessageEvent;
import org.modeldriven.uml.fuml.Signal;

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
