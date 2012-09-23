package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.Element;
import org.modeldriven.uml.Namespace;
import org.modeldriven.uml.Package;

public interface PackageImport extends Element {
	public String getVisibility();

	public void setVisibility(String visibility);

	public Namespace getImportingNamespace();

	public Package getImportedPackage();

	public void setImportedPackage(Package importedPackage);
}
