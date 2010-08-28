
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
 * The definition of a classifier template parameter, which acts as a classifier
 * within the definition of the template.
 **/

public class ClassifierTemplateParameter extends ClassifierDefinition {

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * Annotations are not allowed on classifier template parameters.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean matchForStub(UnitDefinition unit) {
		/*
		 * Returns false. (Classifier template parameters cannot be stubs.)
		 */
		return false; // STUB
	} // matchForStub

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is a classifier template parameter.
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
} // ClassifierTemplateParameter
