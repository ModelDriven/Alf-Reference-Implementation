package org.modeldriven.alf.uml;

import java.util.List;

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
