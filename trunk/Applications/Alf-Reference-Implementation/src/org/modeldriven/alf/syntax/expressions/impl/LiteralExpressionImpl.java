
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.expressions.*;

/**
 * An expression that comprises a primitive literal.
 **/

public abstract class LiteralExpressionImpl extends ExpressionImpl {

	public LiteralExpressionImpl(LiteralExpression self) {
		super(self);
	}

	@Override
	public LiteralExpression getSelf() {
		return (LiteralExpression) this.self;
	}
	
	/**
	 * The multiplicity lower bound of a literal expression is always 1.
	 **/
    @Override
    protected Integer deriveLower() {
        return 1;
    }

	/**
	 * The multiplicity upper bound of a literal expression is always 1.
	 **/
    @Override
    protected Integer deriveUpper() {
        return 1;
    }

    /*
     * Derivations
     */
    
	/**
	 * The type of a literal expression is given by the type of the literal, as
	 * defined for each subclass below.
	 **/
	public boolean literalExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean literalExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean literalExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

} // LiteralExpressionImpl
