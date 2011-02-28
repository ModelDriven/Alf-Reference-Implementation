package org.omg.uml;

import java.util.ArrayList;

public class Package extends Namespace {
    
    public ArrayList<PackageableElement> visibleMembers() {
        return new ArrayList<PackageableElement>();
    }

}
