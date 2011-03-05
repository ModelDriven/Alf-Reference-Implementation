
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

import org.modeldriven.alf.syntax.units.impl.NamespaceDefinitionImpl;

/**
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinition extends Member {

	private ArrayList<Member> ownedMember = new ArrayList<Member>();
	private UnitDefinition unit = null;
	private ArrayList<Member> member = null; // DERIVED

	public NamespaceDefinitionImpl getImpl() {
		return (NamespaceDefinitionImpl) this.impl;
	}

	public ArrayList<Member> getOwnedMember() {
		return this.ownedMember;
	}

	public void setOwnedMember(ArrayList<Member> ownedMember) {
		this.ownedMember = ownedMember;
	}

	public void addOwnedMember(Member ownedMember) {
		this.ownedMember.add(ownedMember);
	}

	public UnitDefinition getUnit() {
		return this.unit;
	}

	public void setUnit(UnitDefinition unit) {
		this.unit = unit;
	}

	public ArrayList<Member> getMember() {
		if (this.member == null) {
			this.setMember(this.getImpl().deriveMember());
		}
		return this.member;
	}

	public void setMember(ArrayList<Member> member) {
		this.member = member;
	}

	public void addMember(Member member) {
		this.member.add(member);
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<Member> ownedMember = this.getOwnedMember();
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
		ArrayList<Member> member = this.getMember();
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
