package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.LiteralSpecification;

public class LiteralUnlimitedNatural extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralUnlimitedNatural {
	public LiteralUnlimitedNatural() {
		this(new fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural());
	}

	public LiteralUnlimitedNatural(
			fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralUnlimitedNatural) this.base;
	}

	public int getValue() {
		return this.getBase().value.naturalValue;
	}

	public void setValue(int value) {
		this.getBase().setValue(value);
	}

}
