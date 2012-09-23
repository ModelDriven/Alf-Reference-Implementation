package org.modeldriven.uml.alf.fuml;


public class PackageableElement extends NamedElement implements
		org.modeldriven.alf.uml.PackageableElement {

	public PackageableElement(fUML.Syntax.Classes.Kernel.PackageableElement base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.PackageableElement getBase() {
		return (fUML.Syntax.Classes.Kernel.PackageableElement) this.base;
	}

}
