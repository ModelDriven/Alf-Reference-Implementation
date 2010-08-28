
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
 * The definition of a signal, whose members must all be properties.
 **/

public class SignalDefinition extends ClassifierDefinition {

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns true if the given unit definition matches this signal
		 * definition considered as a classifier definition and the subunit is
		 * for a signal definition.
		 */
		return false; // STUB
	} // matchForStub

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * In addition to the annotations allowed for classifiers in general, a
		 * signal definition allows an annotation for any stereotype whose
		 * metaclass is consistent with Signal.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either a SignalDefinition or an
		 * imported member whose referent is a SignalDefinition or a Reception
		 * (where signal reception definitions are considered to be kinds of
		 * signal definitions).
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
} // SignalDefinition
