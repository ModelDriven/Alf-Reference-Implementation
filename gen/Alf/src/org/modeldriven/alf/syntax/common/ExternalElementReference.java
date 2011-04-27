
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.common.impl.ExternalElementReferenceImpl;

/**
 * A direct reference to a UML model element.
 **/

public class ExternalElementReference extends ElementReference {

	public ExternalElementReference() {
		this.impl = new ExternalElementReferenceImpl(this);
	}

	public ExternalElementReferenceImpl getImpl() {
		return (ExternalElementReferenceImpl) this.impl;
	}

	public Element getElement() {
		return this.getImpl().getElement();
	}

	public void setElement(Element element) {
		this.getImpl().setElement(element);
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
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Element element = this.getElement();
		if (element != null) {
			System.out.println(prefix + " element:" + element);
		}
	}
} // ExternalElementReference
