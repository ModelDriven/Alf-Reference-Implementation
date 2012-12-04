package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LiteralInteger extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralInteger {
	public LiteralInteger() {
		this(UMLFactory.eINSTANCE.createLiteralInteger());
	}

	public LiteralInteger(fUML.Syntax.Classes.Kernel.LiteralInteger base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LiteralInteger getBase() {
		return (org.eclipse.uml2.uml.LiteralInteger) this.base;
	}

	public int getValue() {
		return this.getBase().getValue();
	}

	public void setValue(int value) {
		this.getBase().setValue(value);
	}

}
