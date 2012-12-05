package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Type extends PackageableElement implements
		org.modeldriven.alf.uml.Type {

	public Type(org.eclipse.uml2.uml.Type base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Type getBase() {
		return (org.eclipse.uml2.uml.Type) this.base;
	}

	public org.modeldriven.alf.uml.Package getPackage_() {
		return wrap(this.getBase().getPackage_());
	}

}
