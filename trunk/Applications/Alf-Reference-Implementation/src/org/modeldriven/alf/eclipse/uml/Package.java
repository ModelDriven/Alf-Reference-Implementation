package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class Package extends Namespace implements
		org.modeldriven.alf.uml.Package {
	public Package() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createPackage());
	}

	public Package(org.eclipse.uml2.uml.Package base) {
		super(base);
	}

	public org.eclipse.uml2.uml.Package getBase() {
		return (org.eclipse.uml2.uml.Package) this.base;
	}

	public List< org.modeldriven.alf.uml.PackageableElement> getPackagedElement
() {
		List< org.modeldriven.alf.uml.PackageableElement> list = new ArrayList< org.modeldriven.alf.uml.PackageableElement>();
		for (org.eclipse.uml2.uml.PackageableElement
 element: this.getBase().getPackagedElement
s()) {
			list.add( new PackageableElement(element)
);
		}
		return list;
	}

	public void addPackagedElement
( org.modeldriven.alf.uml.PackageableElement packagedElement) {
		this.getBase().getPackagedElement
s.add( packagedElement == null? null: ((PackageableElement)packagedElement).getBase()
);
	}

	public List< org.modeldriven.alf.uml.Type> getOwnedType
() {
		List< org.modeldriven.alf.uml.Type> list = new ArrayList< org.modeldriven.alf.uml.Type>();
		for (org.eclipse.uml2.uml.Type
 element: this.getBase().getOwnedType
s()) {
			list.add( new Type(element)
);
		}
		return list;
	}

	public List< org.modeldriven.alf.uml.Package> getNestedPackage
() {
		List< org.modeldriven.alf.uml.Package> list = new ArrayList< org.modeldriven.alf.uml.Package>();
		for (org.eclipse.uml2.uml.Package
 element: this.getBase().getNestedPackage
s()) {
			list.add( new Package(element)
);
		}
		return list;
	}

	public org.modeldriven.alf.uml.Package getNestingPackage() {
		return new Package(this.getBase().getNestingPackage());
	}

	public String getURI() {
		return this.getBase().getURI();
	}

	public void setURI(String URI) {
		this.getBase().setURI(URI);
	}

	public String getVisibility() {
		return this.getBase().getVisibility().toString();
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(
				fUML.Syntax.Classes.Kernel.VisibilityKind.valueOf(visibility));
	}

}
