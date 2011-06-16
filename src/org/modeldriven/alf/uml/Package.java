package org.modeldriven.alf.uml;

import java.util.Set;

public interface Package extends Namespace, PackageableElement {
    
    public Set<PackageableElement> visibleMembers();

}
