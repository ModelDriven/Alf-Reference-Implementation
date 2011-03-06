package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.units.Member;
import org.modeldriven.alf.syntax.units.StereotypeAnnotation;

public class MissingMemberImpl extends MemberImpl {

    public MissingMemberImpl(Member self) {
        super(self);
    }

    @Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
        return false;
    }

    @Override
    public Boolean isSameKindAs(Member member) {
        return false;
    }

}
