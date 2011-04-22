
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
 * A sequence expansion expression with a forAll, exists or one operation.
 **/

public class ForAllOrExistsOrOneExpressionImpl
		extends SequenceExpansionExpressionImpl {

	public ForAllOrExistsOrOneExpressionImpl(ForAllOrExistsOrOneExpression self) {
		super(self);
	}

	@Override
	public ForAllOrExistsOrOneExpression getSelf() {
		return (ForAllOrExistsOrOneExpression) this.self;
	}

	/**
	 * A forAll, exists or one expression has the type Boolean.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getBooleanType();
	}
	
	/**
	 * A forAll, exists or one expression has a multiplicity lower bound of 1.
	 **/
	@Override
	protected Integer deriveLower() {
	    return 1;
	}
	
	/**
	 * A forAll, exists or one expression has a multiplicity upper bound of 1.
	 **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }
    

	/*
	 * Derivations
	 */
	
	public boolean forAllOrExistsOrOneExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean forAllOrExistsOrOneExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean forAllOrExistsOrOneExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
	 * The argument of a forAll, exists or one expression must have type Boolean
	 * and a multiplicity upper bound of 1.
	 **/
	public boolean forAllOrExistsOrOneExpressionArgument() {
	    ForAllOrExistsOrOneExpression self = this.getSelf();
	    Expression argument = self.getArgument();
	    ElementReference type = argument == null? null: argument.getType();
		return argument != null && argument.getUpper() == 1 &&
		            type != null && type.getImpl().isBoolean();
	}

} // ForAllOrExistsOrOneExpressionImpl
