
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;

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
     * The type of an arithmetic expression is the same as the type of its
     * operands.
     **/
	@Override
	protected ElementReference deriveType() {
	    ArithmeticExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    Expression operand2 = self.getOperand2();
	    if (operand1 != null) {
	        return operand1.getType();
	    } else if (operand2 != null) {
	        return operand2.getType();
	    } else {
	        return null;
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
	    return (operand1 != null && operand1.getLower() == 0) ||
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
	 * The operands of an arithmetic expression must both have type Integer,
	 * unless the operator is +, in which case they may also both have type
	 * String.
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
		           type1.getImpl().isInteger() &&
		               type2.getImpl().isInteger() ||
		           operator != null && operator.equals("+") &&
		               type1.getImpl().isString() &&
		               type2.getImpl().isString());
		}
	}
	
} // ArithmeticExpressionImpl
