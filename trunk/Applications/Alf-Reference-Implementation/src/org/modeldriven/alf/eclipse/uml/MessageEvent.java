package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class MessageEvent extends Event implements
		org.modeldriven.alf.uml.MessageEvent {

	public MessageEvent(org.eclipse.uml2.uml.MessageEvent base) {
		super(base);
	}

	public org.eclipse.uml2.uml.MessageEvent getBase() {
		return (org.eclipse.uml2.uml.MessageEvent) this.base;
	}

}
