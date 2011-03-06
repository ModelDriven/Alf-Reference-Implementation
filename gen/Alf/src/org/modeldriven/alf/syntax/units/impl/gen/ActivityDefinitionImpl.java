
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
import java.util.Collection;
import java.util.List;

/**
 * The definition of an activity, with any formal parameters defined as owned
 * members.
 **/

public class ActivityDefinitionImpl extends
		org.modeldriven.alf.syntax.units.impl.gen.ClassifierDefinitionImpl {

	private Block body = null;

	public ActivityDefinitionImpl(ActivityDefinition self) {
		super(self);
	}

	public ActivityDefinition getSelf() {
		return (ActivityDefinition) this.self;
	}

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	/**
	 * An activity definition may not have a specialization list.
	 **/
	public boolean activityDefinitionSpecialization() {
		return true;
	}

	/**
	 * If an activity definition is primitive, then it must have a body that is
	 * empty.
	 **/
	public boolean activityDefinitionPrimitive() {
		return true;
	}

	/**
	 * In addition to the annotations allowed for classifiers in general, an
	 * activity definition allows @primitive annotations and any stereotype
	 * whose metaclass is consistent with Activity.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return false; // STUB
	} // annotationAllowed

	/**
	 * Returns true if the given unit definition matches this activity
	 * definition considered as a classifier definition and the subunit is for
	 * an activity definition. In addition, the subunit definition must have
	 * formal parameters that match each of the formal parameters of the stub
	 * definition, in order. Two formal parameters match if they have the same
	 * direction, name, multiplicity bounds, ordering, uniqueness and type
	 * reference.
	 **/
	public Boolean matchForStub(UnitDefinition unit) {
		return false; // STUB
	} // matchForStub

	/**
	 * Return true if the given member is either an ActivityDefinition or an
	 * imported member whose referent is an ActivityDefinition or an Activity.
	 **/
	public Boolean isSameKindAs(Member member) {
		return false; // STUB
	} // isSameKindAs

} // ActivityDefinitionImpl
