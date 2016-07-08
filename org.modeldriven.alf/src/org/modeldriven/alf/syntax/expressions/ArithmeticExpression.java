/*******************************************************************************
 * Copyright 2011, 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions;

import org.modeldriven.alf.parser.Parser;
import org.modeldriven.alf.parser.Token;

import org.modeldriven.alf.syntax.common.*;
import java.util.Collection;
import org.modeldriven.alf.syntax.expressions.impl.ArithmeticExpressionImpl;

/**
 * A binary expression with an arithmetic operator.
 **/

public class ArithmeticExpression extends BinaryExpression {

	public ArithmeticExpression() {
		this.impl = new ArithmeticExpressionImpl(this);
	}

	public ArithmeticExpression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public ArithmeticExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public ArithmeticExpressionImpl getImpl() {
		return (ArithmeticExpressionImpl) this.impl;
	}

	public Boolean getIsConcatenation() {
		return this.getImpl().getIsConcatenation();
	}

	public void setIsConcatenation(Boolean isConcatenation) {
		this.getImpl().setIsConcatenation(isConcatenation);
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
	 * An arithmetic expression is a string concatenation expression if its type
	 * is String.
	 **/
	public boolean arithmeticExpressionIsConcatenationDerivation() {
		return this.getImpl().arithmeticExpressionIsConcatenationDerivation();
	}

    /**
     * If both operands of an arithmetic expression operator are of type
     * Integer, then the type of the expression is Integer. If one operand is of
     * type Real and the other Integer or both are of type Real, then the type
     * of the expression is Real. If both operands are of type String, then the
     * type of the expression is String. Otherwise the expression has no type.
     **/
	public boolean arithmeticExpressionTypeDerivation() {
		return this.getImpl().arithmeticExpressionTypeDerivation();
	}

	/**
	 * An arithmetic expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	public boolean arithmeticExpressionLowerDerivation() {
		return this.getImpl().arithmeticExpressionLowerDerivation();
	}

	/**
	 * An arithmetic expression has a multiplicity upper bound of 1.
	 **/
	public boolean arithmeticExpressionUpperDerivation() {
		return this.getImpl().arithmeticExpressionUpperDerivation();
	}

    /**
     * The operands of an arithmetic expression must both have types that
     * conform to type Integer or Real, unless the operator is + or %. If the
     * operator is +, then both operands may also both have types that conform
     * to type String. If the operator is %, then both operands must have types
     * that conform to type Integer.
     **/
	public boolean arithmeticExpressionOperandTypes() {
		return this.getImpl().arithmeticExpressionOperandTypes();
	}
	
    /**
     * An arithmetic expression is a real computation if its type is Real.
     */
	public boolean arithmeticExpressionIsRealDerivation() {
	    return this.getImpl().arithmeticExpressionIsRealDerivation();
	}

    /**
     * Real conversion is required if the type of an arithmetic expression is
     * Real and the first operand expression has type Integer.
     **/
    public boolean arithmeticExpressionIsRealConversion1Derivation() {
        return this.getImpl()
                .arithmeticExpressionIsRealConversion1Derivation();
    }

    /**
     * Real conversion is required if the type of an arithmetic expression is
     * Real and the second operand expression has type Integer.
     **/
    public boolean arithmeticExpressionIsRealConversion2Derivation() {
        return this.getImpl()
                .arithmeticExpressionIsRealConversion2Derivation();
    }

	public void _deriveAll() {
		this.getIsConcatenation();
		this.getIsRealConversion1();
		this.getIsRealConversion2();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.arithmeticExpressionIsConcatenationDerivation()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionIsConcatenationDerivation", this));
		}
		if (!this.arithmeticExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionTypeDerivation", this));
		}
		if (!this.arithmeticExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionLowerDerivation", this));
		}
		if (!this.arithmeticExpressionUpperDerivation()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionUpperDerivation", this));
		}
		if (!this.arithmeticExpressionOperandTypes()) {
			violations.add(new ConstraintViolation(
					"arithmeticExpressionOperandTypes", this));
		}
        if (!this.arithmeticExpressionIsRealDerivation()) {
            violations.add(new ConstraintViolation(
                    "arithmeticExpressionIsRealDerivation", this));
        }
        if (!this.arithmeticExpressionIsRealConversion1Derivation()) {
            violations.add(new ConstraintViolation(
                    "arithmeticExpressionIsRealConversion1Derivation", this));
        }
        if (!this.arithmeticExpressionIsRealConversion2Derivation()) {
            violations.add(new ConstraintViolation(
                    "arithmeticExpressionIsRealConversion2Derivation", this));
        }
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isConcatenation:");
			s.append(this.getIsConcatenation());
			s.append(" /isReal:");
			s.append(this.getIsReal());
            s.append(" /isRealConversion1:");
            s.append(this.getIsRealConversion1());
            s.append(" /isRealConversion2:");
            s.append(this.getIsRealConversion2());
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
	}
} // ArithmeticExpression
