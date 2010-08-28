
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
 * The definition of an enumeration, whose members must all be enumeration
 * literal names.
 **/

public class EnumerationDefinition extends ClassifierDefinition {

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns true if the given unit definition matches this enumeration
		 * definition considered as a classifier definition and the subunit is
		 * for an enumeration definition.
		 */
		return false; // STUB
	} // matchForStub

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * In addition to the annotations allowed for classifiers in general, an
		 * enumeration definition allows an annotation for any stereotype whose
		 * metaclass is consistent with Enumeration.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either an EnumerationDefinition or
		 * an imported member whose referent is an EnumerationDefinition or an
		 * Enumeration.
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
} // EnumerationDefinition
