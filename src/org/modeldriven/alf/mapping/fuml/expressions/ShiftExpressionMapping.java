
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.BinaryExpressionMapping;

import org.modeldriven.alf.syntax.common.ElementReference;
import org.modeldriven.alf.syntax.expressions.ShiftExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

public class ShiftExpressionMapping extends BinaryExpressionMapping {

    @Override
    protected ElementReference getOperatorFunction(String operator) {
        return RootNamespace.getBitStringFunction(operator);
    }

	public ShiftExpression getShiftExpression() {
		return (ShiftExpression) this.getSource();
	}

} // ShiftExpressionMapping
