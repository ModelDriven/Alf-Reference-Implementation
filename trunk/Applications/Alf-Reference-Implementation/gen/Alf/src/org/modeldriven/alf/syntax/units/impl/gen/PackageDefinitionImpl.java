
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The definition of a package, all of whose members must be packageable
 * elements.
 **/

public class PackageDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.NamespaceDefinitionImpl {

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

	protected Collection<Profile> deriveAppliedProfile() {
		return null; // STUB
	}

	/**
	 * The applied profiles of a package definition are the profiles listed in
	 * any @apply annotations on the package.
	 **/
	public boolean packageDefinitionAppliedProfileDerivation() {
		this.getSelf().getAppliedProfile();
		return true;
	}

	/**
	 * In addition to the annotations allowed on any namespace definition, a
	 * package definition allows @apply annotations plus any stereotype whose
	 * metaclass is consistent with Package.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * Returns true of the namespace definition associated with the given unit
	 * definition is a package definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

	/**
	 * Return true if the given member is either a PackageDefinition or an
	 * imported member whose referent is a PackageDefinition or a Package.
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // PackageDefinitionImpl
