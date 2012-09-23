package org.modeldriven.uml;

import java.util.List;

public interface Package extends Namespace, PackageableElement {
	public List<PackageableElement> getPackagedElement();

	public void addPackagedElement(PackageableElement packagedElement);

	public List<Type> getOwnedType();

	public List<Package> getNestedPackage();

	public Package getNestingPackage();

    public List<NamedElement> visibleMembers();
}
