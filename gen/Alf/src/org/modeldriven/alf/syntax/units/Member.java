
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
 * A model of the common properties of the definition of a member of a namespace
 * in Alf.
 **/

public abstract class Member extends DocumentedElement {

	private String name = "";
	private String visibility = "";
	private boolean isStub = false;
	private NamespaceDefinition namespace = null;
	private ArrayList<StereotypeAnnotation> annotation = new ArrayList<StereotypeAnnotation>();
	private boolean isFeature = false; // DERIVED
	private boolean isPrimitive = false; // DERIVED
	private boolean isExternal = false; // DERIVED
	private UnitDefinition subunit = null; // DERIVED

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisibility() {
		return this.visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public boolean getIsStub() {
		return this.isStub;
	}

	public void setIsStub(boolean isStub) {
		this.isStub = isStub;
	}

	public NamespaceDefinition getNamespace() {
		return this.namespace;
	}

	public void setNamespace(NamespaceDefinition namespace) {
		this.namespace = namespace;
	}

	public ArrayList<StereotypeAnnotation> getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(ArrayList<StereotypeAnnotation> annotation) {
		this.annotation = annotation;
	}

	public void addAnnotation(StereotypeAnnotation annotation) {
		this.annotation.add(annotation);
	}

	public boolean getIsFeature() {
		return this.isFeature;
	}

	public void setIsFeature(boolean isFeature) {
		this.isFeature = isFeature;
	}

	public boolean getIsPrimitive() {
		return this.isPrimitive;
	}

	public void setIsPrimitive(boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public boolean getIsExternal() {
		return this.isExternal;
	}

	public void setIsExternal(boolean isExternal) {
		this.isExternal = isExternal;
	}

	public UnitDefinition getSubunit() {
		return this.subunit;
	}

	public void setSubunit(UnitDefinition subunit) {
		this.subunit = subunit;
	}

	public abstract boolean annotationAllowed(StereotypeAnnotation annotation);

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns true of the given unit definition is a legal match for this
		 * member as a stub. By default, always returns false.
		 */
		return false; // STUB
	} // matchForStub

	public boolean isDistinguishableFrom(Member member) {
		/*
		 * Returns true if this member is distinguishable from the given member.
		 * Two members are distinguishable if their names are different or the
		 * they are of different kinds (as determined by the isSameKindAs
		 * operation). However, in any case that the UML Superstructure
		 * considers two names to be distinguishable if they are different, an
		 * Alf implementation may instead impose the stronger requirement that
		 * the names not be conflicting.
		 */
		return false; // STUB
	} // isDistinguishableFrom

	public abstract boolean isSameKindAs(Member member);

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.name);
		s.append(" visibility:");
		s.append(this.visibility);
		s.append(" isStub:");
		s.append(this.isStub);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.namespace != null) {
			this.namespace.print(prefix + " ");
		}
		for (StereotypeAnnotation annotation : this.getAnnotation()) {
			if (annotation != null) {
				annotation.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // Member
