
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.units.*;
import org.modeldriven.alf.uml.Profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The definition of a package, all of whose members must be packageable
 * elements.
 **/

public class PackageDefinitionImpl extends NamespaceDefinitionImpl {

	private Collection<Profile> appliedProfile = null; // DERIVED

	public PackageDefinitionImpl(PackageDefinition self) {
		super(self);
	}

	public PackageDefinition getSelf() {
		return (PackageDefinition) this.self;
	}

	public Collection<Profile> getAppliedProfile() {
		if (this.appliedProfile == null) {
			this.setAppliedProfile(this.deriveAppliedProfile());
		}
		return this.appliedProfile;
	}

	public void setAppliedProfile(Collection<Profile> appliedProfile) {
		this.appliedProfile = appliedProfile;
	}

	public void addAppliedProfile(Profile appliedProfile) {
		this.appliedProfile.add(appliedProfile);
	}

	/**
	 * The applied profiles of a package definition are the profiles listed in
	 * any @apply annotations on the package.
	 **/
	protected Collection<Profile> deriveAppliedProfile() {
	    // TODO: Handle applied profiles.
		return new ArrayList<Profile>();
	}
	
	/*
	 * Derivations
	 */

	public boolean packageDefinitionAppliedProfileDerivation() {
		this.getSelf().getAppliedProfile();
		return true;
	}
	
	/*
	 * Helper Methods
	 */

	/**
	 * In addition to the annotations allowed on any namespace definition, a
	 * package definition allows @apply annotations plus any stereotype whose
	 * metaclass is consistent with Package.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    // TODO: Allow profile and stereotype applications on packages.
		return super.annotationAllowed(annotation) ||
		       // The following is a temporary special check until true
		       // stereotype resolution is implementation.
		       annotation.getStereotypeName().getImpl().equals("ModelLibrary");
	} // annotationAllowed

	/**
	 * Returns true if the namespace definition associated with the given unit
	 * definition is a package definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return unit.getDefinition() instanceof PackageDefinition;
	} // matchForStub

	/**
	 * Return true if the given member is either a PackageDefinition or an
	 * imported member whose referent is a PackageDefinition or a Package.
	 **/
	public Boolean isSameKindAs(Member member) {
	    return member.getImpl().getReferent().getImpl().isPackage();
	} // isSameKindAs
	
	// Package-only members are limited to visibility within this package 
	// definition.
    protected boolean allowPackageOnly() {
        return false;
    }

    public List<Member> getPublicMembers() {
        ArrayList<Member> publicMembers = new ArrayList<Member>();
        for (Member member: this.getSelf().getMember()) {
            if (member.getImpl().isPublic()) {
                publicMembers.add(member);
            }
        }
        return publicMembers;
    }
    
    @Override
    protected void bindTo(SyntaxElement base,
            List<ElementReference> templateParameters, 
            List<ElementReference> templateArguments) {
        super.bindTo(base, templateParameters, templateArguments);
        if (base instanceof PackageDefinition) {
            this.getSelf().setAppliedProfile
                (((PackageDefinition)base).getAppliedProfile());
        }
    }

} // PackageDefinitionImpl
