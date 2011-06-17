
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

import org.modeldriven.alf.syntax.expressions.impl.NamedTupleImpl;

/**
 * A tuple in which the arguments are matched to parameters by name.
 **/

public class NamedTuple extends Tuple {

	public NamedTuple() {
		this.impl = new NamedTupleImpl(this);
	}

	public NamedTupleImpl getImpl() {
		return (NamedTupleImpl) this.impl;
	}

	public List<NamedExpression> getNamedExpression() {
		return this.getImpl().getNamedExpression();
	}

	public void setNamedExpression(List<NamedExpression> namedExpression) {
		this.getImpl().setNamedExpression(namedExpression);
	}

	public void addNamedExpression(NamedExpression namedExpression) {
		this.getImpl().addNamedExpression(namedExpression);
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		for (Object _namedExpression : this.getNamedExpression().toArray()) {
			((NamedExpression) _namedExpression).checkConstraints(violations);
		}
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean includeDerived) {
		return "(" + this.hashCode() + ")"
				+ this.getImpl().toString(includeDerived);
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
		List<NamedExpression> namedExpression = this.getNamedExpression();
		if (namedExpression != null && namedExpression.size() > 0) {
			System.out.println(prefix + " namedExpression:");
			for (Object _object : namedExpression.toArray()) {
				NamedExpression _namedExpression = (NamedExpression) _object;
				if (_namedExpression != null) {
					_namedExpression.print(prefix + "  ", includeDerived);
				} else {
					System.out.println(prefix + "  null");
				}
			}
		}
	}
} // NamedTuple
