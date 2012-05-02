
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl.gen;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

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

/**
 * A set of tagged values for a stereotype application.
 **/

public class TaggedValueListImpl extends
		org.modeldriven.alf.syntax.common.impl.gen.SyntaxElementImpl {

	private Collection<TaggedValue> taggedValue = new ArrayList<TaggedValue>();

	public TaggedValueListImpl(TaggedValueList self) {
		super(self);
	}

	public TaggedValueList getSelf() {
		return (TaggedValueList) this.self;
	}

	public Collection<TaggedValue> getTaggedValue() {
		return this.taggedValue;
	}

	public void setTaggedValue(Collection<TaggedValue> taggedValue) {
		this.taggedValue = taggedValue;
	}

	public void addTaggedValue(TaggedValue taggedValue) {
		this.taggedValue.add(taggedValue);
	}

} // TaggedValueListImpl
