
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

import org.modeldriven.alf.syntax.expressions.impl.IsUniqueExpressionImpl;

/**
 * A sequence expansion expression with a isUnique.
 **/

public class IsUniqueExpression extends SequenceExpansionExpression {

	public IsUniqueExpression() {
		this.impl = new IsUniqueExpressionImpl(this);
	}

	public IsUniqueExpressionImpl getImpl() {
		return (IsUniqueExpressionImpl) this.impl;
	}

	/**
	 * An isUnique expression has the type Boolean.
	 **/
	public boolean isUniqueExpressionTypeDerivation() {
		return this.getImpl().isUniqueExpressionTypeDerivation();
	}

	/**
	 * An isUnique expression has a multiplicity lower bound of 1.
	 **/
	public boolean isUniqueExpressionLowerDerivation() {
		return this.getImpl().isUniqueExpressionLowerDerivation();
	}

	/**
	 * An isUnique expression has a multiplicity upper bound of 1.
	 **/
	public boolean isUniqueExpressionUpperDerivation() {
		return this.getImpl().isUniqueExpressionUpperDerivation();
	}

	/**
	 * The argument of an isUnique expression must have a multiplicity upper
	 * bound of 1.
	 **/
	public boolean isUniqueExpressionExpressionArgument() {
		return this.getImpl().isUniqueExpressionExpressionArgument();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.isUniqueExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"isUniqueExpressionTypeDerivation", this));
		}
		if (!this.isUniqueExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"isUniqueExpressionLowerDerivation", this));
		}
		if (!this.isUniqueExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"isUniqueExpressionUpperDerivation", this));
		}
		if (!this.isUniqueExpressionExpressionArgument()) {
			violations.add(new ConstraintViolation(
					"isUniqueExpressionExpressionArgument", this));
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
} // IsUniqueExpression