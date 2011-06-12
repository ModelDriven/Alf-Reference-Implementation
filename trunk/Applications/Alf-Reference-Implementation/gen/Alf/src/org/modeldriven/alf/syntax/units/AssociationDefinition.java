
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.AssociationDefinitionImpl;

/**
 * The definition of an association, whose members must all be properties.
 **/

public class AssociationDefinition extends ClassifierDefinition {

	public AssociationDefinition() {
		this.impl = new AssociationDefinitionImpl(this);
	}

	public AssociationDefinitionImpl getImpl() {
		return (AssociationDefinitionImpl) this.impl;
	}

	/**
	 * The specialization referents of an association definition must all be
	 * associations.
	 **/
	public boolean associationDefinitionSpecializationReferent() {
		return this.getImpl().associationDefinitionSpecializationReferent();
	}

	/**
	 * Returns true if the given unit definition matches this association
	 * definition considered as a classifier definition and the subunit is for
	 * an association definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * association definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Association.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either an AssociationDefinition or an
	 * imported member whose referent is an AssociationDefinition or an
	 * Association.
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
		if (!this.associationDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"associationDefinitionSpecializationReferent", this));
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
} // AssociationDefinition
