
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
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
 * A binary expression that tests the equality of its operands.
 **/

public class EqualityExpressionImpl extends BinaryExpressionImpl {

	private Boolean isNegated = null; // DERIVED

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
	    return RootNamespace.getBooleanType();
	}
	
	/**
	 * An equality expression has a multiplicity lower bound of 1.
	 **/
    @Override
    protected Integer deriveLower() {
        return 1;
    }
    
	/**
	 * An equality expression has a multiplicity upper bound of 1.
	 **/
    @Override
    protected Integer deriveUpper() {
        return 1;
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
	
} // EqualityExpressionImpl
