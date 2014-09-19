package org.modeldriven.alf.eclipse.uml;

import org.eclipse.uml2.uml.UMLFactory;

public class InterfaceRealization extends Dependency {

	public InterfaceRealization() {
		this(UMLFactory.eINSTANCE.createInterfaceRealization());
	}

	public InterfaceRealization(org.eclipse.uml2.uml.Dependency base) {
		super(base);
	}

	@Override
	public org.eclipse.uml2.uml.InterfaceRealization getBase() {
		return (org.eclipse.uml2.uml.InterfaceRealization)this.base;
	}

}
