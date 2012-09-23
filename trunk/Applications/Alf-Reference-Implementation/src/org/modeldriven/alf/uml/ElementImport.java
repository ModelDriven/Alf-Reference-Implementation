package org.modeldriven.alf.uml;


public interface ElementImport extends Element {
	public String getVisibility();

	public void setVisibility(String visibility);

	public String getAlias();

	public void setAlias(String alias);

	public PackageableElement getImportedElement();

	public void setImportedElement(PackageableElement importedElement);

	public Namespace getImportingNamespace();
}
