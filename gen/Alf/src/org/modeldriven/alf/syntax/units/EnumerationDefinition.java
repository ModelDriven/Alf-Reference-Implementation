
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.EnumerationDefinitionImpl;

/**
 * The definition of an enumeration, whose members must all be enumeration
 * literal names.
 **/

public class EnumerationDefinition extends ClassifierDefinition {

	public EnumerationDefinition() {
		this.impl = new EnumerationDefinitionImpl(this);
	}

	public EnumerationDefinitionImpl getImpl() {
		return (EnumerationDefinitionImpl) this.impl;
	}

	/**
	 * The specialization referents of a class definition must all be classes. A
	 * class definition may not have any referents that are active classes
	 * unless this is an active class definition.
	 **/
	public boolean classDefinitionSpecializationReferent() {
		return this.getImpl().classDefinitionSpecializationReferent();
	}

	/**
	 * Returns true if the given unit definition matches this enumeration
	 * definition considered as a classifier definition and the subunit is for
	 * an enumeration definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * enumeration definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Enumeration.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either an EnumerationDefinition or an
	 * imported member whose referent is an EnumerationDefinition or an
	 * Enumeration.
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
		if (!this.classDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"classDefinitionSpecializationReferent", this));
		}
	}

	public String toString() {
		return this.getImpl().toString();
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
} // EnumerationDefinition
