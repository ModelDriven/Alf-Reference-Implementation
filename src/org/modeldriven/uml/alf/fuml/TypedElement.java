package org.modeldriven.uml.alf.fuml;


public class TypedElement extends NamedElement implements
		org.modeldriven.alf.uml.TypedElement {
	public TypedElement() {
		this(new fUML.Syntax.Classes.Kernel.TypedElement());
	}

	public TypedElement(fUML.Syntax.Classes.Kernel.TypedElement base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.TypedElement getBase() {
		return (fUML.Syntax.Classes.Kernel.TypedElement) this.base;
	}

	public org.modeldriven.alf.uml.Type getType() {
		return new Type(this.getBase().type);
	}

	public void setType(org.modeldriven.alf.uml.Type type) {
		this.getBase().setType(((Type) type).getBase());
	}

}
