
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

import org.modeldriven.alf.uml.Element;
import org.modeldriven.alf.uml.Profile;
import org.modeldriven.alf.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.SequenceExpressionListImpl;

/**
 * A specification of the elements of a sequence using a list of expressions.
 **/

public class SequenceExpressionList extends SequenceElements {

	public SequenceExpressionList() {
		this.impl = new SequenceExpressionListImpl(this);
	}

	public SequenceExpressionListImpl getImpl() {
		return (SequenceExpressionListImpl) this.impl;
	}

	public Collection<Expression> getElement() {
		return this.getImpl().getElement();
	}

	public void setElement(Collection<Expression> element) {
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
	 * The multiplicity lower bound of the elements of a sequence expression
	 * list is given by the sum of the lower bounds of each of the expressions
	 * in the list. If any of the expressions in the list have an unbounded
	 * upper bound, then the sequence expression list also has an unbounded
	 * upper bound.
	 **/
	public boolean sequenceExpressionListUpperDerivation() {
		return this.getImpl().sequenceExpressionListUpperDerivation();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
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
		for (Object _element : this.getElement().toArray()) {
			((Expression) _element).checkConstraints(violations);
		}
	}

	public String toString() {
		return "(" + this.hashCode() + ")" + this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
		Collection<Expression> element = this.getElement();
		if (element != null) {
			if (element.size() > 0) {
				System.out.println(prefix + " element:");
			}
			for (Object _object : element.toArray()) {
				Expression _element = (Expression) _object;
				if (_element != null) {
					_element.print(prefix + "  ");
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // SequenceExpressionList
