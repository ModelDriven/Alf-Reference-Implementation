
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.syntax.expressions.BehaviorInvocationExpression;
import org.modeldriven.alf.syntax.expressions.InvocationExpression;
import org.modeldriven.alf.syntax.expressions.SequenceOperationExpression;

public class SequenceOperationExpressionMapping extends
		BehaviorInvocationExpressionMapping {
    
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
