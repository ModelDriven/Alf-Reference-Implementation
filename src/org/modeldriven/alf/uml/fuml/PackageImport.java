package org.modeldriven.alf.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Element;
import org.modeldriven.uml.fuml.Namespace;
import org.modeldriven.uml.fuml.Package;

public class PackageImport extends Element implements
		org.modeldriven.alf.uml.PackageImport {
	public PackageImport() {
		this(new fUML.Syntax.Classes.Kernel.PackageImport());
	}

	public PackageImport(fUML.Syntax.Classes.Kernel.PackageImport base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.PackageImport getBase() {
		return (fUML.Syntax.Classes.Kernel.PackageImport) this.base;
	}

	public String getVisibility() {
		return this.getBase().visibility.toString();
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(
				fUML.Syntax.Classes.Kernel.VisibilityKind.valueOf(visibility));
	}

	public org.modeldriven.alf.uml.Namespace getImportingNamespace() {
		return new Namespace(this.getBase().importingNamespace);
	}

	public org.modeldriven.alf.uml.Package getImportedPackage() {
		return new Package(this.getBase().importedPackage);
	}

	public void setImportedPackage(org.modeldriven.alf.uml.Package importedPackage) {
		this.getBase()
				.setImportedPackage(((Package) importedPackage).getBase());
	}

}
