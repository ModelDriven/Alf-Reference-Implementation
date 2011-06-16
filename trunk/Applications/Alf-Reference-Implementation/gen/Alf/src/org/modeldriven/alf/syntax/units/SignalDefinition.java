
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

import org.modeldriven.alf.syntax.units.impl.SignalDefinitionImpl;

/**
 * The definition of a signal, whose members must all be properties.
 **/

public class SignalDefinition extends ClassifierDefinition {

	public SignalDefinition() {
		this.impl = new SignalDefinitionImpl(this);
	}

	public SignalDefinitionImpl getImpl() {
		return (SignalDefinitionImpl) this.impl;
	}

	/**
	 * The specialization referents of a signal definition must all be signals.
	 **/
	public boolean signalDefinitionSpecializationReferent() {
		return this.getImpl().signalDefinitionSpecializationReferent();
	}

	/**
	 * Returns true if the given unit definition matches this signal definition
	 * considered as a classifier definition and the subunit is for a signal
	 * definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, a
	 * signal definition allows an annotation for any stereotype whose metaclass
	 * is consistent with Signal.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a SignalDefinition or an
	 * imported member whose referent is a SignalDefinition or a Reception
	 * (where signal reception definitions are considered to be kinds of signal
	 * definitions).
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
		if (!this.signalDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"signalDefinitionSpecializationReferent", this));
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
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
	}
} // SignalDefinition
