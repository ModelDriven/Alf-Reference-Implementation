package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LiteralBoolean extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralBoolean {
	public LiteralBoolean() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createLiteralBoolean());
	}

	public LiteralBoolean(org.eclipse.uml2.uml.LiteralBoolean base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LiteralBoolean getBase() {
		return (org.eclipse.uml2.uml.LiteralBoolean) this.base;
	}

	public boolean getValue() {
		return this.getBase().value();
	}

	public void setValue(boolean value) {
		this.getBase().setValue(value);
	}

}
