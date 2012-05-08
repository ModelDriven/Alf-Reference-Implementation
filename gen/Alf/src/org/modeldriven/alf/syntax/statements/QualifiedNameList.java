
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.statements;

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

import org.modeldriven.alf.syntax.statements.impl.QualifiedNameListImpl;

/**
 * A group of qualified names.
 **/

public class QualifiedNameList extends SyntaxElement {

	public QualifiedNameList() {
		this.impl = new QualifiedNameListImpl(this);
	}

	public QualifiedNameList(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public QualifiedNameList(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public QualifiedNameListImpl getImpl() {
		return (QualifiedNameListImpl) this.impl;
	}

	public Collection<QualifiedName> getName() {
		return this.getImpl().getName();
	}

	public void setName(Collection<QualifiedName> name) {
		this.getImpl().setName(name);
	}

	public void addName(QualifiedName name) {
		this.getImpl().addName(name);
	}

	public void _deriveAll() {
		super._deriveAll();
		Collection<QualifiedName> name = this.getName();
		if (name != null) {
			for (Object _name : name.toArray()) {
				((QualifiedName) _name).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		Collection<QualifiedName> name = this.getName();
		if (name != null) {
			for (Object _name : name.toArray()) {
				((QualifiedName) _name).checkConstraints(violations);
			}
		}
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
		Collection<QualifiedName> name = this.getName();
		if (name != null && name.size() > 0) {
			System.out.println(prefix + " name:");
			for (Object _object : name.toArray()) {
				QualifiedName _name = (QualifiedName) _object;
				if (_name != null) {
					_name.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // QualifiedNameList
