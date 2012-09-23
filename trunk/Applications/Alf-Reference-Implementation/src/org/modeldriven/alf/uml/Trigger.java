package org.modeldriven.alf.uml;


public interface Trigger extends NamedElement {
	public Event getEvent();

	public void setEvent(Event event);
}
