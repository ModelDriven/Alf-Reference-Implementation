/*******************************************************************************
 * Copyright 2011-2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import java.util.Map;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * A binary expression that tests the equality of its operands.
 **/

public class EqualityExpressionImpl extends BinaryExpressionImpl {

	private Boolean isNegated = null; // DERIVED
	private Boolean isRealConversion1 = null; // DERIVED
	private Boolean isRealConversion2 = null; // DERIVED

	public EqualityExpressionImpl(EqualityExpression self) {
		super(self);
	}

	@Override
	public EqualityExpression getSelf() {
		return (EqualityExpression) this.self;
	}

	public Boolean getIsNegated() {
		if (this.isNegated == null) {
			this.setIsNegated(this.deriveIsNegated());
		}
		return this.isNegated;
	}

	public void setIsNegated(Boolean isNegated) {
		this.isNegated = isNegated;
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
	 * An equality expression is negated if its operator is "!=".
	 **/
	protected Boolean deriveIsNegated() {
	    String operator = this.getSelf().getOperator();
		return operator != null && operator.equals("!=");
	}

	/**
	 * An equality expression has type Boolean.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getRootScope().getBooleanType();
	}
	
	/**
	 * An equality expression has a multiplicity lower bound of 1.
	 **/
    @Override
    protected Integer deriveLower() {
        return 1;
    }
    
    /**
     * An equality expression requires real conversion if the first operand is
     * of type Integer and the second is of type Real.
     */
    protected Boolean deriveIsRealConversion1() {
        EqualityExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        ElementReference type1 = operand1 == null? null: operand1.getType();
        ElementReference type2 = operand2 == null? null: operand2.getType();
        return type1 != null && type2 != null &&
               type1.getImpl().isInteger() && type2.getImpl().isReal();
    }
    
    /**
     * An equality expression requires real conversion if the first operand is
     * of type Real and the second is of type Integer.
     */
    protected Boolean deriveIsRealConversion2() {
        EqualityExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        ElementReference type1 = operand1 == null? null: operand1.getType();
        ElementReference type2 = operand2 == null? null: operand2.getType();
        return type1 != null && type2 != null &&
               type1.getImpl().isReal() && type2.getImpl().isInteger();
    }

    /*
	 * Derivations
	 */
	
	public boolean equalityExpressionIsNegatedDerivation() {
		this.getSelf().getIsNegated();
		return true;
	}

	public boolean equalityExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean equalityExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean equalityExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
    public boolean equalityExpressionIsRealConversion1Derivation() {
        this.getSelf().getIsRealConversion1();
        return true;
    }

    public boolean equalityExpressionIsRealConversion2Derivation() {
        this.getSelf().getIsRealConversion2();
        return true;
    }

	/*
	 * Helper methods
	 */
	
    /**
     * Returns false for an equality expression.
     **/
    @Override
    public Boolean noNullArguments() {
        return false;
    }
    
    /**
     * If the one operand expression has multiplicity 0..0, then check the other
     * operand expression for known nulls and non-nulls, using the exclusive-or
     * of the given truth condition and whether the equality expression is
     * negated or not.
     */
    @Override
    public Map<String, AssignedSource> adjustAssignments(
            Map<String, AssignedSource> assignmentMap, boolean condition) {
        EqualityExpression self = this.getSelf();
        Expression operand1 = self.getOperand1();
        Expression operand2 = self.getOperand2();
        if (operand1 != null && operand1.getImpl().isNull()) {
            assignmentMap = operand2.getImpl().adjustMultiplicity(
                    assignmentMap, condition ^ self.getIsNegated());
        } else if (operand2 != null && operand2.getImpl().isNull()) {
            assignmentMap = operand1.getImpl().adjustMultiplicity(
                    assignmentMap, condition ^ self.getIsNegated());
        }
        return assignmentMap;
    }

} // EqualityExpressionImpl
