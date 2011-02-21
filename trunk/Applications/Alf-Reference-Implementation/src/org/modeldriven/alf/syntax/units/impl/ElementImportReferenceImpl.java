
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An import reference to a single element to be imported into a unit.
 **/

public class ElementImportReferenceImpl extends
		org.modeldriven.alf.syntax.units.impl.ImportReferenceImpl {

	public ElementImportReferenceImpl(ElementImportReference self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.ElementImportReference getSelf() {
		return (ElementImportReference) this.self;
	}
	
	/*
	 * Helper Methods
	 */

    @Override
    public ArrayList<Member> getImportedMembers() {
        ArrayList<Member> members = new ArrayList<Member>();
        ElementReference referent = this.getSelf().getReferent();
        if (referent != null) {
            members.add(this.makeImportedMember(referent));
        }
        return members;
    }
    
    @Override
    protected ImportedMember makeImportedMember(ElementReference referent) {
        ImportedMember member = super.makeImportedMember(referent);
        String alias = this.getSelf().getAlias();
        if (alias != null) {
            member.setName(alias);
        }
        return member;
    }

} // ElementImportReferenceImpl
