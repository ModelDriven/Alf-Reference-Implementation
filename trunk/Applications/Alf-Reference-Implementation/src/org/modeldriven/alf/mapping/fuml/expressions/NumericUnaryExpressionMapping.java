
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.UnaryExpressionMapping;

import org.modeldriven.alf.syntax.expressions.NumericUnaryExpression;

public class NumericUnaryExpressionMapping extends UnaryExpressionMapping {

	public NumericUnaryExpression getNumericUnaryExpression() {
		return (NumericUnaryExpression) this.getSource();
	}

} // NumericUnaryExpressionMapping
