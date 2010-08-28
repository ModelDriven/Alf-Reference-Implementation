
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
 * The definition of a data type, whose members must all be properties.
 **/

public class DataTypeDefinition extends ClassifierDefinition {

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns true if the given unit definition matches this data type
		 * definition considered as a classifier definition and the subunit is
		 * for a data type definition.
		 */
		return false; // STUB
	} // matchForStub

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * In addition to the annotations allowed for classifiers in general, a
		 * data type definition allows @primitive annotations plus any
		 * stereotype whose metaclass is consistent with DataType.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either a DataTypeDefinition or an
		 * imported member whose referent is a DataTypeDefinition or a DataType.
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
} // DataTypeDefinition
