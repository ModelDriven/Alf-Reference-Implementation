
/*
 * Copyright 2010 Data Access Technologies, Inc. (Model Driven Solutions)
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

import java.util.ArrayList;

/**
 * The definition of an activity, with any formal parameters defined as owned
 * members.
 **/

public class ActivityDefinition extends ClassifierDefinition {

	private Block body = null;

	public Block getBody() {
		return this.body;
	}

	public void setBody(Block body) {
		this.body = body;
	}

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * In addition to the annotations allowed for classifiers in general, an
		 * activity definition allows @primitive annotations and any stereotype
		 * whose metaclass is consistent with Activity.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns true if the given unit definition matches this activity
		 * definition considered as a classifier definition and the subunit is
		 * for an activity definition. In addition, the subunit definition must
		 * have formal parameters that match each of the formal parameters of
		 * the stub definition, in order. Two formal parameters match if they
		 * have the same direction, name, multiplicity bounds, ordering,
		 * uniqueness and type reference.
		 */
		return false; // STUB
	} // matchForStub

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either an ActivityDefinition or an
		 * imported member whose referent is an ActivityDefinition or an
		 * Activity.
		 */
		return false; // STUB
	} // isSameKindAs

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.body != null) {
			this.body.print(prefix + " ");
		}
	}
} // ActivityDefinition
