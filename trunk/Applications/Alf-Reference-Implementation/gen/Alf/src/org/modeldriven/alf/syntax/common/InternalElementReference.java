
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

import java.util.ArrayList;

import org.modeldriven.alf.syntax.common.impl.InternalElementReferenceImpl;

/**
 * A direct reference to a UML model element.
 **/

public class InternalElementReference extends ElementReference {

	private SyntaxElement element = null;

	public InternalElementReference() {
		this.impl = new InternalElementReferenceImpl(this);
	}

	public InternalElementReferenceImpl getImpl() {
		return (InternalElementReferenceImpl) this.impl;
	}

	public SyntaxElement getElement() {
		return this.element;
	}

	public void setElement(SyntaxElement element) {
		this.element = element;
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		SyntaxElement element = this.getElement();
		if (element != null) {
			element.print(prefix + " ");
		}
	}
} // InternalElementReference
