package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class LiteralString extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralString {
	public LiteralString() {
		this(UMLFactory.eINSTANCE.createLiteralString());
	}

	public LiteralString(fUML.Syntax.Classes.Kernel.LiteralString base) {
		super(base);
	}

	public org.eclipse.uml2.uml.LiteralString getBase() {
		return (org.eclipse.uml2.uml.LiteralString) this.base;
	}

	public String getValue() {
		return this.getBase().getValue();
	}

	public void setValue(String value) {
		this.getBase().setValue(value);
	}

}
