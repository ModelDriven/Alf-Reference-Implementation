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

import org.modeldriven.alf.syntax.expressions.impl.EqualityExpressionImpl;

/**
 * A binary expression that tests the equality of its operands.
 **/

public class EqualityExpression extends BinaryExpression {

	public EqualityExpression() {
		this.impl = new EqualityExpressionImpl(this);
	}

	public EqualityExpression(Parser parser) {
		this();
		Token token = parser.getToken(0);
		if (token.next != null) {
			token = token.next;
		}
		this.setParserInfo(parser.getFileName(), token.beginLine,
				token.beginColumn);
	}

	public EqualityExpression(ParsedElement element) {
		this();
		this.setParserInfo(element.getFileName(), element.getLine(), element
				.getColumn());
	}

	public EqualityExpressionImpl getImpl() {
		return (EqualityExpressionImpl) this.impl;
	}

	public Boolean getIsNegated() {
		return this.getImpl().getIsNegated();
	}

	public void setIsNegated(Boolean isNegated) {
		this.getImpl().setIsNegated(isNegated);
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
	 * An equality expression is negated if its operator is "!=".
	 **/
	public boolean equalityExpressionIsNegatedDerivation() {
		return this.getImpl().equalityExpressionIsNegatedDerivation();
	}

	/**
	 * An equality expression has type Boolean.
	 **/
	public boolean equalityExpressionTypeDerivation() {
		return this.getImpl().equalityExpressionTypeDerivation();
	}

	/**
	 * An equality expression has a multiplicity lower bound of 1.
	 **/
	public boolean equalityExpressionLowerDerivation() {
		return this.getImpl().equalityExpressionLowerDerivation();
	}

	/**
	 * An equality expression has a multiplicity upper bound of 1.
	 **/
	public boolean equalityExpressionUpperDerivation() {
		return this.getImpl().equalityExpressionUpperDerivation();
	}
	
    /**
     * An equality expression requires real conversion if the first operand is
     * of type Integer and the second is of type Real.
     */
    public boolean equalityExpressionIsRealConversion1Derivation() {
        return this.getImpl().equalityExpressionIsRealConversion1Derivation();
    }

    /**
     * An equality expression requires real conversion if the first operand is
     * of type Real and the second is of type Integer.
     */
    public boolean equalityExpressionIsRealConversion2Derivation() {
        return this.getImpl().equalityExpressionIsRealConversion2Derivation();
    }

    /**
     * The minimum lower bound is 0 for operands of equality expressions.
     **/
    @Override
    public Integer minLowerBound() {
        return 0;
    }
    
    /**
     * If the one operand expression has multiplicity 0..0, then check the other
     * operand expression for known nulls and non-nulls, using the exclusive-or
     * of the given truth condition and whether the equality expression is
     * negated or not.
     */
    @Override
    public Collection<AssignedSource> adjustAssignments(
            Collection<AssignedSource> assignments, boolean condition) {
        return this.getImpl().adjustAssignments(assignments, condition);
    }
    
	public void _deriveAll() {
		this.getIsNegated();
		this.getIsRealConversion1();
		this.getIsRealConversion2();
		super._deriveAll();
	}

	public void checkConstraints(Collection<ConstraintViolation> violations) {
		super.checkConstraints(violations);
		if (!this.equalityExpressionIsNegatedDerivation()) {
			violations.add(new ConstraintViolation(
					"equalityExpressionIsNegatedDerivation", this));
		}
		if (!this.equalityExpressionTypeDerivation()) {
			violations.add(new ConstraintViolation(
					"equalityExpressionTypeDerivation", this));
		}
		if (!this.equalityExpressionLowerDerivation()) {
			violations.add(new ConstraintViolation(
					"equalityExpressionLowerDerivation", this));
		}
        if (!this.equalityExpressionUpperDerivation()) {
            violations.add(new ConstraintViolation(
                    "equalityExpressionUpperDerivation", this));
        }
        if (!this.equalityExpressionIsRealConversion1Derivation()) {
            violations.add(new ConstraintViolation(
                    "equalityExpressionIsRealConversion1Derivation", this));
        }
        if (!this.equalityExpressionIsRealConversion2Derivation()) {
            violations.add(new ConstraintViolation(
                    "equalityExpressionIsRealConversion2Derivation", this));
        }
	}

	public String _toString(boolean includeDerived) {
		StringBuffer s = new StringBuffer(super._toString(includeDerived));
		if (includeDerived) {
			s.append(" /isNegated:");
			s.append(this.getIsNegated());
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
} // EqualityExpression
