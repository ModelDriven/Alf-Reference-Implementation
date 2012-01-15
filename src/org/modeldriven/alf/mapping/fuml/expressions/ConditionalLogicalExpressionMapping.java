
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.syntax.expressions.ConditionalLogicalExpression;
import org.modeldriven.alf.syntax.expressions.ConditionalTestExpression;

public class ConditionalLogicalExpressionMapping extends
		ConditionalTestExpressionMapping {

	/**
     * 1. A conditional-and expression is mapped like a conditional-test
     * expression whose first two operand expressions are the same as those of
     * the conditional-and expression and whose third operand expression is
     * false.
     * 
     * 2. A conditional-or operator expression is mapped like a conditional-test
     * expression whose first and third operand expressions are the same as the
     * two operand expressions of the conditional-or expression and whose second
     * operand expression is true.
     */
    
    @Override
    public ConditionalTestExpression getConditionalTestExpression() {
        return this.getConditionalLogicalExpression().getImpl().
            getConditionalTestExpression();
    }
    
	public ConditionalLogicalExpression getConditionalLogicalExpression() {
		return (ConditionalLogicalExpression) this.getSource();
	}

} // ConditionalLogicalExpressionMapping
