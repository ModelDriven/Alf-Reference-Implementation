
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.SequenceExpansionExpressionMapping;

import org.modeldriven.alf.syntax.expressions.SelectOrRejectExpression;

public class SelectOrRejectExpressionMapping extends
		SequenceExpansionExpressionMapping {

    /**
     * 1. A select or reject expression is mapped as a sequence expansion
     * expression. The expansion region from this mapping has an output
     * expansion node of the same type as the primary expression of the sequence
     * expansion expression. This node is the result source element for the
     * overall sequence expansion expression.
     * 
     * 2. The result source element of the mapping of the argument expression is
     * the source of the decision input flow for a decision node inside the
     * expansion region. The decision node also has an incoming object flow from
     * the expansion variable fork node and an outgoing object flow to the
     * output expansion node. For a select operation, the guard on the outgoing
     * object flow is true. For a reject operation, it is false.
     */
    
    // NOTE: Uses the default behavior inherited from 
    // SequenceExpansionExpressionMapping.
    
    @Override
    protected boolean isSelectLike(String operation) {
        return "select".equals(operation);
    }
    
    
	public SelectOrRejectExpression getSelectOrRejectExpression() {
		return (SelectOrRejectExpression) this.getSource();
	}

} // SelectOrRejectExpressionMapping
