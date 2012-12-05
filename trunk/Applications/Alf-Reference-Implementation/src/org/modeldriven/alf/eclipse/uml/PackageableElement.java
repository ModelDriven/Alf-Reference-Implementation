package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class PackageableElement extends NamedElement implements
		org.modeldriven.alf.uml.PackageableElement {

	public PackageableElement(org.eclipse.uml2.uml.PackageableElement base) {
		super(base);
	}

	public org.eclipse.uml2.uml.PackageableElement getBase() {
		return (org.eclipse.uml2.uml.PackageableElement) this.base;
	}

	public String getVisibility() {
		return this.getBase().getVisibility().toString();
	}

	public void setVisibility( String
 visibility) {
		this.getBase().setVisibility( org.eclipse.uml2.uml.org.eclipse.uml2.uml.internal.impl.EnumerationImpl@aff6f0 (name: VisibilityKind, visibility: <unset>) (isLeaf: false, isAbstract: false).get(visibility)
);
	}

}
