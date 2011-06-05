
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

import org.omg.uml.Element;
import org.omg.uml.Profile;
import org.omg.uml.Stereotype;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modeldriven.alf.syntax.expressions.impl.LiteralExpressionImpl;

/**
 * An expression that comprises a primitive literal.
 **/

public abstract class LiteralExpression extends Expression {

	public LiteralExpressionImpl getImpl() {
		return (LiteralExpressionImpl) this.impl;
	}

	/**
	 * The type of a literal expression is given by the type of the literal, as
	 * defined for each subclass below.
	 **/
	public boolean literalExpressionTypeDerivation() {
		return this.getImpl().literalExpressionTypeDerivation();
	}

	/**
	 * The multiplicity upper bound of a literal expression is always 1.
	 **/
	public boolean literalExpressionUpperDerivation() {
		return this.getImpl().literalExpressionUpperDerivation();
	}

	/**
	 * The multiplicity lower bound of a literal expression is always 1.
	 **/
	public boolean literalExpressionLowerDerivation() {
		return this.getImpl().literalExpressionLowerDerivation();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.literalExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"literalExpressionTypeDerivation", this));
		}
		if (!this.literalExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"literalExpressionUpperDerivation", this));
		}
		if (!this.literalExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"literalExpressionLowerDerivation", this));
		}
	}

	public String toString() {
		return this.getImpl().toString();
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
	}
} // LiteralExpression
