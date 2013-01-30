
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for use under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.fuml.mapping.expressions;

import org.modeldriven.alf.fuml.mapping.expressions.BinaryExpressionMapping;
import org.modeldriven.alf.mapping.MappingError;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.RelationalExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import org.modeldriven.alf.uml.CallBehaviorAction;
import org.modeldriven.alf.uml.ActivityNode;

public class RelationalExpressionMapping extends BinaryExpressionMapping {
    
    @Override
    protected ActivityNode mapOperand(Expression operand) throws MappingError {
        ActivityNode resultSource = super.mapOperand(operand);
        
        if (this.getRelationalExpression().getIsUnlimitedNatural() && 
                operand.getType().getImpl().isInteger()) {
            CallBehaviorAction callAction = this.graph.addCallBehaviorAction(
                    getBehavior(RootNamespace.getIntegerFunctionToUnlimitedNatural()));
            this.graph.addObjectFlow(resultSource, callAction.getArgument().get(0));
            resultSource = callAction.getResult().get(0);
        }
        
        return resultSource;
    }

    @Override
    protected ElementReference getOperatorFunction(String operator) {
        return this.getRelationalExpression().getIsUnlimitedNatural()?
                RootNamespace.getUnlimitedNaturalFunction(operator):
                RootNamespace.getIntegerFunction(operator);
    }

	public RelationalExpression getRelationalExpression() {
		return (RelationalExpression) this.getSource();
	}

} // RelationalExpressionMapping
