
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

import org.modeldriven.alf.syntax.units.impl.TaggedValueImpl;

/**
 * An assignment of a value to an attribute of an applied stereotype.
 **/

public class TaggedValue extends SyntaxElement {

	public TaggedValue() {
		this.impl = new TaggedValueImpl(this);
	}

	public TaggedValueImpl getImpl() {
		return (TaggedValueImpl) this.impl;
	}

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public String getValue() {
		return this.getImpl().getValue();
	}

	public void setValue(String value) {
		this.getImpl().setValue(value);
	}

	public String getOperator() {
		return this.getImpl().getOperator();
	}

	public void setOperator(String operator) {
		this.getImpl().setOperator(operator);
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
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

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // TaggedValue
