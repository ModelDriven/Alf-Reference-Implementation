package org.modeldriven.uml.fuml;

import UMLPrimitiveTypes.UnlimitedNatural;

import java.util.List;
import java.util.ArrayList;

import org.modeldriven.uml.fuml.Element;
import org.modeldriven.uml.fuml.Namespace;
import org.modeldriven.uml.fuml.PackageableElement;

public class ElementImport extends Element implements
		org.modeldriven.alf.uml.ElementImport {
	public ElementImport() {
		this(new fUML.Syntax.Classes.Kernel.ElementImport());
	}

	public ElementImport(fUML.Syntax.Classes.Kernel.ElementImport base) {
		super(base);
	}

	public fUML.Syntax.Classes.Kernel.ElementImport getBase() {
		return (fUML.Syntax.Classes.Kernel.ElementImport) this.base;
	}

	public String getVisibility() {
		return this.getBase().visibility.toString();
	}

	public void setVisibility(String visibility) {
		this.getBase().setVisibility(
				fUML.Syntax.Classes.Kernel.VisibilityKind.valueOf(visibility));
	}

	public String getAlias() {
		return this.getBase().alias;
	}

	public void setAlias(String alias) {
		this.getBase().setAlias(alias);
	}

	public org.modeldriven.alf.uml.PackageableElement getImportedElement() {
		return new PackageableElement(this.getBase().importedElement);
	}

	public void setImportedElement(
			org.modeldriven.alf.uml.PackageableElement importedElement) {
		this.getBase().setImportedElement(
				((PackageableElement) importedElement).getBase());
	}

	public org.modeldriven.alf.uml.Namespace getImportingNamespace() {
		return new Namespace(this.getBase().importingNamespace);
	}

}
