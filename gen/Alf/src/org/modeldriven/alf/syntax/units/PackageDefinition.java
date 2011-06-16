
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.PackageDefinitionImpl;

/**
 * The definition of a package, all of whose members must be packageable
 * elements.
 **/

public class PackageDefinition extends NamespaceDefinition {

	public PackageDefinition() {
		this.impl = new PackageDefinitionImpl(this);
	}

	public PackageDefinitionImpl getImpl() {
		return (PackageDefinitionImpl) this.impl;
	}

	public Collection<Profile> getAppliedProfile() {
		return this.getImpl().getAppliedProfile();
	}

	public void setAppliedProfile(Collection<Profile> appliedProfile) {
		this.getImpl().setAppliedProfile(appliedProfile);
	}

	public void addAppliedProfile(Profile appliedProfile) {
		this.getImpl().addAppliedProfile(appliedProfile);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.packageDefinitionAppliedProfileDerivation()) {
			violations.add(new ConstraintViolation(
					"packageDefinitionAppliedProfileDerivation", this));
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<Profile> appliedProfile = this.getAppliedProfile();
		if (appliedProfile != null) {
			if (appliedProfile.size() > 0) {
				System.out.println(prefix + " /appliedProfile:");
			}
			for (Object _object : appliedProfile.toArray()) {
				Profile _appliedProfile = (Profile) _object;
				System.out.println(prefix + "  " + _appliedProfile);
			}
		}
	}
} // PackageDefinition
