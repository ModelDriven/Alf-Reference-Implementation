/*******************************************************************************
 * Copyright 2011-2016 Model Driven Solutions, Inc.
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
import org.modeldriven.alf.syntax.expressions.ArithmeticExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;
import org.modeldriven.alf.uml.ActivityNode;

public class ArithmeticExpressionMapping extends BinaryExpressionMapping {

    @Override
    protected void mapOperator(
            String operator,
            ActivityNode operand1Result, 
            ActivityNode operand2Result) throws MappingError {
        ArithmeticExpression expression = this.getArithmeticExpression();
        if (expression.getIsRealConversion1()) {
            operand1Result = this.addRealConversion(operand1Result);
        }
        if (expression.getIsRealConversion2()) {
            operand2Result = this.addRealConversion(operand2Result);
        }
        super.mapOperator(operator, operand1Result, operand2Result);
    }

    @Override
    protected ElementReference getOperatorFunction(String operator) {
        ArithmeticExpression expression = this.getArithmeticExpression();
        return expression.getIsConcatenation()?
                    RootNamespace.getRootScope().getStringFunction(operator):
               expression.getIsReal()?
                    RootNamespace.getRootScope().getRealFunction(operator):
                    RootNamespace.getRootScope().getIntegerFunction(operator);
    }

	public ArithmeticExpression getArithmeticExpression() {
		return (ArithmeticExpression) this.getSource();
	}

}
