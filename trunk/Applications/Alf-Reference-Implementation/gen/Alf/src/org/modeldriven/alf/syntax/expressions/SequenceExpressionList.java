
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.syntax.*;
import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.statements.*;
import org.modeldriven.alf.syntax.units.*;

import org.omg.uml.*;

import java.util.ArrayList;

import org.modeldriven.alf.syntax.expressions.impl.SequenceExpressionListImpl;

/**
 * A specification of the elements of a sequence using a list of expressions.
 **/

public class SequenceExpressionList extends SequenceElements {

	private ArrayList<Expression> element = new ArrayList<Expression>();

	public SequenceExpressionList() {
		this.impl = new SequenceExpressionListImpl(this);
	}

	public SequenceExpressionListImpl getImpl() {
		return (SequenceExpressionListImpl) this.impl;
	}

	public ArrayList<Expression> getElement() {
		return this.element;
	}

	public void setElement(ArrayList<Expression> element) {
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
		return this.getImpl().sequenceExpressionListLowerDerivation();
	}

	/**
	 * The multiplicity lower bound of the elements of a sequence expression
	 * list is given by the sum of the lower bounds of each of the expressions
	 * in the list. If any of the expressions in the list have an unbounded
	 * upper bound, then the sequence expression list also has an unbounded
	 * upper bound.
	 **/
	public boolean sequenceExpressionListUpperDerivation() {
		return this.getImpl().sequenceExpressionListUpperDerivation();
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		return s.toString();
	}

	public void print(String prefix) {
		super.print(prefix);
		ArrayList<Expression> element = this.getElement();
		if (element != null) {
			if (element.size() > 0) {
				System.out.println(prefix + " element:");
			}
			for (Expression item : this.getElement()) {
				if (item != null) {
					item.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // SequenceExpressionList
