
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.units.TaggedValue;
import org.modeldriven.alf.syntax.units.TaggedValueList;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A set of tagged values for a stereotype application.
 **/

public class TaggedValueListImpl extends SyntaxElementImpl {

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
	
	/*
	 * Helper Methods
	 */
	
	public String getValue(String name) {
	    String value = "";
	    for (TaggedValue taggedValue: this.getSelf().getTaggedValue()) {
	        if (taggedValue.getName().equals(name)) {
	            String operator = taggedValue.getOperator();
	            return (operator == null? "": operator) + taggedValue.getValue();
	        }
	    }
	    return value;
	}

} // TaggedValueListImpl
