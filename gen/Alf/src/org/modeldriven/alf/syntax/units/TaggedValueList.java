
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.units.impl.TaggedValueListImpl;

/**
 * A set of tagged values for a stereotype application.
 **/

public class TaggedValueList extends SyntaxElement {

	public TaggedValueList() {
		this.impl = new TaggedValueListImpl(this);
	}

	public TaggedValueListImpl getImpl() {
		return (TaggedValueListImpl) this.impl;
	}

	public Collection<TaggedValue> getTaggedValue() {
		return this.getImpl().getTaggedValue();
	}

	public void setTaggedValue(Collection<TaggedValue> taggedValue) {
		this.getImpl().setTaggedValue(taggedValue);
	}

	public void addTaggedValue(TaggedValue taggedValue) {
		this.getImpl().addTaggedValue(taggedValue);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<TaggedValue> taggedValue = this.getTaggedValue();
		if (taggedValue != null) {
			if (taggedValue.size() > 0) {
				System.out.println(prefix + " taggedValue:");
			}
			for (TaggedValue _taggedValue : taggedValue) {
				System.out.println(prefix + "  " + _taggedValue);
			}
		}
	}
} // TaggedValueList
