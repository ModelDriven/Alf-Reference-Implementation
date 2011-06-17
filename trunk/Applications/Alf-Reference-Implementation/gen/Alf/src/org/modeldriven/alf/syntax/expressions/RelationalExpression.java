
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

import org.modeldriven.alf.syntax.expressions.impl.RelationalExpressionImpl;

/**
 * A binary expression with a relational operator.
 **/

public class RelationalExpression extends BinaryExpression {

	public RelationalExpression() {
		this.impl = new RelationalExpressionImpl(this);
	}

	public RelationalExpressionImpl getImpl() {
		return (RelationalExpressionImpl) this.impl;
	}

	public Boolean getIsUnlimitedNatural() {
		return this.getImpl().getIsUnlimitedNatural();
	}

	public void setIsUnlimitedNatural(Boolean isUnlimitedNatural) {
		this.getImpl().setIsUnlimitedNatural(isUnlimitedNatural);
	}

	/**
	 * A relational expression is an UnlimitedNatural comparison if either one
	 * of its operands has type UnlimitedNatural.
	 **/
	public boolean relationalExpressionIsUnlimitedNaturalDerivation() {
		return this.getImpl()
				.relationalExpressionIsUnlimitedNaturalDerivation();
	}

	/**
	 * The type of a relational expression is Boolean.
	 **/
	public boolean relationalExpressionTypeDerivation() {
		return this.getImpl().relationalExpressionTypeDerivation();
	}

	/**
	 * A relational expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean relationalExpressionLowerDerivation() {
		return this.getImpl().relationalExpressionLowerDerivation();
	}

	/**
	 * A relational expression has a multiplicity upper bound of 1.
	 **/
	public boolean relationalExpressionUpperDerivation() {
		return this.getImpl().relationalExpressionUpperDerivation();
	}

	/**
	 * The operand expressions for a comparison operator must have type Integer,
	 * UnlimitedNatural or Natural. However, it is not allowed to have one
	 * operand expression be Integer and the other be UnlimitedNatural.
	 **/
	public boolean relationalExpressionOperandTypes() {
		return this.getImpl().relationalExpressionOperandTypes();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.relationalExpressionIsUnlimitedNaturalDerivation()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionIsUnlimitedNaturalDerivation", this));
		}
		if (!this.relationalExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionTypeDerivation", this));
		}
		if (!this.relationalExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionLowerDerivation", this));
		}
		if (!this.relationalExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionUpperDerivation", this));
		}
		if (!this.relationalExpressionOperandTypes()) {
			violations.add(new ConstraintViolation(
					"relationalExpressionOperandTypes", this));
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
		if (includeDerived) {
			s.append(" /isUnlimitedNatural:");
			s.append(this.getIsUnlimitedNatural());
		}
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
		if (includeDerived) {
		}
	}
} // RelationalExpression
