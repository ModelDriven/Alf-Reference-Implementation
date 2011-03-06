
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.units.impl;

import org.modeldriven.alf.syntax.common.impl.SyntaxElementImpl;
import org.modeldriven.alf.syntax.units.TaggedValue;

/**
 * An assignment of a value to an attribute of an applied stereotype.
 **/

public class TaggedValueImpl extends SyntaxElementImpl {

	private String name = "";
	private String value = "";
	private String operator = "";

	public TaggedValueImpl(TaggedValue self) {
		super(self);
	}

	@Override
	public TaggedValue getSelf() {
		return (TaggedValue) this.self;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

} // TaggedValueImpl
