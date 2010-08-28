
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

public class ImportedMember extends Member {

	private ElementReference referent = null;

	public ElementReference getReferent() {
		return this.referent;
	}

	public void setReferent(ElementReference referent) {
		this.referent = referent;
	}

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * Returns false. (Imported members do not have annotations.)
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean isSameKindAs(Member member) {
		/*
		 * If the given member is not an imported member, then return the result
		 * of checking whether the given member is distinguishable from this
		 * member. Else, if the element of the referent for this member is an
		 * Alf member, then return the result of checking whether that element
		 * is distinguishable from the given member. Else, if the element of the
		 * referent for the given member is an Alf member, then return the
		 * result of checking whether that element is distinguishable from this
		 * member. Else, the referents for both this and the given member are
		 * UML elements, so return the result of checking their
		 * distinguishability according to the rules of the UML superstructure.
		 */
		return false; // STUB
	} // isSameKindAs

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.referent != null) {
			this.referent.print(prefix + " ");
		}
	}
} // ImportedMember
