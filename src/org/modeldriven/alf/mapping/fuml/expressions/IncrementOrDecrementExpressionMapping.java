
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.IncrementOrDecrementExpression;

import fUML.Syntax.Classes.Kernel.Element;

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
