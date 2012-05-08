
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

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
