
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

/**
 * The definition of a package, all of whose members must be packageable
 * elements.
 **/

public class PackageDefinition extends NamespaceDefinition {

	private ArrayList<Profile> appliedProfile = new ArrayList<Profile>(); // DERIVED

	public ArrayList<Profile> getAppliedProfile() {
		return this.appliedProfile;
	}

	public void setAppliedProfile(ArrayList<Profile> appliedProfile) {
		this.appliedProfile = appliedProfile;
	}

	public void addAppliedProfile(Profile appliedProfile) {
		this.appliedProfile.add(appliedProfile);
	}

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * In addition to the annotations allowed on any namespace definition, a
		 * package definition allows @apply annotations plus any stereotype
		 * whose metaclass is consistent with Package.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns true of the namespace definition associated with the given
		 * unit definition is a package definition.
		 */
		return false; // STUB
	} // matchForStub

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either a PackageDefinition or an
		 * imported member whose referent is a PackageDefinition or a Package.
		 */
		return false; // STUB
	} // isSameKindAs

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // PackageDefinition
