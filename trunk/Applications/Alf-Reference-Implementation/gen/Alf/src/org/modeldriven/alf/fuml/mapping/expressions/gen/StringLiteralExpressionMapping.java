
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.fuml.mapping.expressions.gen;

import org.modeldriven.alf.fuml.mapping.expressions.gen.LiteralExpressionMapping;

import org.modeldriven.alf.syntax.expressions.StringLiteralExpression;

import org.modeldriven.alf.uml.Element;

import java.util.ArrayList;
import java.util.List;

public class StringLiteralExpressionMapping extends LiteralExpressionMapping {

	public StringLiteralExpressionMapping() {
		this
				.setErrorMessage("StringLiteralExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public StringLiteralExpression getStringLiteralExpression() {
		return (StringLiteralExpression) this.getSource();
	}

} // StringLiteralExpressionMapping
