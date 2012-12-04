package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LiteralNull extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralNull {
	public LiteralNull() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createLiteralNull());
	}

	public LiteralNull(org.eclipse.uml2.uml.LiteralNull base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LiteralNull getBase() {
		return (org.eclipse.uml2.uml.LiteralNull) this.base;
	}

}
