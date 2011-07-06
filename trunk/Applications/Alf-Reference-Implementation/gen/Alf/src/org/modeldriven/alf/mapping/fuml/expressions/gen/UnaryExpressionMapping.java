
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions.gen;

import org.modeldriven.alf.mapping.fuml.expressions.gen.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.UnaryExpression;

public abstract class UnaryExpressionMapping extends ExpressionMapping {

	public UnaryExpression getUnaryExpression() {
		return (UnaryExpression) this.getSource();
	}

} // UnaryExpressionMapping
