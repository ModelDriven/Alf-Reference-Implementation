
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
 * An expression that comprises an unbounded value literal.
 **/

public class UnboundedLiteralExpressionImpl extends LiteralExpressionImpl {

	public UnboundedLiteralExpressionImpl(UnboundedLiteralExpression self) {
		super(self);
	}

	@Override
	public UnboundedLiteralExpression getSelf() {
		return (UnboundedLiteralExpression) this.self;
	}

	/**
	 * The type of an unbounded literal expression is UnlimitedNatural.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return RootNamespace.getUnlimitedNaturalType();
	}
	
	/*
	 * Derivations
	 */
	
	public boolean unboundedLiteralExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

} // UnboundedLiteralExpressionImpl
