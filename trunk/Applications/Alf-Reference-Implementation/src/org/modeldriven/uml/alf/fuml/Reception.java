package org.modeldriven.uml.alf.fuml;


public class Reception extends BehavioralFeature implements
		org.modeldriven.alf.uml.Reception {
	public Reception() {
		this(new fUML.Syntax.CommonBehaviors.Communications.Reception());
	}

	public Reception(fUML.Syntax.CommonBehaviors.Communications.Reception base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.Reception getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.Reception) this.base;
	}

	public org.modeldriven.alf.uml.Signal getSignal() {
		return new Signal(this.getBase().signal);
	}

	public void setSignal(org.modeldriven.alf.uml.Signal signal) {
		this.getBase().setSignal(((Signal) signal).getBase());
	}

}
