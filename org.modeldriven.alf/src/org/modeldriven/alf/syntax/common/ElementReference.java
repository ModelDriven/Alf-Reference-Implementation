/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common;

import java.util.Collection;
import java.util.TreeSet;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;

/**
 * A reference to a model element, either directly or via its Alf abstract
 * syntax representation. (NOTE: The definitions of all the helper operations of
 * ElementReference are specific to its subclasses.)
 **/

public abstract class ElementReference extends ParsedElement {

	protected ElementReferenceImpl impl;

	public ElementReferenceImpl getImpl() {
		return (ElementReferenceImpl) this.impl;
	}

	public void deriveAll() {
		this.getImpl().deriveAll();
	}

	public void _deriveAll() {
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new TreeSet<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
	}

	public String getId() {
		return Integer.toHexString(this.hashCode());
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.getId() + ")"
				+ this.getImpl().toString(includeDerived);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(this.getClass().getSimpleName());
		return s.toString();
	}

	public void print() {
		this.print("", false);
	}

	public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	public void print(String prefix, boolean includeDerived) {
		System.out.println(prefix + "[" + this.getId() + "]"
				+ this._toString(includeDerived));
	}
} // ElementReference
