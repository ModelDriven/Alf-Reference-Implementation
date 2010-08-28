
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
 * The definition of an association, whose members must all be properties.
 **/

public class AssociationDefinition extends ClassifierDefinition {

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns true if the given unit definition matches this association
		 * definition considered as a classifier definition and the subunit is
		 * for an association definition.
		 */
		return false; // STUB
	} // matchForStub

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * In addition to the annotations allowed for classifiers in general, an
		 * association definition allows an annotation for any stereotype whose
		 * metaclass is consistent with Association.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either an AssociationDefinition or
		 * an imported member whose referent is an AssociationDefinition or an
		 * Association.
		 */
		return false; // STUB
	} // isSameKindAs

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // AssociationDefinition
