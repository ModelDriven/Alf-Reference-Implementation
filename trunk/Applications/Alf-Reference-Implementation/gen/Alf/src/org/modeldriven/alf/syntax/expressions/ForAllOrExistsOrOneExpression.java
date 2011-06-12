
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

import org.modeldriven.alf.syntax.expressions.impl.ForAllOrExistsOrOneExpressionImpl;

/**
 * A sequence expansion expression with a forAll, exists or one operation.
 **/

public class ForAllOrExistsOrOneExpression extends SequenceExpansionExpression {

	public ForAllOrExistsOrOneExpression() {
		this.impl = new ForAllOrExistsOrOneExpressionImpl(this);
	}

	public ForAllOrExistsOrOneExpressionImpl getImpl() {
		return (ForAllOrExistsOrOneExpressionImpl) this.impl;
	}

	/**
	 * A forAll, exists or one expression has the type Boolean.
	 **/
	public boolean forAllOrExistsOrOneExpressionTypeDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionTypeDerivation();
	}

	/**
	 * A forAll, exists or one expression has a multiplicity lower bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionLowerDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionLowerDerivation();
	}

	/**
	 * A forAll, exists or one expression has a multiplicity upper bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionUpperDerivation() {
		return this.getImpl().forAllOrExistsOrOneExpressionUpperDerivation();
	}

	/**
	 * The argument of a forAll, exists or one expression must have type Boolean
	 * and a multiplicity upper bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionArgument() {
		return this.getImpl().forAllOrExistsOrOneExpressionArgument();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.forAllOrExistsOrOneExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"forAllOrExistsOrOneExpressionTypeDerivation", this));
		}
		if (!this.forAllOrExistsOrOneExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"forAllOrExistsOrOneExpressionLowerDerivation", this));
		}
		if (!this.forAllOrExistsOrOneExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"forAllOrExistsOrOneExpressionUpperDerivation", this));
		}
		if (!this.forAllOrExistsOrOneExpressionArgument()) {
			violations.add(new ConstraintViolation(
					"forAllOrExistsOrOneExpressionArgument", this));
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
	}
} // ForAllOrExistsOrOneExpression
