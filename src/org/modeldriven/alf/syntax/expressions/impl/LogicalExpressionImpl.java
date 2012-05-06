
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A binary expression with a logical operator.
 **/

public class LogicalExpressionImpl extends BinaryExpressionImpl {

	private Boolean isBitWise = null; // DERIVED
	private Boolean isBitStringConversion1 = null; // DERIVED
	private Boolean isBitStringConversion2 = null; // DERIVED

	public LogicalExpressionImpl(LogicalExpression self) {
		super(self);
	}

	@Override
	public LogicalExpression getSelf() {
		return (LogicalExpression) this.self;
	}

	public Boolean getIsBitWise() {
		if (this.isBitWise == null) {
			this.setIsBitWise(this.deriveIsBitWise());
		}
		return this.isBitWise;
	}

	public void setIsBitWise(Boolean isBitWise) {
		this.isBitWise = isBitWise;
	}

	public Boolean getIsBitStringConversion1() {
		if (this.isBitStringConversion1 == null) {
			this.setIsBitStringConversion1(this.deriveIsBitStringConversion1());
		}
		return this.isBitStringConversion1;
	}

	public void setIsBitStringConversion1(Boolean isBitStringConversion1) {
		this.isBitStringConversion1 = isBitStringConversion1;
	}

	public Boolean getIsBitStringConversion2() {
		if (this.isBitStringConversion2 == null) {
			this.setIsBitStringConversion2(this.deriveIsBitStringConversion2());
		}
		return this.isBitStringConversion2;
	}

	public void setIsBitStringConversion2(Boolean isBitStringConversion2) {
		this.isBitStringConversion2 = isBitStringConversion2;
	}

    /**
     * A logical expression is bit-wise if the type of its first operand is not
     * Boolean.
     **/
	protected Boolean deriveIsBitWise() {
        Expression operand1 = this.getSelf().getOperand1();
        ElementReference type = operand1 == null? null: operand1.getType();
        return type == null || !type.getImpl().isBoolean();
	}

    /**
     * BitString conversion is required if the first operand expression of a
     * logical expression has type Integer.
     **/
	protected Boolean deriveIsBitStringConversion1() {
        Expression operand1 = this.getSelf().getOperand1();
        ElementReference type = operand1 == null? null: operand1.getType();
        return type != null && type.getImpl().isInteger();
	}

    /**
     * BitString conversion is required if the second operand expression of a
     * logical expression has type Integer.
     **/
	protected Boolean deriveIsBitStringConversion2() {
        Expression operand2 = this.getSelf().getOperand2();
        ElementReference type = operand2 == null? null: operand2.getType();
        return type != null && type.getImpl().isInteger();
	}

	/**
	 * A logical expression has type Boolean if it is not bit-wise and type 
	 * BitString if it is.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return this.getSelf().getIsBitWise()? 
	                RootNamespace.getBitStringType():
	                RootNamespace.getBooleanType();
	}
	/**
	 * A logical expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
	@Override
	protected Integer deriveLower() {
	    LogicalExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    Expression operand2 = self.getOperand2();
	    return operand1 != null && operand1.getLower() == 0 ||
	           operand2 != null && operand2.getLower() == 0? 0: 1;
	}
	
	/**
	 * A logical expression has a multiplicity upper bound of 1.
	 **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
    
	/*
	 * Derivations
	 */
	
	public boolean logicalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean logicalExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean logicalExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
    public boolean logicalExpressionIsBitStringConversion1Derivation() {
        this.getSelf().getIsBitStringConversion1();
        return true;
    }

    public boolean logicalExpressionIsBitStringConversion2Derivation() {
        this.getSelf().getIsBitStringConversion2();
        return true;
    }

    public boolean logicalExpressionIsBitWiseDerivation() {
        this.getSelf().getIsBitWise();
        return true;
    }

	/*
	 * Constraints
	 */

	/**
	 * The operands of a logical expression must have type Boolean, BitString or
	 * Integer. However, if one of the operands is Boolean, the other must be 
	 * also.
	 **/
	public boolean logicalExpressionOperands() {
        LogicalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        ElementReference type1 = operand1 == null? null: operand1.getType();
        ElementReference type2 = operand2 == null? null: operand2.getType();
		return type1 != null && type2 != null &&
		       (type1.getImpl().isBoolean() && type2.getImpl().isBoolean() ||
		       (type1.getImpl().isBitString() || type1.getImpl().isInteger()) &&
		       (type2.getImpl().isBitString() || type2.getImpl().isInteger()));
	}

} // LogicalExpressionImpl
