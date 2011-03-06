
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

import org.modeldriven.alf.syntax.units.impl.FormalParameterImpl;

/**
 * A typed element definition for the formal parameter of an activity or
 * operation.
 **/

public class FormalParameter extends TypedElementDefinition {

	public FormalParameter() {
		this.impl = new FormalParameterImpl(this);
	}

	public FormalParameterImpl getImpl() {
		return (FormalParameterImpl) this.impl;
	}

	public String getDirection() {
		return this.getImpl().getDirection();
	}

	public void setDirection(String direction) {
		this.getImpl().setDirection(direction);
	}

	/**
	 * Returns true if the annotation is for a stereotype that has a metaclass
	 * consistent with Parameter.
	 **/
	public Boolean annotationAllowed(StereotypeAnnotation annotation) {
		return this.getImpl().annotationAllowed(annotation);
	}

	/**
	 * Return true if the given member is a FormalParameter.
	 **/
	public Boolean isSameKindAs(Member member) {
		return this.getImpl().isSameKindAs(member);
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" direction:");
		s.append(this.getDirection());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // FormalParameter
