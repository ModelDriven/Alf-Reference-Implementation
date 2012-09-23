package org.modeldriven.uml.alf.fuml;


public class Event extends PackageableElement implements
		org.modeldriven.alf.uml.Event {

	public Event(fUML.Syntax.CommonBehaviors.Communications.Event base) {
		super(base);
	}

	public fUML.Syntax.CommonBehaviors.Communications.Event getBase() {
		return (fUML.Syntax.CommonBehaviors.Communications.Event) this.base;
	}

}
