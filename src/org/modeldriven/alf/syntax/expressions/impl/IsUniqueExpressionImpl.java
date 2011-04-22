
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
 * A sequence expansion expression with a isUnique.
 **/

public class IsUniqueExpressionImpl
		extends SequenceExpansionExpressionImpl {

	public IsUniqueExpressionImpl(IsUniqueExpression self) {
		super(self);
	}

	@Override
	public IsUniqueExpression getSelf() {
		return (IsUniqueExpression) this.self;
	}

	/**
	 * An isUnique expression has the type Boolean.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getBooleanType();
	}
	
	/**
	 * An isUnique expression has a multiplicity lower bound of 1.
	 **/
    @Override
    protected Integer deriveLower() {
        return 1;
    }
    
	/**
	 * An isUnique expression has a multiplicity upper bound of 1.
	 **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
	
	/*
	 * Derivations
	 */
	
	public boolean isUniqueExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean isUniqueExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean isUniqueExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	/*
	 * Constraints
	 */
	
	/**
	 * The argument of an isUnique expression must have a multiplicity upper
	 * bound of 1.
	 **/
	public boolean isUniqueExpressionExpressionArgument() {
	    Expression argument = this.getSelf().getArgument();
		return argument != null && argument.getUpper() == 1;
	}

} // IsUniqueExpressionImpl
