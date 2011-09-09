
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units;

import org.modeldriven.alf.parser.AlfParser;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

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

	public TaggedValueList(AlfParser parser) {
		this();
		this.setParserInfo(parser.getFileName(), parser.getLine(), parser
				.getColumn());
	}

	public TaggedValueList(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
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

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
		Collection<TaggedValue> taggedValue = this.getTaggedValue();
		if (taggedValue != null && taggedValue.size() > 0) {
			System.out.println(prefix + " taggedValue:");
			for (Object _object : taggedValue.toArray()) {
				TaggedValue _taggedValue = (TaggedValue) _object;
				System.out.println(prefix + "  "
						+ _taggedValue.toString(includeDerived));
			}
		}
	}
} // TaggedValueList
