package org.modeldriven.uml.alf.fuml;


public class LiteralSpecification extends ValueSpecification implements
		org.modeldriven.alf.uml.LiteralSpecification {

	public LiteralSpecification(
			fUML.Syntax.Classes.Kernel.LiteralSpecification base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.LiteralSpecification getBase() {
		return (fUML.Syntax.Classes.Kernel.LiteralSpecification) this.base;
	}

}
