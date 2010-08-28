
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
 * A typed element definition for a property (attribute or association end).
 **/

public class PropertyDefinition extends TypedElementDefinition {

	private boolean isComposite = false;
	private Expression initializer = null;
	private boolean isCollectionConversion = false; // DERIVED
	private boolean isBitStringConversion = false; // DERIVED

	public boolean getIsComposite() {
		return this.isComposite;
	}

	public void setIsComposite(boolean isComposite) {
		this.isComposite = isComposite;
	}

	public Expression getInitializer() {
		return this.initializer;
	}

	public void setInitializer(Expression initializer) {
		this.initializer = initializer;
	}

	public boolean getIsCollectionConversion() {
		return this.isCollectionConversion;
	}

	public void setIsCollectionConversion(boolean isCollectionConversion) {
		this.isCollectionConversion = isCollectionConversion;
	}

	public boolean getIsBitStringConversion() {
		return this.isBitStringConversion;
	}

	public void setIsBitStringConversion(boolean isBitStringConversion) {
		this.isBitStringConversion = isBitStringConversion;
	}

	public boolean annotationAllowed(StereotypeAnnotation annotation) {
		/*
		 * Returns true if the annotation is for a stereotype that has a
		 * metaclass consistent with Property.
		 */
		return false; // STUB
	} // annotationAllowed

	public boolean isSameKindAs(Member member) {
		/*
		 * Return true if the given member is either a PropertyDefinition or an
		 * imported member whose referent is a PropertyDefinition or a Property.
		 */
		return false; // STUB
	} // isSameKindAs

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" isComposite:");
		s.append(this.isComposite);
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.initializer != null) {
			this.initializer.print(prefix + " ");
		}
	}
} // PropertyDefinition
