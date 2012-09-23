package org.modeldriven.uml.alf.fuml;


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
