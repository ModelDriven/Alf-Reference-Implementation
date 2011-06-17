
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

import org.modeldriven.alf.syntax.expressions.impl.UnboundedLiteralExpressionImpl;

/**
 * An expression that comprises an unbounded value literal.
 **/

public class UnboundedLiteralExpression extends LiteralExpression {

	public UnboundedLiteralExpression() {
		this.impl = new UnboundedLiteralExpressionImpl(this);
	}

	public UnboundedLiteralExpressionImpl getImpl() {
		return (UnboundedLiteralExpressionImpl) this.impl;
	}

	/**
	 * The type of an unbounded literal expression is UnlimitedNatural.
	 **/
	public boolean unboundedLiteralExpressionTypeDerivation() {
		return this.getImpl().unboundedLiteralExpressionTypeDerivation();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.unboundedLiteralExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"unboundedLiteralExpressionTypeDerivation", this));
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
	}
} // UnboundedLiteralExpression
