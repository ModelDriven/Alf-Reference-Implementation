
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
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.NaturalLiteralExpressionImpl;

/**
 * An expression that comprises a natural literal.
 **/

public class NaturalLiteralExpression extends LiteralExpression {

	public NaturalLiteralExpression() {
		this.impl = new NaturalLiteralExpressionImpl(this);
	}

	public NaturalLiteralExpressionImpl getImpl() {
		return (NaturalLiteralExpressionImpl) this.impl;
	}

	public String getImage() {
		return this.getImpl().getImage();
	}

	public void setImage(String image) {
		this.getImpl().setImage(image);
	}

	/**
	 * The type of a natural literal is the Alf library type Natural.
	 * 
	 * NOTE: If the context of a natural literal expression unambiguously
	 * requires either an Integer or an UnlimitedNatural value, then the result
	 * of the literal expression is implicitly downcast to the required type. If
	 * the context is ambiguous, however, than an explicit cast to Integer or
	 * UnlimitedNatural must be used.
	 **/
	public boolean naturalLiteralExpressionTypeDerivation() {
		return this.getImpl().naturalLiteralExpressionTypeDerivation();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.naturalLiteralExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"naturalLiteralExpressionTypeDerivation", this));
		}
	}

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
		s.append(" image:");
		s.append(this.getImage());
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // NaturalLiteralExpression
