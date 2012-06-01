
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for under the terms of the GNU General Public License 
 * (GPL) version 3 that accompanies this distribution and is available at 
 * http://www.gnu.org/licenses/gpl-3.0.html. For alternative licensing terms, 
 * contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.MappingError;
import org.modeldriven.alf.mapping.fuml.expressions.BinaryExpressionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.ShiftExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

import fUML.Syntax.Activities.IntermediateActivities.ActivityNode;

public class ShiftExpressionMapping extends BinaryExpressionMapping {

    @Override
    protected void mapOperator(
            String operator,
            ActivityNode operand1Result, 
            ActivityNode operand2Result) throws MappingError {
        ShiftExpression expression = this.getShiftExpression();
        if (expression.getIsBitStringConversion()) {
            operand1Result = this.addBitStringConversion(operand1Result);
        }
        super.mapOperator(operator, operand1Result, operand2Result);
    }

    @Override
    protected ElementReference getOperatorFunction(String operator) {
        return RootNamespace.getBitStringFunction(operator);
    }

	public ShiftExpression getShiftExpression() {
		return (ShiftExpression) this.getSource();
	}

} // ShiftExpressionMapping
