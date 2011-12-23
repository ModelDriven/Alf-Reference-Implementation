
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.InvocationExpressionMapping;

import org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.SequenceOperationExpression;

public class SequenceOperationExpressionMapping extends
		InvocationExpressionMapping {
    
    /**
     * A sequence operation expression is mapped as a behavior invocation
     * expression for the referent behavior, with the target primary expression
     * as the first behavior argument. The result source element for the
     * sequence operation expression is that of the behavior invocation
     * expression.
     */
    
    BehaviorInvocationExpression invocation = null;

    @Override
    public InvocationExpression getInvocationExpression() {
        if (this.invocation == null) {
            // The getInvocation operation returns the equivalent behavior
            // invocation expression for the sequence operation expression.
            this.invocation = 
                this.getSequenceOperationExpression().getImpl().getInvocation();
        }
        return this.invocation;        
    }

	public SequenceOperationExpression getSequenceOperationExpression() {
		return (SequenceOperationExpression) this.getSource();
	}

} // SequenceOperationExpressionMapping
