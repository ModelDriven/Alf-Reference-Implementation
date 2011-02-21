
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

import org.modeldriven.alf.syntax.common.impl.ExternalElementReferenceImpl;

/**
 * A direct reference to a UML model element.
 **/

public class ExternalElementReference extends ElementReference {

	private Element element = null;

	public ExternalElementReference() {
		this.impl = new ExternalElementReferenceImpl(this);
	}

	public ExternalElementReferenceImpl getImpl() {
		return (ExternalElementReferenceImpl) this.impl;
	}

	public Element getElement() {
		return this.element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		Element element = this.getElement();
		if (element != null) {
			System.out.println(prefix + " element:" + element);
		}
	}
} // ExternalElementReference
