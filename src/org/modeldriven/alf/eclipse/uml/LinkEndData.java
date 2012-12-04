package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LinkEndData extends Element implements
		org.modeldriven.alf.uml.LinkEndData {
	public LinkEndData() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createLinkEndData());
	}

	public LinkEndData(org.eclipse.uml2.uml.LinkEndData base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LinkEndData getBase() {
		return (org.eclipse.uml2.uml.LinkEndData) this.base;
	}

	public org.modeldriven.alf.uml.InputPin getValue() {
		return new InputPin(this.getBase().getValue());
	}

	public void setValue(org.modeldriven.alf.uml.InputPin value) {
		this.getBase().setValue(
				value == null ? null : ((InputPin) value).getBase());
	}

	public org.modeldriven.alf.uml.Property getEnd() {
		return new Property(this.getBase().getEnd());
	}

	public void setEnd(org.modeldriven.alf.uml.Property end) {
		this.getBase().setEnd(end == null ? null : ((Property) end).getBase());
	}

}
