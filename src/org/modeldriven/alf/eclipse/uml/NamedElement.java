package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class NamedElement extends Element implements
		org.modeldriven.alf.uml.NamedElement {

	public NamedElement(org.eclipse.uml2.uml.NamedElement base) {
		super(base);
	}

	public org.eclipse.uml2.uml.NamedElement getBase() {
		return (org.eclipse.uml2.uml.NamedElement) this.base;
	}

	public String getName() {
		return this.getBase().getName();
	}

	public void setName(String name) {
		this.getBase().setName(name);
	}

	public String getVisibility() {
		return this.getBase().getVisibility().toString();
	}

	public void setVisibility( String
 visibility) {
		this.getBase().setVisibility( org.eclipse.uml2.uml.org.eclipse.uml2.uml.internal.impl.EnumerationImpl@130fe4e (name: VisibilityKind, visibility: <unset>) (isLeaf: false, isAbstract: false).get(visibility)
);
	}

	public String getQualifiedName() {
		return this.getBase().getQualifiedName();
	}

	public org.modeldriven.alf.uml.Namespace getNamespace() {
		return new Namespace(this.getBase().getNamespace());
	}

}
