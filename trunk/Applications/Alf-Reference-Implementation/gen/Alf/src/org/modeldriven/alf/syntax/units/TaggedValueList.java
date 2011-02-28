
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

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.units.impl.TaggedValueListImpl;

/**
 * A set of tagged values for a stereotype application.
 **/

public class TaggedValueList extends SyntaxElement {

	private ArrayList<TaggedValue> taggedValue = new ArrayList<TaggedValue>();

	public TaggedValueList() {
		this.impl = new TaggedValueListImpl(this);
	}

	public TaggedValueListImpl getImpl() {
		return (TaggedValueListImpl) this.impl;
	}

	public ArrayList<TaggedValue> getTaggedValue() {
		return this.taggedValue;
	}

	public void setTaggedValue(ArrayList<TaggedValue> taggedValue) {
		this.taggedValue = taggedValue;
	}

	public void addTaggedValue(TaggedValue taggedValue) {
		this.taggedValue.add(taggedValue);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<TaggedValue> taggedValue = this.getTaggedValue();
		if (taggedValue != null) {
			if (taggedValue.size() > 0) {
				System.out.println(prefix + " taggedValue:");
			}
			for (TaggedValue _taggedValue : (ArrayList<TaggedValue>) taggedValue
					.clone()) {
				System.out.println(prefix + "  " + _taggedValue);
			}
		}
	}
} // TaggedValueList
