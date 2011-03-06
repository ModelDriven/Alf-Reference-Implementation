package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.units.impl.MissingMemberImpl;

public class MissingMember extends Member {
    
    public MissingMember(String name) {
        this.impl = new MissingMemberImpl(this);
        this.setName(name);
        this.setVisibility("private");
    }
    
}
