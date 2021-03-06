/*******************************************************************************
 * Copyright 2011, 2018 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import java.util.Collection;

import org.modeldriven.alf.parser.ParsedElement;
import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.syntax.common.ConstraintViolation;
import org.modeldriven.alf.syntax.expressions.impl.LogicalExpressionImpl;

/**
 * A binary expression with a logical operator.
 **/

public class LogicalExpression extends BinaryExpression {

	public LogicalExpression() {
		this.impl = new LogicalExpressionImpl(this);
	}

	public LogicalExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public LogicalExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
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
	 * A logical expression has type Boolean if it is not bit-wise and type
	 * BitString if it is bit-wise.
	 **/
	public boolean logicalExpressionTypeDerivation() {
		return this.getImpl().logicalExpressionTypeDerivation();
	}

	/**
	 * A logical expression has a multiplicity lower bound of 1.
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
     * The operands of a logical expression must have a type that conforms to
     * type Boolean, Integer or BitString. However, if one of the operands is
     * Boolean, then the other must also be Boolean.
     **/
	public boolean logicalExpressionOperands() {
		return this.getImpl().logicalExpressionOperands();
	}

	/**
	 * BitString conversion is required if the first operand expression of a
	 * logical expression has a type that conforms to type Integer.
	 **/
	public boolean logicalExpressionIsBitStringConversion1Derivation() {
		return this.getImpl()
				.logicalExpressionIsBitStringConversion1Derivation();
	}

	/**
	 * BitString conversion is required if the second operand expression of a
	 * logical expression has a type that conforms to type Integer.
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

	@Override
    public void _deriveAll() {
		this.getIsBitWise();
		this.getIsBitStringConversion1();
		this.getIsBitStringConversion2();
		super._deriveAll();
	}

	@Override
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

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isBitWise:");
			s.append(this.getIsBitWise());
		}
		if (includeDerived) {
			s.append(" /isBitStringConversion1:");
			s.append(this.getIsBitStringConversion1());
		}
		if (includeDerived) {
			s.append(" /isBitStringConversion2:");
			s.append(this.getIsBitStringConversion2());
		}
		return s.toString();
	}

	@Override
    public void print() {
		this.print("", false);
	}

	@Override
    public void print(boolean includeDerived) {
		this.print("", includeDerived);
	}

	@Override
    public void print(String prefix, boolean includeDerived) {
		super.print(prefix, includeDerived);
	}
} // LogicalExpression
