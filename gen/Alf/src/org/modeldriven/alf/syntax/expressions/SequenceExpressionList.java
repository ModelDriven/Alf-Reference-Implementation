
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

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

import org.modeldriven.alf.syntax.expressions.impl.SequenceExpressionListImpl;

/**
 * A specification of the elements of a sequence using a list of expressions.
 **/

public class SequenceExpressionList extends SequenceElements {

	public SequenceExpressionList() {
		this.impl = new SequenceExpressionListImpl(this);
	}

	public SequenceExpressionList(AlfParser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public SequenceExpressionList(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public SequenceExpressionListImpl getImpl() {
		return (SequenceExpressionListImpl) this.impl;
	}

	public List<Expression> getElement() {
		return this.getImpl().getElement();
	}

	public void setElement(List<Expression> element) {
		this.getImpl().setElement(element);
	}

	public void addElement(Expression element) {
		this.getImpl().addElement(element);
	}

	/**
	 * The multiplicity lower bound of the elements of a sequence expression
	 * list is given by the sum of the lower bounds of each of the expressions
	 * in the list.
	 **/
	public boolean sequenceExpressionListLowerDerivation() {
		return this.getImpl().sequenceExpressionListLowerDerivation();
	}

	/**
	 * The multiplicity upper bound of the elements of a sequence expression
	 * list is given by the sum of the upper bounds of each of the expressions
	 * in the list. If any of the expressions in the list have an unbounded
	 * upper bound, then the sequence expression list also has an unbounded
	 * upper bound.
	 **/
	public boolean sequenceExpressionListUpperDerivation() {
		return this.getImpl().sequenceExpressionListUpperDerivation();
	}

	public void _deriveAll() {
		super._deriveAll();
		Collection<Expression> element = this.getElement();
		if (element != null) {
			for (Object _element : element.toArray()) {
				((Expression) _element).deriveAll();
			}
		}
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.sequenceExpressionListLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceExpressionListLowerDerivation", this));
		}
		if (!this.sequenceExpressionListUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"sequenceExpressionListUpperDerivation", this));
		}
		Collection<Expression> element = this.getElement();
		if (element != null) {
			for (Object _element : element.toArray()) {
				((Expression) _element).checkConstraints(violations);
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
		List<Expression> element = this.getElement();
		if (element != null && element.size() > 0) {
			System.out.println(prefix + " element:");
			for (Object _object : element.toArray()) {
				Expression _element = (Expression) _object;
				if (_element != null) {
					_element.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // SequenceExpressionList
