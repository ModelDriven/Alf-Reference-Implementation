package org.modeldriven.uml.alf.fuml;


public class PrimitiveType extends DataType implements
		org.modeldriven.alf.uml.PrimitiveType {
	public PrimitiveType() {
		this(new fUML.Syntax.Classes.Kernel.PrimitiveType());
	}

	public PrimitiveType(fUML.Syntax.Classes.Kernel.PrimitiveType base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.PrimitiveType getBase() {
		return (fUML.Syntax.Classes.Kernel.PrimitiveType) this.base;
	}

}
