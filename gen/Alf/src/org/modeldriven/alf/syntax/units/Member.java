
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

import org.modeldriven.alf.syntax.units.impl.MemberImpl;

/**
 * A model of the common properties of the definition of a member of a namespace
 * in Alf.
 **/

public abstract class Member extends DocumentedElement {

	private String name = "";
	private String visibility = "";
	private Boolean isStub = false;
	private NamespaceDefinition namespace = null;
	private ArrayList<StereotypeAnnotation> annotation = new ArrayList<StereotypeAnnotation>();
	private Boolean isFeature = null; // DERIVED
	private Boolean isPrimitive = null; // DERIVED
	private Boolean isExternal = null; // DERIVED
	private UnitDefinition subunit = null; // DERIVED

	public MemberImpl getImpl() {
		return (MemberImpl) this.impl;
	}

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

	public Boolean getIsStub() {
		return this.isStub;
	}

	public void setIsStub(Boolean isStub) {
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

	public Boolean getIsFeature() {
		if (this.isFeature == null) {
			this.setIsFeature(this.getImpl().deriveIsFeature());
		}
		return this.isFeature;
	}

	public void setIsFeature(Boolean isFeature) {
		this.isFeature = isFeature;
	}

	public Boolean getIsPrimitive() {
		if (this.isPrimitive == null) {
			this.setIsPrimitive(this.getImpl().deriveIsPrimitive());
		}
		return this.isPrimitive;
	}

	public void setIsPrimitive(Boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public Boolean getIsExternal() {
		if (this.isExternal == null) {
			this.setIsExternal(this.getImpl().deriveIsExternal());
		}
		return this.isExternal;
	}

	public void setIsExternal(Boolean isExternal) {
		this.isExternal = isExternal;
	}

	public UnitDefinition getSubunit() {
		if (this.subunit == null) {
			this.setSubunit(this.getImpl().deriveSubunit());
		}
		return this.subunit;
	}

	public void setSubunit(UnitDefinition subunit) {
		this.subunit = subunit;
	}

	/**
	 * All stereotype annotations for a member must be allowed, as determined
	 * using the stereotypeAllowed operation.
	 **/
	public boolean memberAnnotations() {
		return this.getImpl().memberAnnotations();
	}

	/**
	 * A member is primitive if it has a @primitive annotation.
	 **/
	public boolean memberIsPrimitiveDerivation() {
		return this.getImpl().memberIsPrimitiveDerivation();
	}

	/**
	 * A member is external if it has an @external derivation.
	 **/
	public boolean memberIsExternalDerivation() {
		return this.getImpl().memberIsExternalDerivation();
	}

	/**
	 * If a member is external then it must be a stub.
	 **/
	public boolean memberExternal() {
		return this.getImpl().memberExternal();
	}

	/**
	 * If a member is a stub and is not external, then there must be a single
	 * subunit with the same qualified name as the stub that matches the stub,
	 * as determined by the matchForStub operation.
	 **/
	public boolean memberStub() {
		return this.getImpl().memberStub();
	}

	/**
	 * If the member is a stub and is not external, then its corresponding
	 * subunit is a unit definition with the same fully qualified name as the
	 * stub.
	 **/
	public boolean memberSubunitDerivation() {
		return this.getImpl().memberSubunitDerivation();
	}

	/**
	 * If a member is a stub, then the it must not have any stereotype
	 * annotations that are the same as its subunit. Two stereotype annotations
	 * are the same if they are for the same stereotype.
	 **/
	public boolean memberStubStereotypes() {
		return this.getImpl().memberStubStereotypes();
	}

	/**
	 * If a member is primitive, then it may not be a stub and it may not have
	 * any owned members that are template parameters.
	 **/
	public boolean memberPrimitive() {
		return this.getImpl().memberPrimitive();
	}

	/**
	 * Returns true of the given stereotype annotation is allowed for this kind
	 * of element.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Returns true of the given unit definition is a legal match for this
	 * member as a stub. By default, always returns false.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * Returns true if this member is distinguishable from the given member. Two
	 * members are distinguishable if their names are different or the they are
	 * of different kinds (as determined by the isSameKindAs operation).
	 * However, in any case that the UML Superstructure considers two names to
	 * be distinguishable if they are different, an Alf implementation may
	 * instead impose the stronger requirement that the names not be
	 * conflicting.
	 **/
	public Boolean isDistinguishableFrom(Member member) {
		return this.getImpl().isDistinguishableFrom(member);
	}

	/**
	 * Returns true if this member is of the same kind as the given member.
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.getName());
		s.append(" visibility:");
		s.append(this.getVisibility());
		s.append(" isStub:");
		s.append(this.getIsStub());
		Boolean isFeature = this.getIsFeature();
		if (isFeature != null) {
			s.append(" /isFeature:");
			s.append(isFeature);
		}
		Boolean isPrimitive = this.getIsPrimitive();
		if (isPrimitive != null) {
			s.append(" /isPrimitive:");
			s.append(isPrimitive);
		}
		Boolean isExternal = this.getIsExternal();
		if (isExternal != null) {
			s.append(" /isExternal:");
			s.append(isExternal);
		}
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		NamespaceDefinition namespace = this.getNamespace();
		if (namespace != null) {
			System.out.println(prefix + " namespace:" + namespace);
		}
		ArrayList<StereotypeAnnotation> annotation = this.getAnnotation();
		if (annotation != null) {
			if (annotation.size() > 0) {
				System.out.println(prefix + " annotation:");
			}
			for (StereotypeAnnotation _annotation : annotation) {
				if (_annotation != null) {
					_annotation.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		UnitDefinition subunit = this.getSubunit();
		if (subunit != null) {
			System.out.println(prefix + " /subunit:" + subunit);
		}
	}
} // Member
