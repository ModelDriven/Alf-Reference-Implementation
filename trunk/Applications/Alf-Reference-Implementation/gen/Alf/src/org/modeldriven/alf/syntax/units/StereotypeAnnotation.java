
/*
 * Copyright 2009 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.nodes.*;
import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import java.util.ArrayList;

public class StereotypeAnnotation extends SyntaxNode {

	private TaggedValueList taggedValues = null;
	private QualifiedNameList names = null;
	private QualifiedName stereotype = null;

	public StereotypeAnnotation(QualifiedName stereotype) {
		this.stereotype = stereotype;
	} // StereotypeAnnotation

	public QualifiedName getStereotype() {
		return this.stereotype;
	} // getStereotype

	public void setNames(QualifiedNameList names) {
		this.names = names;
	} // setNames

	public QualifiedNameList getNames() {
		return this.names;
	} // getNames

	public void setTaggedValues(TaggedValueList taggedValues) {
		this.taggedValues = taggedValues;
	} // setTaggedValues

	public TaggedValueList getTaggedValues() {
		return this.taggedValues;
	} // getTaggedValues

	public String toString() {
		return "@" + this.getStereotype();
	} // toString

	public void print(String prefix) {
		super.print(prefix);

		if (prefix == null || prefix.equals("")) {
			prefix = " ";
		} else {
			prefix = prefix.charAt(0) + prefix;
		}

		QualifiedNameList names = this.getNames();
		if (names != null) {
			names.print(prefix);
		}

		TaggedValueList taggedValues = this.getTaggedValues();
		if (taggedValues != null) {
			taggedValues.print(prefix);
		}
	} // print

} // StereotypeAnnotation
