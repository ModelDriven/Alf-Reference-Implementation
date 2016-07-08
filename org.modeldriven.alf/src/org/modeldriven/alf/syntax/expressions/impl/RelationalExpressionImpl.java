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
import org.modeldriven.alf.syntax.units.*;

/**
 * A binary expression with a relational operator.
 **/

public class RelationalExpressionImpl extends BinaryExpressionImpl {

	private Boolean isUnlimitedNatural = null; // DERIVED
	private Boolean isReal = null; // DERIVED
	private Boolean isRealConversion1 = null; // DERIVED
	private Boolean isRealConversion2 = null; // DERIVED

	public RelationalExpressionImpl(RelationalExpression self) {
		super(self);
	}

	public RelationalExpression getSelf() {
		return (RelationalExpression) this.self;
	}

	public Boolean getIsUnlimitedNatural() {
		if (this.isUnlimitedNatural == null) {
			this.setIsUnlimitedNatural(this.deriveIsUnlimitedNatural());
		}
		return this.isUnlimitedNatural;
	}

	public void setIsUnlimitedNatural(Boolean isUnlimitedNatural) {
		this.isUnlimitedNatural = isUnlimitedNatural;
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
	 * A relational expression is an UnlimitedNatural comparison if either one
	 * of its operands has type UnlimitedNatural.
	 **/
    protected Boolean deriveIsUnlimitedNatural() {
        RelationalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        ElementReference type1 = operand1 == null? null: operand1.getType();
        ElementReference type2 = operand2 == null? null: operand2.getType();
        // Note: Checking for "not Integer" allows Natural values be treated as
        // Integer, even though they are also Unlimited Natural.
        return type1 != null && !type1.getImpl().isIntegerOrReal() ||
               type2 != null && !type2.getImpl().isIntegerOrReal();
    }

    /**
     * A relational expression is a Real comparison if either one
     * of its operands has type Real.
     **/
    protected Boolean deriveIsReal() {
        RelationalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        ElementReference type1 = operand1 == null? null: operand1.getType();
        ElementReference type2 = operand2 == null? null: operand2.getType();
        return type1 != null && type1.getImpl().isReal() ||
               type2 != null && type2.getImpl().isReal();
    }

	/**
	 * The type of a relational expression is Boolean.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getRootScope().getBooleanType();
	}
	
	/**
	 * A relational expression has a multiplicity lower bound of 0 if the lower
	 * bound if either operand expression is 0 and 1 otherwise.
	 **/
    @Override
    protected Integer deriveLower() {
        RelationalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        return operand1 != null && operand1.getLower() == 0 ||
               operand2 != null && operand2.getLower() == 0? 0: 1;
    }
	
	/**
	 * A relational expression has a multiplicity upper bound of 1.
	 **/
	@Override
	protected Integer deriveUpper() {
	    return 1;
	}
	
    /**
     * A relational expression requires Real conversion of it is a Real
     * comparison and its first operand is of type Integer.
     */	
	protected Boolean deriveIsRealConversion1() {
	    RelationalExpression self = this.getSelf();
	    Expression operand1 = self.getOperand1();
	    ElementReference type1 = operand1 == null? null: operand1.getType();
	    return self.getIsReal() && type1 != null && type1.getImpl().isInteger();
	}
	
    /**
     * A relational expression requires Real conversion of it is a Real
     * comparison and its second operand is of type Integer.
     */
    protected Boolean deriveIsRealConversion2() {
        RelationalExpression self = this.getSelf();
        Expression operand2 = self.getOperand2();
        ElementReference type2 = operand2 == null? null: operand2.getType();
        return self.getIsReal() && type2 != null && type2.getImpl().isInteger();
    }
    
	/*
	 * Derivations
	 */
	
	public boolean relationalExpressionIsUnlimitedNaturalDerivation() {
		this.getSelf().getIsUnlimitedNatural();
		return true;
	}

    public boolean relationalExpressionIsRealDerivation() {
        this.getSelf().getIsReal();
        return true;
    }

	public boolean relationalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean relationalExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean relationalExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
    public boolean relationalExpressionIsRealConversion1Derivation() {
        this.getSelf().getIsRealConversion1();
        return true;
    }

    public boolean relationalExpressionIsRealConversion2Derivation() {
        this.getSelf().getIsRealConversion2();
        return true;
    }

	/*
	 * Constraints
	 */

    /**
     * The operand expressions for a comparison operator must both be of a type
     * that conforms to type Natural, Integer or Real, or both be of a type that
     * conforms to type Natural or UnlimitedNatural.
     **/
	public boolean relationalExpressionOperandTypes() {
        RelationalExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        ElementReference type1 = operand1 == null? null: operand1.getType();
        ElementReference type2 = operand2 == null? null: operand2.getType();
		return type1 != null && type2 != null &&
		       // Note: The condition below checks for type conformance, not 
		       // equality. Therefore, the case of a Natural value is covered, 
		       // since it is both an Integer and UnlimitedNatural value.
		       (type1.getImpl().isIntegerOrReal() && type2.getImpl().isIntegerOrReal() ||
		       (type1.getImpl().isNatural() || type1.getImpl().isUnlimitedNatural()) && 
		           (type2.getImpl().isNatural() || type2.getImpl().isUnlimitedNatural()));
	}

}
