
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * Copyright 2013 Ivar Jacobson International
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
	    ElementReference context = this.currentScope == null? null: 
	                                    this.currentScope.getImpl().getReferent();
	    if (context == null) {
	        return null;
	    } else if (context.getImpl().isOperation()) {
	        return context.getImpl().getNamespace();
	    } else if (context.getImpl().isActivity()) {
	        // Check if the context is an that activity definition is the 
	        // subunit of a stub operation.
	        // NOTE: This will also work in the case in which the activity
	        // definition provides the textual definition of an external
	        // operation.
	        UnitDefinition unit = context.getImpl().asNamespace().getUnit();
	        if (unit != null) {
	            ElementReference namespace = unit.getNamespace();
	            if (namespace != null) {
	                Member stub = namespace.getImpl().asNamespace().
	                        getImpl().getStubFor(unit);
	                if (stub != null) {
	                    context = stub.getImpl().getReferent();
	                    if (context.getImpl().isOperation()) {
	                        return namespace;
	                    }
	                }
	            }
	        }
	        
	        // Check if the context activity is the classifier behavior of an
	        // active class.
	        // NOTE: If the activity is a subunit, then the context will now be
	        // the stub for that subunit. This ensures that getActiveClass
	        // works even if the "stub" is an external activity for which the
	        // original context activity definition provides a textual 
	        // definition.
	        ElementReference activeClass = context.getImpl().getActiveClass();
	        return activeClass != null? activeClass: context;
	    } else if (context.getImpl().isClass()) {
	        return context;
	    } else {
	        return null;
	    }
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
