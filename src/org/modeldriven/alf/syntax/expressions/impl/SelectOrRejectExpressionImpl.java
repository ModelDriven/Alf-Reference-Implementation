
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
	    ExtentOrExpression primary = this.getSelf().getPrimary();
	    return primary == null? null: primary.getExpression().getType();
	}
	
	/**
	 * A select or reject expression has a multiplicity lower bound of 0.
	 **/
	@Override
	protected Integer deriveLower() {
	    return 0;
	}
	
	/**
	 * A select or reject expression has a multiplicity upper bound of *.
	 **/
    @Override
    protected Integer deriveUpper() {
        return -1;
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
	 * The argument of a select or reject expression must have type Boolean and
	 * a multiplicity upper bound of 1.
	 **/
	public boolean selectOrRejectExpressionArgument() {
	    Expression argument = this.getSelf().getArgument();
	    ElementReference type = argument == null? null: argument.getType();
		return argument != null && type != null &&
		            type.getImpl().isBoolean() && argument.getUpper() == 1;
	}

} // SelectOrRejectExpressionImpl
