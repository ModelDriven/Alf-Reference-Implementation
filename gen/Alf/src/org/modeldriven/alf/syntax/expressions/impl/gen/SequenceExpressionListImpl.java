
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl.gen;

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

/**
 * A specification of the elements of a sequence using a list of expressions.
 **/

public class SequenceExpressionListImpl extends
		org.modeldriven.alf.syntax.expressions.impl.gen.SequenceElementsImpl {

	private List<Expression> element = new ArrayList<Expression>();

	public SequenceExpressionListImpl(SequenceExpressionList self) {
		super(self);
	}

	public SequenceExpressionList getSelf() {
		return (SequenceExpressionList) this.self;
	}

	public List<Expression> getElement() {
		return this.element;
	}

	public void setElement(List<Expression> element) {
		this.element = element;
	}

	public void addElement(Expression element) {
		this.element.add(element);
	}

	/**
	 * The multiplicity lower bound of the elements of a sequence expression
	 * list is given by the sum of the lower bounds of each of the expressions
	 * in the list.
	 **/
	public boolean sequenceExpressionListLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	/**
	 * The multiplicity upper bound of the elements of a sequence expression
	 * list is given by the sum of the upper bounds of each of the expressions
	 * in the list. If any of the expressions in the list have an unbounded
	 * upper bound, then the sequence expression list also has an unbounded
	 * upper bound.
	 **/
	public boolean sequenceExpressionListUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

} // SequenceExpressionListImpl
