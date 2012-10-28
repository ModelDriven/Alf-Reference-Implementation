
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.expressions.gen;

import org.modeldriven.alf.fuml.mapping.expressions.gen.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.BinaryExpression;

public abstract class BinaryExpressionMapping extends ExpressionMapping {

	public BinaryExpression getBinaryExpression() {
		return (BinaryExpression) this.getSource();
	}

} // BinaryExpressionMapping
