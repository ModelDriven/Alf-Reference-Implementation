
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
 * An expression comprising the keyword “this”.
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
            ElementReference activeClass = context.getImpl().getActiveClass();
            if (activeClass != null) {
                return activeClass;
            } else {
    	        UnitDefinition unit = context.getImpl().asNamespace().getUnit();
                ElementReference namespace = unit == null? null: unit.getNamespace();
	            return namespace != null && 
	                    namespace.getImpl().asNamespace().getImpl().getStubFor(unit) 
	                        instanceof OperationDefinition? 
	                                namespace: context;
            }
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
