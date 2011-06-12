
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
import org.modeldriven.alf.syntax.units.*;

/**
 * A binary expression with a relational operator.
 **/

public class RelationalExpressionImpl extends BinaryExpressionImpl {

	private Boolean isUnlimitedNatural = null; // DERIVED

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
		return type1 != null && !type1.getImpl().isInteger() ||
		       type2 != null && !type2.getImpl().isInteger();
	}

	/**
	 * The type of a relational expression is Boolean.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getBooleanType();
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
	
	/*
	 * Derivations
	 */
	
	public boolean relationalExpressionIsUnlimitedNaturalDerivation() {
		this.getSelf().getIsUnlimitedNatural();
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
	
	/*
	 * Constraints
	 */

	/**
	 * The operand expressions for a comparison operator must have type Integer,
	 * UnlimitedNatural or Natural. However, it is not allowed to have one
	 * operand expression be Integer and the other be UnlimitedNatural.
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
		       (type1.getImpl().isInteger() && type2.getImpl().isInteger() ||
		        type1.getImpl().isUnlimitedNatural() && type2.getImpl().isUnlimitedNatural());
	}

} // RelationalExpressionImpl
