package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.LiteralSpecification;

public class LiteralString extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralString {
	public LiteralString() {
		this(new fUML.Syntax.Classes.Kernel.LiteralString());
	}

	public LiteralString(fUML.Syntax.Classes.Kernel.LiteralString base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralString getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralString) this.base;
	}

	public String getValue() {
		return this.getBase().value;
	}

	public void setValue(String value) {
		this.getBase().setValue(value);
	}

}
