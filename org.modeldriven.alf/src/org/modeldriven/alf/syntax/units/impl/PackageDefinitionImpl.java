/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.common.SyntaxElement;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;
import org.modeldriven.alf.syntax.expressions.QualifiedName;
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

    // NOTE: A collection of element references is used here to allow
    // for the (non-standard) possibility of profiles defined using Alf.
	private Collection<ElementReference> appliedProfile = null; // DERIVED
	
	private Collection<ElementReference> allAppliedProfiles = null;

	public PackageDefinitionImpl(PackageDefinition self) {
		super(self);
	}

	public PackageDefinition getSelf() {
		return (PackageDefinition) this.self;
	}

	public Collection<Profile> getAppliedProfile() {
		Collection<Profile> profiles = new ArrayList<Profile>();
		for (ElementReference reference: this.getAppliedProfileReference()) {
		    Profile profile = (Profile)reference.getImpl().getUml();
		    if (profile != null) {
		        profiles.add(profile);
		    }
		}
		return profiles;
	}

	public void setAppliedProfile(Collection<Profile> appliedProfile) {
	    this.appliedProfile.clear();
	    for (Profile profile: appliedProfile) {
	        this.addAppliedProfile(profile);
	    }
	}

	public void addAppliedProfile(Profile appliedProfile) {
		this.appliedProfile.add(ElementReferenceImpl.makeElementReference(appliedProfile));
	}
	
    public Collection<ElementReference> getAppliedProfileReference() {
        if (this.appliedProfile == null) {
            this.setAppliedProfileReference(this.deriveAppliedProfile());
        }
        return this.appliedProfile;
    }

    public void setAppliedProfileReference(Collection<ElementReference> appliedProfile) {
        this.appliedProfile = appliedProfile;
    }

    public void addAppliedProfileReference(ElementReference appliedProfile) {
        this.appliedProfile.add(appliedProfile);
    }
    
	/**
	 * The applied profiles of a package definition are the profiles listed in
	 * any @apply annotations on the package.
	 **/
	protected Collection<ElementReference> deriveAppliedProfile() {
	    Collection<ElementReference> appliedProfiles = new ArrayList<ElementReference>();
	    for (StereotypeAnnotation annotation: this.getAllAnnotations()) {
	        if (annotation.getImpl().getStereotypeName().getImpl().equals("apply")) {
	            for (QualifiedName name: annotation.getImpl().getNamesWithScope()) {
	                ElementReference profile = name.getImpl().getProfileReferent();
	                if (profile != null) {
	                    appliedProfiles.add(profile);
	                }
	            }
	        }
	    }
		return appliedProfiles;
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
	@Override
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
	    return super.annotationAllowed(annotation) ||
	            annotation.getStereotypeName().getImpl().equals("apply") ||
	            // The following allows the non-standard annotation of a
	            // package definition as a profile.
	            annotation.getStereotypeName().getImpl().equals("profile") &&
	            annotation.getNames() == null && annotation.getTaggedValues() == null;
	}

    @Override
    public Class<?> getUMLMetaclass() {
        return org.modeldriven.alf.uml.Package.class;
    }

	/**
	 * Returns true if the namespace definition associated with the given unit
	 * definition is a package definition.
	 **/
	@Override
	public Boolean matchForStub(UnitDefinition unit) {
		return unit.getDefinition() instanceof PackageDefinition;
	}

	/**
	 * Return true if the given member is either a PackageDefinition or an
	 * imported member whose referent is a PackageDefinition or a Package.
	 **/
	@Override
	public Boolean isSameKindAs(Member member) {
	    return member.getImpl().getReferent().getImpl().isPackage();
	}
	
	// Package-only members are limited to visibility within this package 
	// definition.
	@Override
    protected boolean allowPackageOnly() {
        return false;
    }

    public List<Member> getPublicMembers(Collection<ElementReference> excluded) {
        ArrayList<Member> publicMembers = new ArrayList<Member>();
        for (Member member: this.getMember(excluded)) {
            if (member.getImpl().isPublic()) {
                publicMembers.add(member);
            }
        }
        return publicMembers;
    }
    
    @Override
    public Collection<ElementReference> getAllAppliedProfiles() {
        if (this.allAppliedProfiles == null) {
            this.allAppliedProfiles = super.getAllAppliedProfiles();
            Collection<ElementReference> appliedProfiles = this.getAppliedProfileReference();
            this.allAppliedProfiles.addAll(appliedProfiles);
            
            // NOTE: Nested profiles are added here, because, according to the UML spec,
            // "Applying a Profile means recursively applying all its nested and imported Profiles."
            for (ElementReference profile: appliedProfiles) {
                this.allAppliedProfiles.addAll(getNestedProfiles(profile));
            }
        }        
        return this.allAppliedProfiles;
    }
    
    // NOTE: By searching members, not just owned members, this also returns any imported profiles.
    public static Collection<ElementReference> getNestedProfiles(ElementReference profile) {
        Collection<ElementReference> nestedProfiles = new ArrayList<ElementReference>();
        for (ElementReference member: profile.getImpl().getMembers()) {
            if (member.getImpl().isProfile()) {
                nestedProfiles.add(member);
                nestedProfiles.addAll(getNestedProfiles(member));
            }
        }
        return nestedProfiles;
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
