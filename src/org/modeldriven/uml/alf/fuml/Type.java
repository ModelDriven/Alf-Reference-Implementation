package org.modeldriven.uml.alf.fuml;


public class Type extends PackageableElement implements
		org.modeldriven.alf.uml.Type {

	public Type(fUML.Syntax.Classes.Kernel.Type base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.Type getBase() {
		return (fUML.Syntax.Classes.Kernel.Type) this.base;
	}

	public org.modeldriven.alf.uml.Package getPackage() {
		return new Package(this.getBase().package_);
	}

}
