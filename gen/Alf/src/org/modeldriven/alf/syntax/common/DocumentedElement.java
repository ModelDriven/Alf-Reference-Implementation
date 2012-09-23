
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
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


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.common.impl.DocumentedElementImpl;
import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

/**
 * A syntax element that has documentation comments associated with it.
 **/

public abstract class DocumentedElement extends SyntaxElement {

	public DocumentedElement() {
	}

	public DocumentedElement(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public DocumentedElement(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public DocumentedElementImpl getImpl() {
		return (DocumentedElementImpl) this.impl;
	}

	public Collection<String> getDocumentation() {
		return this.getImpl().getDocumentation();
	}

	public void setDocumentation(Collection<String> documentation) {
		this.getImpl().setDocumentation(documentation);
	}

	public void addDocumentation(String documentation) {
		this.getImpl().addDocumentation(documentation);
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
		Collection<String> documentation = this.getDocumentation();
		if (documentation != null && documentation.size() > 0) {
			System.out.println(prefix + " documentation:");
			for (Object _object : documentation.toArray()) {
				String _documentation = (String) _object;
				System.out.println(prefix + "  " + _documentation);
			}
		}
	}
} // DocumentedElement
