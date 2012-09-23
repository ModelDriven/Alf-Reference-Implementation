package org.modeldriven.uml.alf.fuml;


public class EnumerationLiteral extends InstanceSpecification implements
		org.modeldriven.alf.uml.EnumerationLiteral {
	public EnumerationLiteral() {
		this(new fUML.Syntax.Classes.Kernel.EnumerationLiteral());
	}

	public EnumerationLiteral(fUML.Syntax.Classes.Kernel.EnumerationLiteral base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.EnumerationLiteral getBase() {
		return (fUML.Syntax.Classes.Kernel.EnumerationLiteral) this.base;
	}

	public org.modeldriven.alf.uml.Enumeration getEnumeration() {
		return new Enumeration(this.getBase().enumeration);
	}

}
