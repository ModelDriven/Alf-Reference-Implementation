
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
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinition extends Member {

	private ArrayList<Member> ownedMember = new ArrayList<Member>();
	private UnitDefinition unit = null;
	private ArrayList<Member> member = new ArrayList<Member>(); // DERIVED

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
		return this.member;
	}

	public void setMember(ArrayList<Member> member) {
		this.member = member;
	}

	public void addMember(Member member) {
		this.member.add(member);
	}

	public abstract boolean annotationAllowed(StereotypeAnnotation annotation);

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (Member ownedMember : this.getOwnedMember()) {
			if (ownedMember != null) {
				ownedMember.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
		if (this.unit != null) {
			this.unit.print(prefix + " ");
		}
	}
} // NamespaceDefinition
