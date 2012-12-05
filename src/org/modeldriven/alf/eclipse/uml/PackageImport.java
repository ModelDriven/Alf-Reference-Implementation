package org.modeldriven.alf.eclipse.uml;

import java.util.List;
import java.util.ArrayList;

public class PackageImport extends Element implements
		org.modeldriven.alf.uml.PackageImport {
	public PackageImport() {
		this(org.eclipse.uml2.uml.UMLFactory.eINSTANCE.createPackageImport());
	}

	public PackageImport(org.eclipse.uml2.uml.PackageImport base) {
		super(base);
	}

	public org.eclipse.uml2.uml.PackageImport getBase() {
		return (org.eclipse.uml2.uml.PackageImport) this.base;
	}

	public String getVisibility() {
		return this.getBase().getVisibility().toString();
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(
				org.eclipse.uml2.uml.VisibilityKind.get(visibility));
	}

	public org.modeldriven.alf.uml.Namespace getImportingNamespace() {
		return (org.modeldriven.alf.uml.Namespace) wrap(this.getBase()
				.getImportingNamespace());
	}

	public org.modeldriven.alf.uml.Package getImportedPackage() {
		return (org.modeldriven.alf.uml.Package) wrap(this.getBase()
				.getImportedPackage());
	}

	public void setImportedPackage(
			org.modeldriven.alf.uml.Package importedPackage) {
		this.getBase().setImportedPackage(
				importedPackage == null ? null : ((Package) importedPackage)
						.getBase());
	}

}
