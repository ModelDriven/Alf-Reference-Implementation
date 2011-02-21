
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

import org.modeldriven.alf.syntax.expressions.impl.LogicalExpressionImpl;

/**
 * A binary expression with a logical operator.
 **/

public class LogicalExpression extends BinaryExpression {

	private Boolean isBitWise = null; // DERIVED
	private Boolean isBitStringConversion1 = null; // DERIVED
	private Boolean isBitStringConversion2 = null; // DERIVED

	public LogicalExpression() {
		this.impl = new LogicalExpressionImpl(this);
	}

	public LogicalExpressionImpl getImpl() {
		return (LogicalExpressionImpl) this.impl;
	}

	public Boolean getIsBitWise() {
		if (this.isBitWise == null) {
			this.isBitWise = this.getImpl().deriveIsBitWise();
		}
		return this.isBitWise;
	}

	public Boolean getIsBitStringConversion1() {
		if (this.isBitStringConversion1 == null) {
			this.isBitStringConversion1 = this.getImpl()
					.deriveIsBitStringConversion1();
		}
		return this.isBitStringConversion1;
	}

	public Boolean getIsBitStringConversion2() {
		if (this.isBitStringConversion2 == null) {
			this.isBitStringConversion2 = this.getImpl()
					.deriveIsBitStringConversion2();
		}
		return this.isBitStringConversion2;
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

	public String toString() {
		StringBuffer s = new StringBuffer(super.toString());
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

	public void print(String prefix) {
		super.print(prefix);
	}
} // LogicalExpression
