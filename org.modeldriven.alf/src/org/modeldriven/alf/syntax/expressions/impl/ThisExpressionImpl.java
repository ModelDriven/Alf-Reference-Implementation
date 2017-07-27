
/*******************************************************************************
 * Copyright 2011-2015 Data Access Technologies, Inc. (Model Driven Solutions)
 * 
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. 
 *******************************************************************************/

package org.modeldriven.alf.syntax.expressions.impl;

import org.modeldriven.alf.syntax.common.*;
import org.modeldriven.alf.syntax.expressions.*;
import org.modeldriven.alf.syntax.units.*;

/**
 * An expression comprising the keyword "this.
 **/

public class ThisExpressionImpl extends ExpressionImpl {
    
    NamespaceDefinition currentScope = null;

	public ThisExpressionImpl(ThisExpression self) {
		super(self);
	}

	public ThisExpression getSelf() {
		return (ThisExpression) this.self;
	}

	/**
	 * The static type of a this expression is the statically determined context
	 * classifier for the context in which the this expression occurs.
	 **/
	@Override
	protected ElementReference deriveType() {
	    return this.currentScope == null? null: 
	        this.currentScope.getImpl().getContext();
	}
	
	/**
	 * The multiplicity upper bound of a this expression is always 1.
	 **/
	@Override
	protected Integer deriveUpper() {
	    return 1;
	}
	
	/**
	 * The multiplicity lower bound of a this expression is always 1.
	 **/
    @Override
    protected Integer deriveLower() {
        return 1;
    }
	
	/*
	 * Derivations
	 */
	
	public boolean thisExpressionTypeDerivation() {
		this.getSelf().getType();
		return true;
	}

	public boolean thisExpressionUpperDerivation() {
		this.getSelf().getUpper();
		return true;
	}

	public boolean thisExpressionLowerDerivation() {
		this.getSelf().getLower();
		return true;
	}
	
	/*
	 * Helper Methods
	 */
	
	@Override
	public void setCurrentScope(NamespaceDefinition currentScope) {
	    this.currentScope = currentScope;
	}

} // ThisExpressionImpl
