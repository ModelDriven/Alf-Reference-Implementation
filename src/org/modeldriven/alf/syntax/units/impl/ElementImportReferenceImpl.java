
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

public class ElementImportReferenceImpl extends ImportReferenceImpl {

    private String alias = "";

	public ElementImportReferenceImpl(ElementImportReference self) {
		super(self);
	}

	public ElementImportReference getSelf() {
		return (ElementImportReference) this.self;
	}
	
    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
        if (alias != null && alias !="") {
            member.setName(alias);
        }
        return member;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof ElementImportReference || other instanceof ElementImportReferenceImpl) &&
            super.equals(other);
    }
    
} // ElementImportReferenceImpl
