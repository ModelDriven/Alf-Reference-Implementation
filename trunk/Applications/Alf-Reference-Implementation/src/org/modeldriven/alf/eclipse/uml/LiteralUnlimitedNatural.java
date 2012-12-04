package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LiteralUnlimitedNatural extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralUnlimitedNatural {
	public LiteralUnlimitedNatural() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE
				.createLiteralUnlimitedNatural());
	}

	public LiteralUnlimitedNatural(
			org.eclipse.uml2.uml.LiteralUnlimitedNatural base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LiteralUnlimitedNatural getBase() {
		return (org.eclipse.uml2.uml.LiteralUnlimitedNatural) this.base;
	}

	public int getValue() {
		return this.getBase().getValue();
	}

	public void setValue(int value) {
		this.getBase().setValue(value);
	}

}
