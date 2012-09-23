package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.LiteralSpecification;

public class LiteralInteger extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralInteger {
	public LiteralInteger() {
		this(new fUML.Syntax.Classes.Kernel.LiteralInteger());
	}

	public LiteralInteger(fUML.Syntax.Classes.Kernel.LiteralInteger base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralInteger getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralInteger) this.base;
	}

	public int getValue() {
		return this.getBase().value;
	}

	public void setValue(int value) {
		this.getBase().setValue(value);
	}

}
