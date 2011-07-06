
/*
 * Copyright 2011 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions.gen;

import org.modeldriven.alf.mapping.fuml.expressions.gen.ExpressionMapping;

import org.modeldriven.alf.syntax.expressions.ClassExtentExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class ClassExtentExpressionMapping extends ExpressionMapping {

	public ClassExtentExpressionMapping() {
		this
				.setErrorMessage("ClassExtentExpressionMapping not yet implemented.");
	}

	public List<Element> getModelElements() {
		// TODO: Auto-generated stub
		return new ArrayList<Element>();
	}

	public ClassExtentExpression getClassExtentExpression() {
		return (ClassExtentExpression) this.getSource();
	}

} // ClassExtentExpressionMapping
