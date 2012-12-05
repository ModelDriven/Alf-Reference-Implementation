package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class TypedElement extends NamedElement implements
		org.modeldriven.alf.uml.TypedElement {
	public TypedElement(org.eclipse.uml2.uml.TypedElement base) {
		super(base);
	}

	public org.eclipse.uml2.uml.TypedElement getBase() {
		return (org.eclipse.uml2.uml.TypedElement) this.base;
	}

	public org.modeldriven.alf.uml.Type getType() {
		return (org.modeldriven.alf.uml.Type) wrap(this.getBase().getType());
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(type == null ? null : ((Type) type).getBase());
	}

}
