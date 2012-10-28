
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

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

import org.modeldriven.alf.syntax.expressions.impl.SequenceElementsImpl;

/**
 * A specification of the elements of a sequence.
 **/

public abstract class SequenceElements extends SyntaxElement {

	public SequenceElements() {
	}

	public SequenceElements(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SequenceElements(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SequenceElementsImpl getImpl() {
		return (SequenceElementsImpl) this.impl;
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

	public void _deriveAll() {
		this.getUpper();
		this.getLower();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /upper:");
			s.append(this.getUpper());
		}
		if (includeDerived) {
			s.append(" /lower:");
			s.append(this.getLower());
		}
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
	}
} // SequenceElements
