
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.common;

import org.modeldriven.alf.parser.AlfParser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
import java.util.List;

import org.modeldriven.alf.syntax.common.impl.ElementReferenceImpl;

/**
 * A reference to a model element, either directly or via its Alf abstract
 * syntax representation. (NOTE: The definitions of all the helper operations of
 * ElementReference are specific to its subclasses.)
 **/

public abstract class ElementReference implements ParsedElement {

	protected ElementReferenceImpl impl;

	private String fileName = "";
	private int line = 0;
	private int column = 0;

	public ElementReference() {
	}

	public ElementReference(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ElementReference(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public ElementReferenceImpl getImpl() {
		return (ElementReferenceImpl) this.impl;
	}

	public String getFileName() {
		return this.fileName;
	}

	public int getLine() {
		return this.line;
	}

	public int getColumn() {
		return this.column;
	}

	public void setParserInfo(String fileName, int line, int column) {
		this.fileName = fileName;
		this.line = line;
		this.column = column;
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
