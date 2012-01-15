
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
import org.modeldriven.alf.syntax.expressions.LogicalExpression;
import org.modeldriven.alf.syntax.units.RootNamespace;

public class LogicalExpressionMapping extends BinaryExpressionMapping {

    @Override
    protected ElementReference getOperatorFunction(String operator) {
        return this.getLogicalExpression().getIsBitWise()?
                RootNamespace.getBitStringFunction(operator):
                RootNamespace.getBooleanFunction(operator);
    }

	public LogicalExpression getLogicalExpression() {
		return (LogicalExpression) this.getSource();
	}

} // LogicalExpressionMapping
