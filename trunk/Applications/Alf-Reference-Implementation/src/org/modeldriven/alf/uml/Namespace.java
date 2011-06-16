package org.modeldriven.alf.uml;

import java.util.List;
import java.util.Set;

public interface Namespace extends NamedElement {

    public List<NamedElement> getOwnedMember();
    public List<NamedElement> getMember();

    public Set<String> getNamesOfMember(NamedElement element);

}
