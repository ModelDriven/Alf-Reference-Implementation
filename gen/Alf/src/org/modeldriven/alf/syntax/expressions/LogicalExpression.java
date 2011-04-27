
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

import org.modeldriven.alf.syntax.expressions.impl.LogicalExpressionImpl;

/**
 * A binary expression with a logical operator.
 **/

public class LogicalExpression extends BinaryExpression {

	public LogicalExpression() {
		this.impl = new LogicalExpressionImpl(this);
	}

	public LogicalExpressionImpl getImpl() {
		return (LogicalExpressionImpl) this.impl;
	}

	public Boolean getIsBitWise() {
		return this.getImpl().getIsBitWise();
	}

	public void setIsBitWise(Boolean isBitWise) {
		this.getImpl().setIsBitWise(isBitWise);
	}

	public Boolean getIsBitStringConversion1() {
		return this.getImpl().getIsBitStringConversion1();
	}

	public void setIsBitStringConversion1(Boolean isBitStringConversion1) {
		this.getImpl().setIsBitStringConversion1(isBitStringConversion1);
	}

	public Boolean getIsBitStringConversion2() {
		return this.getImpl().getIsBitStringConversion2();
	}

	public void setIsBitStringConversion2(Boolean isBitStringConversion2) {
		this.getImpl().setIsBitStringConversion2(isBitStringConversion2);
	}

	/**
	 * A logical expression has type Boolean.
	 **/
	public boolean logicalExpressionTypeDerivation() {
		return this.getImpl().logicalExpressionTypeDerivation();
	}

	/**
	 * A logical expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean logicalExpressionLowerDerivation() {
		return this.getImpl().logicalExpressionLowerDerivation();
	}

	/**
	 * A logical expression has a multiplicity upper bound of 1.
	 **/
	public boolean logicalExpressionUpperDerivation() {
		return this.getImpl().logicalExpressionUpperDerivation();
	}

	/**
	 * The operands of a logical expression must have type Boolean.
	 **/
	public boolean logicalExpressionOperands() {
		return this.getImpl().logicalExpressionOperands();
	}

	/**
	 * BitString conversion is required if the first operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion1Derivation() {
		return this.getImpl()
				.logicalExpressionIsBitStringConversion1Derivation();
	}

	/**
	 * BitString conversion is required if the second operand expression of a
	 * shift expression has type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion2Derivation() {
		return this.getImpl()
				.logicalExpressionIsBitStringConversion2Derivation();
	}

	/**
	 * A logical expression is bit-wise if the type of its first operand is not
	 * Boolean.
	 **/
	public boolean logicalExpressionIsBitWiseDerivation() {
		return this.getImpl().logicalExpressionIsBitWiseDerivation();
	}

	public Collection<ConstraintViolation> checkConstraints() {
		Collection<ConstraintViolation> violations = new ArrayList<ConstraintViolation>();
		this.checkConstraints(violations);
		return violations;
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.logicalExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionTypeDerivation", this));
		}
		if (!this.logicalExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionLowerDerivation", this));
		}
		if (!this.logicalExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionUpperDerivation", this));
		}
		if (!this.logicalExpressionOperands()) {
			violations.add(new ConstraintViolation("logicalExpressionOperands",
					this));
		}
		if (!this.logicalExpressionIsBitStringConversion1Derivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionIsBitStringConversion1Derivation", this));
		}
		if (!this.logicalExpressionIsBitStringConversion2Derivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionIsBitStringConversion2Derivation", this));
		}
		if (!this.logicalExpressionIsBitWiseDerivation()) {
			violations.add(new ConstraintViolation(
					"logicalExpressionIsBitWiseDerivation", this));
		}
	}

	public String toString() {
		return this.getImpl().toString();
	}

	public String _toString() {
		StringBuffer s = new StringBuffer(super._toString());
		Boolean isBitWise = this.getIsBitWise();
		if (isBitWise != null) {
			s.append(" /isBitWise:");
			s.append(isBitWise);
		}
		Boolean isBitStringConversion1 = this.getIsBitStringConversion1();
		if (isBitStringConversion1 != null) {
			s.append(" /isBitStringConversion1:");
			s.append(isBitStringConversion1);
		}
		Boolean isBitStringConversion2 = this.getIsBitStringConversion2();
		if (isBitStringConversion2 != null) {
			s.append(" /isBitStringConversion2:");
			s.append(isBitStringConversion2);
		}
		return s.toString();
	}

	public void print() {
		this.print("");
	}

	public void print(String prefix) {
		super.print(prefix);
	}
} // LogicalExpression
