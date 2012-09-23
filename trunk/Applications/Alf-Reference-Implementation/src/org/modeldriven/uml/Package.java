package org.modeldriven.uml;

import java.util.List;

import org.modeldriven.uml.NamedElement;
import org.modeldriven.uml.Namespace;
import org.modeldriven.uml.Package;
import org.modeldriven.uml.PackageableElement;
import org.modeldriven.uml.Type;

public interface Package extends Namespace, PackageableElement {
	public List<PackageableElement> getPackagedElement();

	public void addPackagedElement(PackageableElement packagedElement);

	public List<Type> getOwnedType();

	public List<Package> getNestedPackage();

	public Package getNestingPackage();

    public List<NamedElement> visibleMembers();
}
