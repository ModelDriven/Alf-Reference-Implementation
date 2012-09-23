package org.modeldriven.uml.alf.fuml;


public class LiteralNull extends LiteralSpecification implements
		org.modeldriven.alf.uml.LiteralNull {
	public LiteralNull() {
		this(new fUML.Syntax.Classes.Kernel.LiteralNull());
	}

	public LiteralNull(fUML.Syntax.Classes.Kernel.LiteralNull base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralNull getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralNull) this.base;
	}

}
