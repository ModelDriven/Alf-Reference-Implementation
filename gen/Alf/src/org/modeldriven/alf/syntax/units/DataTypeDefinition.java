
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

import org.modeldriven.alf.syntax.units.impl.DataTypeDefinitionImpl;

/**
 * The definition of a data type, whose members must all be properties.
 **/

public class DataTypeDefinition extends ClassifierDefinition {

	public DataTypeDefinition() {
		this.impl = new DataTypeDefinitionImpl(this);
	}

	public DataTypeDefinitionImpl getImpl() {
		return (DataTypeDefinitionImpl) this.impl;
	}

	/**
	 * If a data type is primitive, then it may not have any owned members.
	 **/
	public boolean dataTypeDefinitionPrimitive() {
		return this.getImpl().dataTypeDefinitionPrimitive();
	}

	/**
	 * The specialization referents of a data type definition must all be data
	 * types.
	 **/
	public boolean dataTypeDefinitionSpecializationReferent() {
		return this.getImpl().dataTypeDefinitionSpecializationReferent();
	}

	/**
	 * Returns true if the given unit definition matches this data type
	 * definition considered as a classifier definition and the subunit is for a
	 * data type definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return this.getImpl().matchForStub(unit);
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, a data
	 * type definition allows @primitive annotations plus any stereotype whose
	 * metaclass is consistent with DataType.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is either a DataTypeDefinition or an
	 * imported member whose referent is a DataTypeDefinition or a DataType.
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
		if (!this.dataTypeDefinitionPrimitive()) {
			violations.add(new ConstraintViolation(
					"dataTypeDefinitionPrimitive", this));
		}
		if (!this.dataTypeDefinitionSpecializationReferent()) {
			violations.add(new ConstraintViolation(
					"dataTypeDefinitionSpecializationReferent", this));
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
} // DataTypeDefinition
