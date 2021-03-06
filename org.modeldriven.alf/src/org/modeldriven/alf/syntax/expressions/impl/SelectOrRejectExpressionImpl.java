/*******************************************************************************
 * Copyright 2011, 2017 Model Driven Solutions, Inc.
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;

/**
 * A sequence expansion expression with a select or reject operation.
 **/

public class SelectOrRejectExpressionImpl
		extends SequenceExpansionExpressionImpl {

	public SelectOrRejectExpressionImpl(SelectOrRejectExpression self) {
		super(self);
	}

	@Override
	public SelectOrRejectExpression getSelf() {
		return (SelectOrRejectExpression) this.self;
	}

	/**
	 * A select or reject expression has the same type as its primary
	 * expression.
	 **/
	@Override
	protected ElementReference deriveType() {
	    Expression expression = this.getExpression();
	    return expression == null? null: expression.getType();
	}
	
	/**
	 * A select or reject expression has a multiplicity lower bound of 0.
	 **/
	@Override
	protected Integer deriveLower() {
	    return 0;
	}
	
    /**
     * A select or reject expression has the same multiplicity upper bound as
     * its primary expression.
     **/
    @Override
    protected Integer deriveUpper() {
        Expression primary = this.getExpression();
        return primary == null? -1: primary.getUpper();
    }
    
	/*
	 * Derivations
	 */
	
	public boolean selectOrRejectExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean selectOrRejectExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}

	public boolean selectOrRejectExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}
	
	/*
	 * Constraints
	 */

	/**
     * The argument of a select or reject expression must have a type that
     * conforms to type Boolean and a multiplicity upper bound of 1.
	 **/
	public boolean selectOrRejectExpressionArgument() {
	    Expression argument = this.getSelf().getArgument();
	    ElementReference type = argument == null? null: argument.getType();
		return type == null ||
		            type.getImpl().isBoolean() && argument.getUpper() == 1;
	}

} // SelectOrRejectExpressionImpl
