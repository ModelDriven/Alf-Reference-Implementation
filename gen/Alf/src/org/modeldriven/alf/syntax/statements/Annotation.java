
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
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

import org.modeldriven.alf.syntax.statements.impl.AnnotationImpl;

/**
 * An identified modification to the behavior of an annotated statement.
 **/

public class Annotation extends SyntaxElement {

	public Annotation() {
		this.impl = new AnnotationImpl(this);
	}

	public Annotation(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public Annotation(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public AnnotationImpl getImpl() {
		return (AnnotationImpl) this.impl;
	}

	public String getIdentifier() {
		return this.getImpl().getIdentifier();
	}

	public void setIdentifier(String identifier) {
		this.getImpl().setIdentifier(identifier);
	}

	public Collection<String> getArgument() {
		return this.getImpl().getArgument();
	}

	public void setArgument(Collection<String> argument) {
		this.getImpl().setArgument(argument);
	}

	public void addArgument(String argument) {
		this.getImpl().addArgument(argument);
	}

	public void _deriveAll() {
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		s.append(" identifier:");
		s.append(this.getIdentifier());
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
		Collection<String> argument = this.getArgument();
		if (argument != null && argument.size() > 0) {
			System.out.println(prefix + " argument:");
			for (Object _object : argument.toArray()) {
				String _argument = (String) _object;
				System.out.println(prefix + "  " + _argument);
			}
		}
	}
} // Annotation
