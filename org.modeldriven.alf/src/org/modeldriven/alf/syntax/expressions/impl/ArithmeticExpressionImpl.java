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
	private Boolean isReal = null; // DERIVED
    private Boolean isRealConversion1 = null; // DERIVED
    private Boolean isRealConversion2 = null; // DERIVED

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

    public Boolean getIsReal() {
        if (this.isReal == null) {
            this.setIsReal(this.deriveIsReal());
        }
        return this.isReal;
    }
    
    public void setIsReal(Boolean isReal) {
        this.isReal = isReal;
    }
    
	public void setIsConcatenation(Boolean isConcatenation) {
		this.isConcatenation = isConcatenation;
	}

    public Boolean getIsRealConversion1() {
        if (this.isRealConversion1 == null) {
            this.setIsRealConversion1(this.deriveIsRealConversion1());
        }
        return this.isRealConversion1;
    }
    
    public void setIsRealConversion1(Boolean isRealConversion1) {
        this.isRealConversion1 = isRealConversion1;
    }

    public Boolean getIsRealConversion2() {
        if (this.isRealConversion2 == null) {
            this.setIsRealConversion2(this.deriveIsRealConversion2());
        }
        return this.isRealConversion2;
    }
    
    public void setIsRealConversion2(Boolean isRealConversion2) {
        this.isRealConversion2 = isRealConversion2;
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
            ElementReference type2 = operand2.getType();
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
     * An arithmetic expression has a multiplicity lower bound of 0 if its
     * operator is "/" or if the lower bound if either operand expression is 0,
     * and 1 otherwise.
     **/
    @Override
	protected Integer deriveLower() {
        return this.getSelf().getOperator().equals("/")? 0: super.deriveLower();
	}
	
    /**
     * An arithmetic expression has a multiplicity upper bound of 1.
     **/
    @Override
	protected Integer deriveUpper() {
	    return 1;
	}

    /**
     * An arithmetic expression is a real computation if its type is Real.
     */
    protected Boolean deriveIsReal() {
        ElementReference type = this.getSelf().getType();
        return type != null && type.getImpl().isReal();
    }
    
    /**
     * Real conversion is required if the type of an arithmetic expression is
     * Real and the first operand expression has type Integer.
     **/
    protected Boolean deriveIsRealConversion1() {
        ArithmeticExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        ElementReference type1 = operand1 == null? null: operand1.getType();
        return self.getIsReal() && type1 != null && type1.getImpl().isInteger();
    }

    /**
     * Real conversion is required if the type of an arithmetic expression is
     * Real and the first operand expression has type Integer.
     **/
    protected Boolean deriveIsRealConversion2() {
        ArithmeticExpression self = this.getSelf();
        Expression operand2 = self.getOperand2();
        ElementReference type2 = operand2 == null? null: operand2.getType();
        return self.getIsReal() && type2 != null && type2.getImpl().isInteger();
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
	
    public boolean arithmeticExpressionIsRealDerivation() {
        this.getSelf().getIsReal();
        return true;
    }
    public boolean arithmeticExpressionIsRealConversion1Derivation() {
        this.getSelf().getIsRealConversion1();
        return true;
    }

    public boolean arithmeticExpressionIsRealConversion2Derivation() {
        this.getSelf().getIsRealConversion2();
        return true;
    }

	/*
	 * Constraints
	 */

    /**
     * The operands of an arithmetic expression must both have types that
     * conform to type Integer or Real, unless the operator is + or %. If the
     * operator is +, then both operands may also both have types that conform
     * to type String. If the operator is %, then both operands must have types
     * that conform to type Integer.
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
		    return operator == null || type1 != null && type2 != null && (
		           !operator.equals("%") &&
		           type1.getImpl().isIntegerOrReal()  &&
		           type2.getImpl().isIntegerOrReal() ||
                   operator.equals("+") &&
                   type1.getImpl().isString() &&
                   type2.getImpl().isString() ||
                   operator.equals("%") &&
                   type1.getImpl().isInteger() &&
                   type2.getImpl().isInteger());
		}
	}
	
    /**
     * The operands of an arithmetic expression must both have multiplicity
     * upper bounds of 1.
     */
    public boolean arithmeticExpressionOperandMultiplicity() {
        ArithmeticExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        return (operand1 == null || operand1.getUpper() == 1) &&
               (operand2 == null || operand2.getUpper() == 1);
    }
    
    /*
     * Helper Methods
     */
	
	/**
	 * Null arguments are allowed for arithmetic expressions to allow the
	 * propagation of null returned from a divide by zero.
	 */
	@Override
	public Boolean noNullArguments() {
	    return false;
	}
    
}
