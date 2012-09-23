package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.uml.ElementImport;
import org.modeldriven.uml.NamedElement;
import org.modeldriven.uml.PackageImport;
import org.modeldriven.uml.PackageableElement;

public interface Namespace extends NamedElement {
	public List<NamedElement> getMember();

	public List<NamedElement> getOwnedMember();

	public List<ElementImport> getElementImport();

	public void addElementImport(ElementImport elementImport);

	public List<PackageImport> getPackageImport();

	public void addPackageImport(PackageImport packageImport);

	public List<PackageableElement> getImportedMember();
	
    public List<String> getNamesOfMember(NamedElement member);
}
