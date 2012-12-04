package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Trigger extends NamedElement implements
		org.modeldriven.alf.uml.Trigger {
	public Trigger() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createTrigger());
	}

	public Trigger(org.eclipse.uml2.uml.Trigger base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Trigger getBase() {
		return (org.eclipse.uml2.uml.Trigger) this.base;
	}

	public org.modeldriven.alf.uml.Event getEvent() {
		return new Event(this.getBase().getEvent());
	}

	public void setEvent(org.modeldriven.alf.uml.Event event) {
		this.getBase().setEvent(
				event == null ? null : ((Event) event).getBase());
	}

}
