
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

import org.modeldriven.alf.syntax.units.impl.TaggedValueImpl;

/**
 * An assignment of a value to an attribute of an applied stereotype.
 **/

public class TaggedValue extends SyntaxElement {

	private String name = "";
	private String value = "";
	private String operator = "";

	public TaggedValue() {
		this.impl = new TaggedValueImpl(this);
	}

	public TaggedValueImpl getImpl() {
		return (TaggedValueImpl) this.impl;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" name:");
		s.append(this.getName());
		s.append(" value:");
		s.append(this.getValue());
		s.append(" operator:");
		s.append(this.getOperator());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // TaggedValue
