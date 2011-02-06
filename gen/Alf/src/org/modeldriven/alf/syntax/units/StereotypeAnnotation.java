
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
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

public class StereotypeAnnotation extends SyntaxElement implements
		IStereotypeAnnotation {

	private ITaggedValueList taggedValues = null;
	private IQualifiedNameList names = null;
	private IQualifiedName stereotypeName = null;

	public ITaggedValueList getTaggedValues() {
		return this.taggedValues;
	}

	public void setTaggedValues(ITaggedValueList taggedValues) {
		this.taggedValues = taggedValues;
	}

	public IQualifiedNameList getNames() {
		return this.names;
	}

	public void setNames(IQualifiedNameList names) {
		this.names = names;
	}

	public IQualifiedName getStereotypeName() {
		return this.stereotypeName;
	}

	public void setStereotypeName(IQualifiedName stereotypeName) {
		this.stereotypeName = stereotypeName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ITaggedValueList taggedValues = this.getTaggedValues();
		if (taggedValues != null) {
			taggedValues.print(prefix + " ");
		}
		IQualifiedNameList names = this.getNames();
		if (names != null) {
			names.print(prefix + " ");
		}
		IQualifiedName stereotypeName = this.getStereotypeName();
		if (stereotypeName != null) {
			stereotypeName.print(prefix + " ");
		}
	}
} // StereotypeAnnotation
