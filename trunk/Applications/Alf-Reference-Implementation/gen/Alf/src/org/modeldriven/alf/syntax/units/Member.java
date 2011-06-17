
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

import org.modeldriven.alf.syntax.units.impl.MemberImpl;

/**
 * A model of the common properties of the definition of a member of a namespace
 * in Alf.
 **/

public abstract class Member extends DocumentedElement {

	public MemberImpl getImpl() {
		return (MemberImpl) this.impl;
	}

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public String getVisibility() {
		return this.getImpl().getVisibility();
	}

	public void setVisibility(String visibility) {
		this.getImpl().setVisibility(visibility);
	}

	public Boolean getIsStub() {
		return this.getImpl().getIsStub();
	}

	public void setIsStub(Boolean isStub) {
		this.getImpl().setIsStub(isStub);
	}

	public NamespaceDefinition getNamespace() {
		return this.getImpl().getNamespace();
	}

	public void setNamespace(NamespaceDefinition namespace) {
		this.getImpl().setNamespace(namespace);
	}

	public Collection<StereotypeAnnotation> getAnnotation() {
		return this.getImpl().getAnnotation();
	}

	public void setAnnotation(Collection<StereotypeAnnotation> annotation) {
		this.getImpl().setAnnotation(annotation);
	}

	public void addAnnotation(StereotypeAnnotation annotation) {
		this.getImpl().addAnnotation(annotation);
	}

	public Boolean getIsFeature() {
		return this.getImpl().getIsFeature();
	}

	public void setIsFeature(Boolean isFeature) {
		this.getImpl().setIsFeature(isFeature);
	}

	public Boolean getIsPrimitive() {
		return this.getImpl().getIsPrimitive();
	}

	public void setIsPrimitive(Boolean isPrimitive) {
		this.getImpl().setIsPrimitive(isPrimitive);
	}

	public Boolean getIsExternal() {
		return this.getImpl().getIsExternal();
	}

	public void setIsExternal(Boolean isExternal) {
		this.getImpl().setIsExternal(isExternal);
	}

	public UnitDefinition getSubunit() {
		return this.getImpl().getSubunit();
	}

	public void setSubunit(UnitDefinition subunit) {
		this.getImpl().setSubunit(subunit);
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

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.memberAnnotations()) {
			violations.add(new ConstraintViolation("memberAnnotations", this));
		}
		if (!this.memberIsPrimitiveDerivation()) {
			violations.add(new ConstraintViolation(
					"memberIsPrimitiveDerivation", this));
		}
		if (!this.memberIsExternalDerivation()) {
			violations.add(new ConstraintViolation(
					"memberIsExternalDerivation", this));
		}
		if (!this.memberExternal()) {
			violations.add(new ConstraintViolation("memberExternal", this));
		}
		if (!this.memberStub()) {
			violations.add(new ConstraintViolation("memberStub", this));
		}
		if (!this.memberSubunitDerivation()) {
			violations.add(new ConstraintViolation("memberSubunitDerivation",
					this));
		}
		if (!this.memberStubStereotypes()) {
			violations.add(new ConstraintViolation("memberStubStereotypes",
					this));
		}
		if (!this.memberPrimitive()) {
			violations.add(new ConstraintViolation("memberPrimitive", this));
		}
		for (Object _annotation : this.getAnnotation().toArray()) {
			((StereotypeAnnotation) _annotation).checkConstraints(violations);
		}
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.hashCode() + ")"
				+ this.getImpl().toString(includeDerived);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" name:");
		s.append(this.getName());
		s.append(" visibility:");
		s.append(this.getVisibility());
		s.append(" isStub:");
		s.append(this.getIsStub());
		if (includeDerived) {
			s.append(" /isFeature:");
			s.append(this.getIsFeature());
		}
		if (includeDerived) {
			s.append(" /isPrimitive:");
			s.append(this.getIsPrimitive());
		}
		if (includeDerived) {
			s.append(" /isExternal:");
			s.append(this.getIsExternal());
		}
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		NamespaceDefinition namespace = this.getNamespace();
		if (namespace != null) {
			System.out.println(prefix + " namespace:"
					+ namespace.toString(includeDerived));
		}
		Collection<StereotypeAnnotation> annotation = this.getAnnotation();
		if (annotation != null && annotation.size() > 0) {
			System.out.println(prefix + " annotation:");
			for (Object _object : annotation.toArray()) {
				StereotypeAnnotation _annotation = (StereotypeAnnotation) _object;
				if (_annotation != null) {
					_annotation.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
		if (includeDerived) {
		}
		if (includeDerived) {
		}
		if (includeDerived) {
		}
		if (includeDerived) {
			UnitDefinition subunit = this.getSubunit();
			if (subunit != null) {
				System.out.println(prefix + " /subunit:"
						+ subunit.toString(includeDerived));
			}
		}
	}
} // Member
