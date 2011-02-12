
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.units.impl.PackageDefinitionImpl;

/**
 * The definition of a package, all of whose members must be packageable
 * elements.
 **/

public class PackageDefinition extends NamespaceDefinition {

	private ArrayList<Profile> appliedProfile = null; // DERIVED

	public PackageDefinition() {
		this.impl = new PackageDefinitionImpl(this);
	}

	public PackageDefinitionImpl getImpl() {
		return (PackageDefinitionImpl) this.impl;
	}

	public ArrayList<Profile> getAppliedProfile() {
		if (this.appliedProfile == null) {
			this.appliedProfile = this.getImpl().deriveAppliedProfile();
		}
		return this.appliedProfile;
	}

	/**
	 * The applied profiles of a package definition are the profiles listed in
	 * any @apply annotations on the package.
	 **/
	public boolean packageDefinitionAppliedProfileDerivation() {
		return this.getImpl().packageDefinitionAppliedProfileDerivation();
	}

	/**
	 * In addition to the annotations allowed on any namespace definition, a
	 * package definition allows @apply annotations plus any stereotype whose
	 * metaclass is consistent with Package.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Returns true of the namespace definition associated with the given unit
	 * definition is a package definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * Return true if the given member is either a PackageDefinition or an
	 * imported member whose referent is a PackageDefinition or a Package.
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<Profile> appliedProfile = this.getAppliedProfile();
		if (appliedProfile != null) {
			for (Profile item : this.getAppliedProfile()) {
				System.out.println(prefix + " /" + item);
			}
		}
	}
} // PackageDefinition
