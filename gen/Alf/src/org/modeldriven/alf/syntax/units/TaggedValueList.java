
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
 * A set of tagged values for a stereotype application.
 **/

public class TaggedValueList extends SyntaxElement {

	private ArrayList<TaggedValue> taggedValue = new ArrayList<TaggedValue>();

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
		for (TaggedValue taggedValue : this.getTaggedValue()) {
			if (taggedValue != null) {
				taggedValue.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // TaggedValueList
