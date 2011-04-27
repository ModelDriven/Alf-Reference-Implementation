
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.ImportedMemberImpl;

public class ImportedMember extends Member {

	public ImportedMember() {
		this.impl = new ImportedMemberImpl(this);
	}

	public ImportedMemberImpl getImpl() {
		return (ImportedMemberImpl) this.impl;
	}

	public ElementReference getReferent() {
		return this.getImpl().getReferent();
	}

	public void setReferent(ElementReference referent) {
		this.getImpl().setReferent(referent);
	}

	/**
	 * An imported element is not a stub.
	 **/
	public boolean importedMemberNotStub() {
		return this.getImpl().importedMemberNotStub();
	}

	/**
	 * An imported element is a feature if its referent is a feature.
	 **/
	public boolean importedMemberIsFeatureDerivation() {
		return this.getImpl().importedMemberIsFeatureDerivation();
	}

	/**
	 * Returns false. (Imported members do not have annotations.)
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * If the given member is not an imported member, then return the result of
	 * checking whether the given member is distinguishable from this member.
	 * Else, if the element of the referent for this member is an Alf member,
	 * then return the result of checking whether that element is
	 * distinguishable from the given member. Else, if the element of the
	 * referent for the given member is an Alf member, then return the result of
	 * checking whether that element is distinguishable from this member. Else,
	 * the referents for both this and the given member are UML elements, so
	 * return the result of checking their distinguishability according to the
	 * rules of the UML superstructure.
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
		if (!this.importedMemberNotStub()) {
			violations.add(new ConstraintViolation("importedMemberNotStub",
					this));
		}
		if (!this.importedMemberIsFeatureDerivation()) {
			violations.add(new ConstraintViolation(
					"importedMemberIsFeatureDerivation", this));
		}
	}

	public String toString() {
		return this.getImpl().toString();
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
		ElementReference referent = this.getReferent();
		if (referent != null) {
			System.out.println(prefix + " referent:" + referent);
		}
	}
} // ImportedMember
