package org.omg.uml;

import java.util.ArrayList;
import java.util.List;

public class Classifier extends NamedElement {
    
    public boolean isTemplate() {
        return false;
    }

    public List<NamedElement> inheritableMembers() {
        return new ArrayList<NamedElement>();
    }

    public boolean isAbstract() {
        return false;
    }

}
