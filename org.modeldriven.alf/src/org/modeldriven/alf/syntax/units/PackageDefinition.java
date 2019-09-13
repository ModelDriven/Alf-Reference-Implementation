/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.uml.Profile;
import java.util.Collection;
import org.modeldriven.alf.syntax.units.impl.PackageDefinitionImpl;

/**
 * The definition of a package, all of whose members must be packageable
 * elements.
 **/

public class PackageDefinition extends NamespaceDefinition {

	public PackageDefinition() {
		this.impl = new PackageDefinitionImpl(this);
	}

	public PackageDefinition(Parser parser) {
		this();
		this.init(parser);
	}

	public PackageDefinition(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	@Override
    public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Returns true of the namespace definition associated with the given unit
	 * definition is a package definition.
	 **/
	@Override
    public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * Return true if the given member is either a PackageDefinition or an
	 * imported member whose referent is a PackageDefinition or a Package.
	 **/
	@Override
    public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	@Override
    public void _deriveAll() {
		this.getAppliedProfile();
		super._deriveAll();
	}

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.packageDefinitionAppliedProfileDerivation()) {
			violations.add(new ConstraintViolation(
					"packageDefinitionAppliedProfileDerivation", this));
		}
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		if (includeDerived) {
			Collection<Profile> appliedProfile = this.getAppliedProfile();
			if (appliedProfile != null && appliedProfile.size() > 0) {
				System.out.println(prefix + " /appliedProfile:");
				for (Object _object : appliedProfile.toArray()) {
					Profile _appliedProfile = (Profile) _object;
					System.out.println(prefix + "  "
							+ _appliedProfile.toString(includeDerived));
				}
			}
		}
	}
} // PackageDefinition
