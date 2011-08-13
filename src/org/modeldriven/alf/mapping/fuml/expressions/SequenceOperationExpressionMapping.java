
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.InvocationExpressionMapping;

import org.modeldriven.alf.syntax.expressions.SequenceOperationExpression;

public class SequenceOperationExpressionMapping extends
		InvocationExpressionMapping {

	public SequenceOperationExpressionMapping() {
		this
				.setErrorMessage("SequenceOperationExpressionMapping not yet implemented.");
	}

	public SequenceOperationExpression getSequenceOperationExpression() {
		return (SequenceOperationExpression) this.getSource();
	}

} // SequenceOperationExpressionMapping
