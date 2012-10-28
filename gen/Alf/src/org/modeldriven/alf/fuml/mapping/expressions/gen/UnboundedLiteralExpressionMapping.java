
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.expressions.gen;

import org.modeldriven.alf.fuml.mapping.expressions.gen.LiteralExpressionMapping;

import org.modeldriven.alf.syntax.expressions.UnboundedLiteralExpression;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class UnboundedLiteralExpressionMapping extends LiteralExpressionMapping {

	public UnboundedLiteralExpressionMapping() {
		this
				.setErrorMessage("UnboundedLiteralExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public UnboundedLiteralExpression getUnboundedLiteralExpression() {
		return (UnboundedLiteralExpression) this.getSource();
	}

} // UnboundedLiteralExpressionMapping
