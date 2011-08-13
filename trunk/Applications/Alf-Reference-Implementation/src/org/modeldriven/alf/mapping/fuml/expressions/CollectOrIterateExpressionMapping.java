
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.expressions.SequenceExpansionExpressionMapping;

import org.modeldriven.alf.syntax.expressions.CollectOrIterateExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class CollectOrIterateExpressionMapping extends
		SequenceExpansionExpressionMapping {

	public CollectOrIterateExpressionMapping() {
		this
				.setErrorMessage("CollectOrIterateExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public CollectOrIterateExpression getCollectOrIterateExpression() {
		return (CollectOrIterateExpression) this.getSource();
	}

} // CollectOrIterateExpressionMapping
