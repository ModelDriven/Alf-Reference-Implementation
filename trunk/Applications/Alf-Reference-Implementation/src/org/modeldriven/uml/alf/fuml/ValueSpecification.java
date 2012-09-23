package org.modeldriven.uml.alf.fuml;


public class ValueSpecification extends TypedElement implements
		org.modeldriven.alf.uml.ValueSpecification {

	public ValueSpecification(fUML.Syntax.Classes.Kernel.ValueSpecification base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.ValueSpecification getBase() {
		return (fUML.Syntax.Classes.Kernel.ValueSpecification) this.base;
	}

}
