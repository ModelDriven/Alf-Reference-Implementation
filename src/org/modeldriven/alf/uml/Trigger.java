package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.Event;
import org.modeldriven.uml.NamedElement;

public interface Trigger extends NamedElement {
	public Event getEvent();

	public void setEvent(Event event);
}
