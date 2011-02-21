
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

/**
 * A model of the common properties of the definition of a member of a namespace
 * in Alf.
 **/

public abstract class MemberImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.DocumentedElementImpl {

	public MemberImpl(Member self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.Member getSelf() {
		return (Member) this.self;
	}

	public Boolean deriveIsFeature() {
		return null; // STUB
	}

	public Boolean deriveIsPrimitive() {
		return null; // STUB
	}

	public Boolean deriveIsExternal() {
		return null; // STUB
	}

	public UnitDefinition deriveSubunit() {
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
