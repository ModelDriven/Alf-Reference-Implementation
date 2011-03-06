
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

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.MemberImpl {

	private Collection<Member> ownedMember = new ArrayList<Member>();
	private UnitDefinition unit = null;
	private Collection<Member> member = null; // DERIVED

	public NamespaceDefinitionImpl(NamespaceDefinition self) {
		super(self);
	}

	public NamespaceDefinition getSelf() {
		return (NamespaceDefinition) this.self;
	}

	public Collection<Member> getOwnedMember() {
		return this.ownedMember;
	}

	public void setOwnedMember(Collection<Member> ownedMember) {
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

	public Collection<Member> getMember() {
		if (this.member == null) {
			this.setMember(this.deriveMember());
		}
		return this.member;
	}

	public void setMember(Collection<Member> member) {
		this.member = member;
	}

	public void addMember(Member member) {
		this.member.add(member);
	}

	protected Collection<Member> deriveMember() {
		return null; // STUB
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
		this.getSelf().getMember();
		return true;
	}

	/**
	 * The members of a namespace must be distinguishable as determined by the
	 * Member::isDistinguishableFrom operation.
	 **/
	public boolean namespaceDefinitionMemberDistinguishaibility() {
		return true;
	}

	/**
	 * Returns true if the annotation is @external.
	 **/
	public abstract Boolean annotationAllowed(StereotypeAnnotation annotation);
} // NamespaceDefinitionImpl
