package org.modeldriven.uml.alf.fuml;


public class Trigger extends NamedElement implements
		org.modeldriven.alf.uml.Trigger {
	public Trigger() {
		this(new fUML.Syntax.CommonBehaviors.Communications.Trigger());
	}

	public Trigger(fUML.Syntax.CommonBehaviors.Communications.Trigger base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.Trigger getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.Trigger) this.base;
	}

	public org.modeldriven.alf.uml.Event getEvent() {
		return new Event(this.getBase().event);
	}

	public void setEvent(org.modeldriven.alf.uml.Event event) {
		this.getBase().setEvent(((Event) event).getBase());
	}

}
