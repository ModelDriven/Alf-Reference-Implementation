/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.RootNamespace;

/**
 * A binary expression with an arithmetic operator.
 **/

public class ArithmeticExpressionImpl extends BinaryExpressionImpl {

	private Boolean isConcatenation = null; // DERIVED

	public ArithmeticExpressionImpl(ArithmeticExpression self) {
		super(self);
	}

	@Override
	public ArithmeticExpression getSelf() {
		return (ArithmeticExpression) this.self;
	}

	public Boolean getIsConcatenation() {
		if (this.isConcatenation == null) {
			this.setIsConcatenation(this.deriveIsConcatenation());
		}
		return this.isConcatenation;
	}

	public void setIsConcatenation(Boolean isConcatenation) {
		this.isConcatenation = isConcatenation;
	}

	/**
	 * An arithmetic expression is a string concatenation expression if its type
	 * is String.
	 **/
	protected Boolean deriveIsConcatenation() {
	    ElementReference type = this.getSelf().getType();
		return type != null && type.getImpl().isString();
	}
	
    /**
     * If both operands of an arithmetic expression operator are of type
     * Integer, then the type of the expression is Integer. If one operand is of
     * type Real and the other Integer or both are of type Real, then the type
     * of the expression is Real. If both operands are of type String, then the
     * type of the expression is String. Otherwise the expression has no type.
     **/
	@Override
	protected ElementReference deriveType() {
	    ArithmeticExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    Expression operand2 = self.getOperand2();
	    if (operand1 == null || operand2 == null) {
	        return null;
	    } else {
            ElementReference type1 = operand1.getType();
            ElementReference type2 = operand1.getType();
	        return type1 == null || type2 == null? null:
	               type1.getImpl().isInteger() && type2.getImpl().isInteger()? 
                        RootNamespace.getRootScope().getIntegerType():
                   type1.getImpl().isIntegerOrReal() && type2.getImpl().isIntegerOrReal()? 
                        RootNamespace.getRootScope().getRealType():
	               type1.getImpl().isString() && type2.getImpl().isString()? 
	                    RootNamespace.getRootScope().getStringType():
	               null;
	    }
	}
	
    /**
     * An arithmetic expression has a multiplicity lower bound of 0 if the lower
     * bound if either operand expression is 0 and 1 otherwise.
     **/
    @Override
	protected Integer deriveLower() {
        ArithmeticExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        String operator = self.getOperator();
        // TODO: Update specification of arithmeticExpressionLowerDerivation for division.
        // NOTE: A division by zero will return null, so division should have
        // a multiplicity lower bound of 0.
	    return operator.equals("/") || 
	           (operand1 != null && operand1.getLower() == 0) ||
	           (operand2 != null && operand2.getLower() == 0)? 0: 1;
	}
	
    /**
     * An arithmetic expression has a multiplicity upper bound of 1.
     **/
    @Override
	protected Integer deriveUpper() {
	    return 1;
	}

	/*
	 * Derivations
	 */
	
	public boolean arithmeticExpressionIsConcatenationDerivation() {
		this.getSelf().getIsConcatenation();
		return true;
	}

	public boolean arithmeticExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean arithmeticExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean arithmeticExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

    /**
     * The operands of an arithmetic expression must both have type Integer or
     * Real, unless the operator is +, in which case they may also both have
     * type String.
     **/
	public boolean arithmeticExpressionOperandTypes() {
	    ArithmeticExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    Expression operand2 = self.getOperand2();
	    String operator = self.getOperator();
		if (operand1 == null || operand2 == null) {
		    return false;
		} else {
		    ElementReference type1 = operand1.getType();
		    ElementReference type2 = operand2.getType();
		    return type1 != null && type2 != null && (
		           type1.getImpl().isIntegerOrReal()  &&
		               type2.getImpl().isIntegerOrReal() ||
		           operator != null && operator.equals("+") &&
		               type1.getImpl().isString() &&
		               type2.getImpl().isString());
		}
	}
	
}
