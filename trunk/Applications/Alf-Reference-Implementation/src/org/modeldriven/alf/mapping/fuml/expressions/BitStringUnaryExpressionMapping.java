
/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.expressions.UnaryExpressionMapping;

import org.modeldriven.alf.syntax.expressions.BitStringUnaryExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Actions.BasicActions.CallBehaviorAction;
import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class BitStringUnaryExpressionMapping extends UnaryExpressionMapping {
    
    @Override
    protected ActivityNode mapOperand() throws MappingError {
        ActivityNode operandResultSource = super.mapOperand();
        if (this.getBitStringUnaryExpression().getIsBitStringConversion()) {
            CallBehaviorAction callAction = 
                this.graph.addCallBehaviorAction(getBehavior(
                        RootNamespace.getBitStringFunctionToBitString()));
            this.graph.addObjectFlow(
                    operandResultSource, callAction.argument.get(0));
            operandResultSource = callAction.result.get(0);
        }
        return operandResultSource;
    }

	public BitStringUnaryExpression getBitStringUnaryExpression() {
		return (BitStringUnaryExpression) this.getSource();
	}

} // BitStringUnaryExpressionMapping
