package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class PrimitiveType extends DataType implements
		org.modeldriven.alf.uml.PrimitiveType {
	public PrimitiveType() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createPrimitiveType());
	}

	public PrimitiveType(org.eclipse.uml2.uml.PrimitiveType base) {
		super(base);
	}

	public org.eclipse.uml2.uml.PrimitiveType getBase() {
		return (org.eclipse.uml2.uml.PrimitiveType) this.base;
	}

}
