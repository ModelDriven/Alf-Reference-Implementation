/*******************************************************************************
 * Copyright 2011, 2017 Data Access Technologies, Inc. (Model Driven Solutions)
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
import org.modeldriven.alf.syntax.expressions.impl.RelationalExpressionImpl;

/**
 * A binary expression with a relational operator.
 **/

public class RelationalExpression extends BinaryExpression {

	public RelationalExpression() {
		this.impl = new RelationalExpressionImpl(this);
	}

	public RelationalExpression(Parser parser) {
		this();
		this.init(parser);
	}

	public RelationalExpression(ParsedElement element) {
		this();
		this.init(element);
	}

	@Override
    public RelationalExpressionImpl getImpl() {
		return (RelationalExpressionImpl) this.impl;
	}

	public Boolean getIsUnlimitedNatural() {
		return this.getImpl().getIsUnlimitedNatural();
	}

	public void setIsUnlimitedNatural(Boolean isUnlimitedNatural) {
		this.getImpl().setIsUnlimitedNatural(isUnlimitedNatural);
	}

    public Boolean getIsReal() {
        return this.getImpl().getIsReal();
    }

    public void setIsReal(Boolean isReal) {
        this.getImpl().setIsReal(isReal);
    }

    public Boolean getIsRealConversion1() {
        return this.getImpl().getIsRealConversion1();
    }

    public void setIsRealConversion1(Boolean isRealConversion1) {
        this.getImpl().setIsRealConversion1(isRealConversion1);
    }

    public Boolean getIsRealConversion2() {
        return this.getImpl().getIsRealConversion2();
    }

    public void setIsRealConversion2(Boolean isRealConversion2) {
        this.getImpl().setIsRealConversion2(isRealConversion2);
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
     * A relational expression is a Real comparison if either one
     * of its operands has type Real.
     **/
    public boolean relationalExpressionIsRealDerivation() {
        return this.getImpl()
                .relationalExpressionIsRealDerivation();
    }

	/**
	 * The type of a relational expression is Boolean.
	 **/
	public boolean relationalExpressionTypeDerivation() {
		return this.getImpl().relationalExpressionTypeDerivation();
	}

	/**
	 * A relational expression has a multiplicity lower bound of 1.
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
     * The operand expressions for a comparison operator must both be of a type
     * that conforms to type Natural, Integer or Real, or both be of a type that
     * conforms to type Natural or UnlimitedNatural.
     **/
	public boolean relationalExpressionOperandTypes() {
		return this.getImpl().relationalExpressionOperandTypes();
	}
	
    /**
     * A relational expression requires Real conversion of it is a Real
     * comparison and its first operand is of type Integer.
     */
    public boolean relationalExpressionIsRealConversion1Derivation() {
        return this.getImpl().relationalExpressionIsRealConversion1Derivation();
    }

    /**
     * A relational expression requires Real conversion of it is a Real
     * comparison and its second operand is of type Integer.
     */
    public boolean relationalExpressionIsRealConversion2Derivation() {
        return this.getImpl().relationalExpressionIsRealConversion2Derivation();
    }

	@Override
    public void _deriveAll() {
		this.getIsUnlimitedNatural();
		this.getIsReal();
		this.getIsRealConversion1();
		this.getIsRealConversion2();
		super._deriveAll();
	}
	
    /**
     * The minimum lower bound is 0 for operands of relational expressions (this
     * allows for the propagation of a null returned from an arithmetic
     * expression used as an operand).
     */
    @Override
    public Integer minLowerBound() {
        return this.getImpl().minLowerBound();
    }

	@Override
    public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
        if (!this.relationalExpressionIsUnlimitedNaturalDerivation()) {
            violations.add(new ConstraintViolation(
                    "relationalExpressionIsUnlimitedNaturalDerivation", this));
        }
        if (!this.relationalExpressionIsRealDerivation()) {
            violations.add(new ConstraintViolation(
                    "relationalExpressionIsRealDerivation", this));
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
        if (!this.relationalExpressionIsRealConversion1Derivation()) {
            violations.add(new ConstraintViolation(
                    "relationalExpressionIsRealConversion1Derivation", this));
        }
        if (!this.relationalExpressionIsRealConversion2Derivation()) {
            violations.add(new ConstraintViolation(
                    "relationalExpressionIsRealConversion2Derivation", this));
        }
	}

	@Override
    public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
        if (includeDerived) {
            s.append(" /isUnlimitedNatural:");
            s.append(this.getIsUnlimitedNatural());
        }
        if (includeDerived) {
            s.append(" /isReal:");
            s.append(this.getIsReal());
        }
        if (includeDerived) {
            s.append(" /isRealConversion1:");
            s.append(this.getIsRealConversion1());
        }
        if (includeDerived) {
            s.append(" /isRealConversion2:");
            s.append(this.getIsRealConversion2());
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
} // RelationalExpression
