package org.modeldriven.uml.alf.fuml;


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
