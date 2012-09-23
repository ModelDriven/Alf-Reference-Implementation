package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.NamedElement;

public class PackageableElement extends NamedElement implements
		org.modeldriven.alf.uml.PackageableElement {

	public PackageableElement(fUML.Syntax.Classes.Kernel.PackageableElement base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.PackageableElement getBase() {
		return (fUML.Syntax.Classes.Kernel.PackageableElement) this.base;
	}

}
