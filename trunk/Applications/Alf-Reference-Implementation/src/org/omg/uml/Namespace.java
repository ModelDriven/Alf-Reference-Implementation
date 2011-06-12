package org.omg.uml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Namespace extends NamedElement {

    public List<NamedElement> getOwnedMember() {
        return new ArrayList<NamedElement>();
    }

    public List<NamedElement> getMember() {
        return new ArrayList<NamedElement>();
    }

    public Set<String> getNamesOfMember(NamedElement element) {
        Set<String> names = new HashSet<String>();
        names.add(element.getName());
        return names;
    }

}
