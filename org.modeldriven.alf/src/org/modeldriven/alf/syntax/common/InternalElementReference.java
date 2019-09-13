/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;

import java.util.Collection;
import org.modeldriven.alf.syntax.common.impl.InternalElementReferenceImpl;

/**
 * A direct reference to a UML model element.
 **/

public class InternalElementReference extends ElementReference {

	public InternalElementReference() {
		this.impl = new InternalElementReferenceImpl(this);
	}

	public InternalElementReference(Parser parser) {
		this();
		this.init(parser);
	}

	public InternalElementReference(ParsedElement element) {
		this();
		this.init(element);
	}

	public InternalElementReferenceImpl getImpl() {
		return (InternalElementReferenceImpl) this.impl;
	}

	public SyntaxElement getElement() {
		return this.getImpl().getElement();
	}

	public void setElement(SyntaxElement element) {
		this.getImpl().setElement(element);
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
		SyntaxElement element = this.getElement();
		if (element != null) {
			System.out.println(prefix + " element:"
					+ element.toString(includeDerived));
		}
	}
} // InternalElementReference
