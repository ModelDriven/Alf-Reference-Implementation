package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class ObjectNode extends ActivityNode implements
		org.modeldriven.alf.uml.ObjectNode {

	public ObjectNode(org.eclipse.uml2.uml.ObjectNode base) {
		super(base);
	}

	public org.eclipse.uml2.uml.ObjectNode getBase() {
		return (org.eclipse.uml2.uml.ObjectNode) this.base;
	}

	public org.modeldriven.alf.uml.Type getType() {
		return new Type(this.getBase().getType());
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(type == null ? null : ((Type) type).getBase());
	}

}
