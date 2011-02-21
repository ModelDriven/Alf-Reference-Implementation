
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
 * The definition of an association, whose members must all be properties.
 **/

public class AssociationDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.ClassifierDefinitionImpl {

	public AssociationDefinitionImpl(AssociationDefinition self) {
		super(self);
	}

	public org.modeldriven.alf.syntax.units.AssociationDefinition getSelf() {
		return (AssociationDefinition) this.self;
	}

	/**
	 * The specialization referents of an association definition must all be
	 * associations.
	 **/
	public boolean associationDefinitionSpecializationReferent() {
		return true;
	}

	/**
	 * Returns true if the given unit definition matches this association
	 * definition considered as a classifier definition and the subunit is for
	 * an association definition.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * association definition allows an annotation for any stereotype whose
	 * metaclass is consistent with Association.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * Return true if the given member is either an AssociationDefinition or an
	 * imported member whose referent is an AssociationDefinition or an
	 * Association.
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // AssociationDefinitionImpl
