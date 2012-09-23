package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.ElementImport;
import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.PackageImport;
import org.modeldriven.alf.uml.PackageableElement;

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
