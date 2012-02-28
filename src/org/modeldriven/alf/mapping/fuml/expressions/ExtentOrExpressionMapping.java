
/*
 * Copyright 2012 Data Access Technologies, Inc. (Model Driven Solutions)
 *
 * Licensed under the Academic Free License version 3.0 
 * (http://www.opensource.org/licenses/afl-3.0.php) 
 *
 */

package org.modeldriven.alf.mapping.fuml.expressions;

import org.modeldriven.alf.mapping.fuml.FumlMapping;

import org.modeldriven.alf.syntax.expressions.ExtentOrExpression;

import fUML.Syntax.Classes.Kernel.Element;

import java.util.ArrayList;
import java.util.List;

public class ExtentOrExpressionMapping extends FumlMapping {

	public ExtentOrExpressionMapping() {
		this.setErrorMessage("No mapping for ExtentOrExpressionMapping.");
	}

	public List<Element> getModelElements() {
		return new ArrayList<Element>();
	}

	public ExtentOrExpression getExtentOrExpression() {
		return (ExtentOrExpression) this.getSource();
	}

} // ExtentOrExpressionMapping
