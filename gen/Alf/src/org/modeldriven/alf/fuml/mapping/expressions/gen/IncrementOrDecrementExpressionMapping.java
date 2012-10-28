
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.expressions.gen;

import org.modeldriven.alf.fuml.mapping.expressions.gen.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.IncrementOrDecrementExpression;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class IncrementOrDecrementExpressionMapping extends ExpressionMapping {

	public IncrementOrDecrementExpressionMapping() {
		this
				.setErrorMessage("IncrementOrDecrementExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public IncrementOrDecrementExpression getIncrementOrDecrementExpression() {
		return (IncrementOrDecrementExpression) this.getSource();
	}

} // IncrementOrDecrementExpressionMapping
