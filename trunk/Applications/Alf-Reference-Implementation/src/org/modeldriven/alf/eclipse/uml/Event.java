package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Event extends PackageableElement implements
		org.modeldriven.alf.uml.Event {

	public Event(fUML.Syntax.CommonBehaviors.Communications.Event base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Event getBase() {
		return (org.eclipse.uml2.uml.Event) this.base;
	}

}
