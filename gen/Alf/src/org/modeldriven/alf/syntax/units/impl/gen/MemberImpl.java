
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

import org.modeldriven.alf.parser.AlfParser;

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

/**
 * A model of the common properties of the definition of a member of a namespace
 * in Alf.
 **/

public abstract class MemberImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.DocumentedElementImpl {

	private String name = "";
	private String visibility = "";
	private Boolean isStub = false;
	private NamespaceDefinition namespace = null;
	private Collection<StereotypeAnnotation> annotation = new ArrayList<StereotypeAnnotation>();
	private Boolean isFeature = null; // DERIVED
	private Boolean isPrimitive = null; // DERIVED
	private Boolean isExternal = null; // DERIVED
	private UnitDefinition subunit = null; // DERIVED

	public MemberImpl(Member self) {
		super(self);
	}

	public Member getSelf() {
		return (Member) this.self;
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

	public Collection<StereotypeAnnotation> getAnnotation() {
		return this.annotation;
	}

	public void setAnnotation(Collection<StereotypeAnnotation> annotation) {
		this.annotation = annotation;
	}

	public void addAnnotation(StereotypeAnnotation annotation) {
		this.annotation.add(annotation);
	}

	public Boolean getIsFeature() {
		if (this.isFeature == null) {
			this.setIsFeature(this.deriveIsFeature());
		}
		return this.isFeature;
	}

	public void setIsFeature(Boolean isFeature) {
		this.isFeature = isFeature;
	}

	public Boolean getIsPrimitive() {
		if (this.isPrimitive == null) {
			this.setIsPrimitive(this.deriveIsPrimitive());
		}
		return this.isPrimitive;
	}

	public void setIsPrimitive(Boolean isPrimitive) {
		this.isPrimitive = isPrimitive;
	}

	public Boolean getIsExternal() {
		if (this.isExternal == null) {
			this.setIsExternal(this.deriveIsExternal());
		}
		return this.isExternal;
	}

	public void setIsExternal(Boolean isExternal) {
		this.isExternal = isExternal;
	}

	public UnitDefinition getSubunit() {
		if (this.subunit == null) {
			this.setSubunit(this.deriveSubunit());
		}
		return this.subunit;
	}

	public void setSubunit(UnitDefinition subunit) {
		this.subunit = subunit;
	}

	protected Boolean deriveIsFeature() {
		return null; // STUB
	}

	protected Boolean deriveIsPrimitive() {
		return null; // STUB
	}

	protected Boolean deriveIsExternal() {
		return null; // STUB
	}

	protected UnitDefinition deriveSubunit() {
		return null; // STUB
	}

	/**
	 * All stereotype annotations for a member must be allowed, as determined
	 * using the stereotypeAllowed operation.
	 **/
	public boolean memberAnnotations() {
		return true;
	}

	/**
	 * A member is primitive if it has a @primitive annotation.
	 **/
	public boolean memberIsPrimitiveDerivation() {
		this.getSelf().getIsPrimitive();
		return true;
	}

	/**
	 * A member is external if it has an @external derivation.
	 **/
	public boolean memberIsExternalDerivation() {
		this.getSelf().getIsExternal();
		return true;
	}

	/**
	 * If a member is external then it must be a stub.
	 **/
	public boolean memberExternal() {
		return true;
	}

	/**
	 * If a member is a stub and is not external, then there must be a single
	 * subunit with the same qualified name as the stub that matches the stub,
	 * as determined by the matchForStub operation.
	 **/
	public boolean memberStub() {
		return true;
	}

	/**
	 * If the member is a stub and is not external, then its corresponding
	 * subunit is a unit definition with the same fully qualified name as the
	 * stub.
	 **/
	public boolean memberSubunitDerivation() {
		this.getSelf().getSubunit();
		return true;
	}

	/**
	 * If a member is a stub, then the it must not have any stereotype
	 * annotations that are the same as its subunit. Two stereotype annotations
	 * are the same if they are for the same stereotype.
	 **/
	public boolean memberStubStereotypes() {
		return true;
	}

	/**
	 * If a member is primitive, then it may not be a stub and it may not have
	 * any owned members that are template parameters.
	 **/
	public boolean memberPrimitive() {
		return true;
	}

	/**
	 * Returns true of the given stereotype annotation is allowed for this kind
	 * of element.
	 **/
	public abstract Boolean annotationAllowed(StereotypeAnnotation annotation);

	/**
	 * Returns true of the given unit definition is a legal match for this
	 * member as a stub. By default, always returns false.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

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
		return false; // STUB
	} // isDistinguishableFrom

	/**
	 * Returns true if this member is of the same kind as the given member.
	 **/
	public abstract Boolean isSameKindAs(Member member);
} // MemberImpl
