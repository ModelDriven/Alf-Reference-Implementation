
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
