package org.modeldriven.uml.alf.fuml;


public class LiteralBoolean extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralBoolean {
	public LiteralBoolean() {
		this(new fUML.Syntax.Classes.Kernel.LiteralBoolean());
	}

	public LiteralBoolean(fUML.Syntax.Classes.Kernel.LiteralBoolean base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralBoolean getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralBoolean) this.base;
	}

	public boolean getValue() {
		return this.getBase().value;
	}

	public void setValue(boolean value) {
		this.getBase().setValue(value);
	}

}
