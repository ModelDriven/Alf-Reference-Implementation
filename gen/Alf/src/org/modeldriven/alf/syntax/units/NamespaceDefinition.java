
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

/**
 * A model of the common properties of the definition of a namespace in Alf.
 **/

public abstract class NamespaceDefinition extends Member implements
		INamespaceDefinition {

	private ArrayList<IMember> ownedMember = new ArrayList<IMember>();
	private IUnitDefinition unit = null;

	public ArrayList<IMember> getOwnedMember() {
		return this.ownedMember;
	}

	public void setOwnedMember(ArrayList<IMember> ownedMember) {
		this.ownedMember = ownedMember;
	}

	public void addOwnedMember(IMember ownedMember) {
		this.ownedMember.add(ownedMember);
	}

	public IUnitDefinition getUnit() {
		return this.unit;
	}

	public void setUnit(IUnitDefinition unit) {
		this.unit = unit;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (IMember ownedMember : this.getOwnedMember()) {
			if (ownedMember != null) {
				ownedMember.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
		IUnitDefinition unit = this.getUnit();
		if (unit != null) {
			unit.print(prefix + " ");
		}
	}
} // NamespaceDefinition
