
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
 * A set of tagged values for a stereotype application.
 **/

public class TaggedValueList extends SyntaxElement implements ITaggedValueList {

	private ArrayList<ITaggedValue> taggedValue = new ArrayList<ITaggedValue>();

	public ArrayList<ITaggedValue> getTaggedValue() {
		return this.taggedValue;
	}

	public void setTaggedValue(ArrayList<ITaggedValue> taggedValue) {
		this.taggedValue = taggedValue;
	}

	public void addTaggedValue(ITaggedValue taggedValue) {
		this.taggedValue.add(taggedValue);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		for (ITaggedValue taggedValue : this.getTaggedValue()) {
			if (taggedValue != null) {
				taggedValue.print(prefix + " ");
			} else {
				System.out.println(prefix + " null");
			}
		}
	}
} // TaggedValueList
