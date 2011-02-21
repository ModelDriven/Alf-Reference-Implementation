
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
import org.omg.uml.Package_;
import org.omg.uml.PackageableElement;

import java.util.ArrayList;

/**
 * An import reference to a package all of whose public members are to be
 * imported.
 **/

public class PackageImportReferenceImpl extends
		org.modeldriven.alf.syntax.units.impl.ImportReferenceImpl {

	public PackageImportReferenceImpl(PackageImportReference self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.PackageImportReference getSelf() {
		return (PackageImportReference) this.self;
	}

	/**
	 * The referent of a package import must be a package.
	 **/
	public boolean packageImportReferenceReferent() {
	    ElementReference referent = this.getSelf().getReferent();
		if (referent == null) {
		    return false;
		} else {
		    SyntaxElement element = referent.getImpl().getAlf();
		    return element != null && element instanceof PackageDefinition ||
		        referent.getImpl().getUml() instanceof Package_;
		}
	}

    /*
     * Helper Methods
     */

    @Override
    public ArrayList<Member> getImportedMembers() {
        ArrayList<Member> members = new ArrayList<Member>();
        if (this.packageImportReferenceReferent()) {
            ElementReference referent = this.getSelf().getReferent();
            PackageDefinition packageDefinition = (PackageDefinition)referent.getImpl().getAlf();
            if (packageDefinition != null) {
                for (Member member: packageDefinition.getImpl().getPublicMembers()) {
                    members.add(makeImportedMember(member.getImpl().getReferent()));
                }
            } else {
                for (PackageableElement element: ((Package_)referent.getImpl().getUml()).visibleMembers()) {
                    ExternalElementReference memberReference = new ExternalElementReference();
                    memberReference.setElement(element);
                    members.add(makeImportedMember(memberReference));
                }
            }
        }
        return members;
    }

} // PackageImportReferenceImpl
