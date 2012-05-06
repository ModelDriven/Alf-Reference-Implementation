
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.expressions.BinaryExpressionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.Expression;
import org.modeldriven.alf.syntax.expressions.RelationalExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class RelationalExpressionMapping extends BinaryExpressionMapping {
    
    @Override
    protected ActivityNode mapOperand(Expression operand) throws MappingError {
        ActivityNode resultSource = super.mapOperand(operand);
        
        if (this.getRelationalExpression().getIsUnlimitedNatural() && 
                operand.getType().getImpl().isInteger()) {
            CallBehaviorAction callAction = this.graph.addCallBehaviorAction(
                    getBehavior(RootNamespace.getIntegerFunctionToUnlimitedNatural()));
            this.graph.addObjectFlow(resultSource, callAction.argument.get(0));
            resultSource = callAction.result.get(0);
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
