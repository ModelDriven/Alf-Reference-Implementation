
/*******************************************************************************
 * Copyright 2011, 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 * All rights reserved worldwide. This program and the accompanying materials
 * are made available for non-commercial use under the terms of the 
 * GNU General Public License (GPL) version 3 that accompanies this
 * distribution and is available at http://www.gnu.org/licenses/gpl-3.0.html.
 * For other licensing terms, contact Model Driven Solutions.
 *******************************************************************************/

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.BinaryExpressionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.ArithmeticExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

public class ArithmeticExpressionMapping extends BinaryExpressionMapping {

    @Override
    protected ElementReference getOperatorFunction(String operator) {
        return this.getArithmeticExpression().getIsConcatenation()?
                RootNamespace.getStringFunction(operator):
                RootNamespace.getIntegerFunction(operator);
    }

	public ArithmeticExpression getArithmeticExpression() {
		return (ArithmeticExpression) this.getSource();
	}

} // ArithmeticExpressionMapping
