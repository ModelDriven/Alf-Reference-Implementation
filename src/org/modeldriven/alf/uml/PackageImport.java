package org.modeldriven.alf.uml;


public interface PackageImport extends Element {
	public String getVisibility();

	public void setVisibility(String visibility);

	public Namespace getImportingNamespace();

	public Package getImportedPackage();

	public void setImportedPackage(Package importedPackage);
}
