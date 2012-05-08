
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

/**
 * An import reference to a package all of whose public members are to be
 * imported.
 **/

public class PackageImportReferenceImpl extends ImportReferenceImpl {

	public PackageImportReferenceImpl(PackageImportReference self) {
		super(self);
	}

	public PackageImportReference getSelf() {
		return (PackageImportReference) this.self;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The referent of a package import must be a package.
	 **/
	public boolean packageImportReferenceReferent() {
	    ElementReference referent = this.getSelf().getReferent();
	    return referent != null && referent.getImpl().isPackage();
	}

    /*
     * Helper Methods
     */

    @Override
    public ArrayList<Member> getImportedMembers() {
        ArrayList<Member> members = new ArrayList<Member>();
        ElementReference referent = this.getSelf().getReferent();
        if (referent != null) {
            for (Member member: referent.getImpl().getPublicMembers()) {
                ImportedMember importedMember = ImportedMemberImpl.makeImportedMember(member);
                importedMember.setVisibility(this.getSelf().getVisibility());
                members.add(importedMember);
            }
        }
        return members;
    }
    
    @Override
    public boolean equals(Object other) {
        return (other instanceof PackageImportReference || other instanceof PackageImportReferenceImpl) &&
            super.equals(other);
    }

} // PackageImportReferenceImpl
