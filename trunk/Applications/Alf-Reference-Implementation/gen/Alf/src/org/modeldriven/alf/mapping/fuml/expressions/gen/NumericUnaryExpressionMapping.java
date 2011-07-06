
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions.gen;

import org.modeldriven.alf.mapping.fuml.expressions.gen.UnaryExpressionMapping;

import org.modeldriven.alf.syntax.expressions.NumericUnaryExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class NumericUnaryExpressionMapping extends UnaryExpressionMapping {

	public NumericUnaryExpressionMapping() {
		this
				.setErrorMessage("NumericUnaryExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public NumericUnaryExpression getNumericUnaryExpression() {
		return (NumericUnaryExpression) this.getSource();
	}

} // NumericUnaryExpressionMapping
