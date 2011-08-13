
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.UnaryExpressionMapping;

import org.modeldriven.alf.syntax.expressions.BitStringUnaryExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class BitStringUnaryExpressionMapping extends UnaryExpressionMapping {

	public BitStringUnaryExpressionMapping() {
		this
				.setErrorMessage("BitStringUnaryExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public BitStringUnaryExpression getBitStringUnaryExpression() {
		return (BitStringUnaryExpression) this.getSource();
	}

} // BitStringUnaryExpressionMapping
