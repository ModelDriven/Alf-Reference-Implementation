/*******************************************************************************
 * Copyright 2016 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.syntax.expressions.ConditionalTestExpression;
import org.modeldriven.alf.syntax.expressions.NullCoalescingExpression;

public class NullCoalescingExpressionMapping extends 
    ConditionalTestExpressionMapping {
    
    @Override
    protected void map() throws MappingError {
        NullCoalescingExpression expression = this.getNullCoalescingExpression();
        if (expression.getOperand2() == null) {
            ExpressionMapping operandMapping = this.mapOperand(expression.getOperand1());
            this.graph.addAll(operandMapping.getGraph());
            this.resultSource = operandMapping.getResultSource();
        } else {
            super.map();
        }
    }

    @Override
    public ConditionalTestExpression getConditionalTestExpression() {
        return this.getNullCoalescingExpression().getImpl().
            getConditionalTestExpression();
    }
    
    public NullCoalescingExpression getNullCoalescingExpression() {
        return (NullCoalescingExpression) this.getSource();
    }

}
