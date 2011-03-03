package org.omg.uml;

import java.util.ArrayList;
import java.util.List;

public class Namespace extends NamedElement {

    public List<NamedElement> getOwnedMember() {
        return new ArrayList<NamedElement>();
    }

    public List<NamedElement> getMember() {
        return new ArrayList<NamedElement>();
    }

}
