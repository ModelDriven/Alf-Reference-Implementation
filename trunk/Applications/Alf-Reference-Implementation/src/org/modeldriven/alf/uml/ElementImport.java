package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.Element;
import org.modeldriven.uml.Namespace;
import org.modeldriven.uml.PackageableElement;

public interface ElementImport extends Element {
	public String getVisibility();

	public void setVisibility(String visibility);

	public String getAlias();

	public void setAlias(String alias);

	public PackageableElement getImportedElement();

	public void setImportedElement(PackageableElement importedElement);

	public Namespace getImportingNamespace();
}
