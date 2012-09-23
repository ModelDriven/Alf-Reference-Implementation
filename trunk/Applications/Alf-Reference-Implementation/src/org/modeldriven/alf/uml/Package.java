package org.modeldriven.alf.uml;

import java.util.List;

import org.modeldriven.alf.uml.NamedElement;
import org.modeldriven.alf.uml.Namespace;
import org.modeldriven.alf.uml.Package;
import org.modeldriven.alf.uml.PackageableElement;
import org.modeldriven.alf.uml.Type;

public interface Package extends Namespace, PackageableElement {
	public List<PackageableElement> getPackagedElement();

	public void addPackagedElement(PackageableElement packagedElement);

	public List<Type> getOwnedType();

	public List<Package> getNestedPackage();

	public Package getNestingPackage();

    public List<NamedElement> visibleMembers();
}
