
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

import org.modeldriven.alf.syntax.units.impl.NamespaceDefinitionImpl;

/**
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinition extends Member {

	public NamespaceDefinitionImpl getImpl() {
		return (NamespaceDefinitionImpl) this.impl;
	}

	public Collection<Member> getOwnedMember() {
		return this.getImpl().getOwnedMember();
	}

	public void setOwnedMember(Collection<Member> ownedMember) {
		this.getImpl().setOwnedMember(ownedMember);
	}

	public void addOwnedMember(Member ownedMember) {
		this.getImpl().addOwnedMember(ownedMember);
	}

	public UnitDefinition getUnit() {
		return this.getImpl().getUnit();
	}

	public void setUnit(UnitDefinition unit) {
		this.getImpl().setUnit(unit);
	}

	public Collection<Member> getMember() {
		return this.getImpl().getMember();
	}

	public void setMember(Collection<Member> member) {
		this.getImpl().setMember(member);
	}

	public void addMember(Member member) {
		this.getImpl().addMember(member);
	}

	/**
	 * The members of a namespace definition include references to all owned
	 * members. Also, if the namespace definition has a unit with imports, then
	 * the members include imported members with referents to all imported
	 * elements. The imported elements and their visibility are determined as
	 * given in the UML Superstructure. The name of an imported member is the
	 * name of the imported element or its alias, if one has been given for it.
	 * Elements that would be indistinguishable from each other or from an owned
	 * member (as determined by the Member::isDistinguishableFrom operation) are
	 * not imported.
	 **/
	public boolean namespaceDefinitionMemberDerivation() {
		return this.getImpl().namespaceDefinitionMemberDerivation();
	}

	/**
	 * The members of a namespace must be distinguishable as determined by the
	 * Member::isDistinguishableFrom operation.
	 **/
	public boolean namespaceDefinitionMemberDistinguishaibility() {
		return this.getImpl().namespaceDefinitionMemberDistinguishaibility();
	}

	/**
	 * Returns true if the annotation is @external.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.namespaceDefinitionMemberDerivation()) {
			violations.add(new ConstraintViolation(
					"namespaceDefinitionMemberDerivation", this));
		}
		if (!this.namespaceDefinitionMemberDistinguishaibility()) {
			violations.add(new ConstraintViolation(
					"namespaceDefinitionMemberDistinguishaibility", this));
		}
		for (Member _ownedMember : this.getOwnedMember()) {
			_ownedMember.checkConstraints(violations);
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
		Collection<Member> ownedMember = this.getOwnedMember();
		if (ownedMember != null) {
			if (ownedMember.size() > 0) {
				System.out.println(prefix + " ownedMember:");
			}
			for (Member _ownedMember : ownedMember) {
				if (_ownedMember != null) {
					_ownedMember.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		UnitDefinition unit = this.getUnit();
		if (unit != null) {
			System.out.println(prefix + " unit:" + unit);
		}
		Collection<Member> member = this.getMember();
		if (member != null) {
			if (member.size() > 0) {
				System.out.println(prefix + " /member:");
			}
			for (Member _member : member) {
				System.out.println(prefix + "  " + _member);
			}
		}
	}
} // NamespaceDefinition
