
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
 * An annotation of a member definition indicating the application of a
 * stereotype (or one of a small number of special-case annotations).
 **/

public class StereotypeAnnotation extends SyntaxElement {

	private TaggedValueList taggedValues = null;
	private QualifiedNameList names = null;
	private QualifiedName stereotypeName = null;
	private Stereotype stereotype = null; // DERIVED

	public TaggedValueList getTaggedValues() {
		return this.taggedValues;
	}

	public void setTaggedValues(TaggedValueList taggedValues) {
		this.taggedValues = taggedValues;
	}

	public QualifiedNameList getNames() {
		return this.names;
	}

	public void setNames(QualifiedNameList names) {
		this.names = names;
	}

	public QualifiedName getStereotypeName() {
		return this.stereotypeName;
	}

	public void setStereotypeName(QualifiedName stereotypeName) {
		this.stereotypeName = stereotypeName;
	}

	public Stereotype getStereotype() {
		return this.stereotype;
	}

	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		if (this.taggedValues != null) {
			this.taggedValues.print(prefix + " ");
		}
		if (this.names != null) {
			this.names.print(prefix + " ");
		}
		if (this.stereotypeName != null) {
			this.stereotypeName.print(prefix + " ");
		}
	}
} // StereotypeAnnotation
