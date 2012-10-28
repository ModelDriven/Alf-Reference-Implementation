
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

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
import java.util.List;
import java.util.TreeSet;

import org.modeldriven.alf.syntax.common.impl.AssignedSourceImpl;

/**
 * An assignment of a source element that gives the value of a local name, along
 * with a record of the defined type (if any) and multiplicity of the local
 * name.
 **/

public class AssignedSource implements ParsedElement {

	protected AssignedSourceImpl impl;

	private String fileName = "";
	private int line = 0;
	private int column = 0;

	public AssignedSource() {
		this.impl = new AssignedSourceImpl(this);
	}

	public AssignedSource(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public AssignedSource(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public AssignedSourceImpl getImpl() {
		return (AssignedSourceImpl) this.impl;
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

	public String getName() {
		return this.getImpl().getName();
	}

	public void setName(String name) {
		this.getImpl().setName(name);
	}

	public SyntaxElement getSource() {
		return this.getImpl().getSource();
	}

	public void setSource(SyntaxElement source) {
		this.getImpl().setSource(source);
	}

	public Integer getUpper() {
		return this.getImpl().getUpper();
	}

	public void setUpper(Integer upper) {
		this.getImpl().setUpper(upper);
	}

	public Integer getLower() {
		return this.getImpl().getLower();
	}

	public void setLower(Integer lower) {
		this.getImpl().setLower(lower);
	}

	public ElementReference getType() {
		return this.getImpl().getType();
	}

	public void setType(ElementReference type) {
		this.getImpl().setType(type);
	}

	public void deriveAll() {
		this.getImpl().deriveAll();
	}

	public void _deriveAll() {
		ElementReference type = this.getType();
		if (type != null) {
			type.deriveAll();
		}
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new TreeSet<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		ElementReference type = this.getType();
		if (type != null) {
			type.checkConstraints(violations);
		}
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
		s.append(" name:");
		s.append(this.getName());
		s.append(" upper:");
		s.append(this.getUpper());
		s.append(" lower:");
		s.append(this.getLower());
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
		SyntaxElement source = this.getSource();
		if (source != null) {
			System.out.println(prefix + " source:"
					+ source.toString(includeDerived));
		}
		ElementReference type = this.getType();
		if (type != null) {
			System.out.println(prefix + " type:");
			type.print(prefix + "  ", includeDerived);
		}
	}
} // AssignedSource
